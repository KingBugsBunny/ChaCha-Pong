import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.net.URL;


@SuppressWarnings("serial")
public class ChaCha_Pong extends Applet implements Runnable, KeyListener 
{
	
	//Declarations
	volatile boolean leftPressed, rightPressed, spacePressed = false;
	
	Paddle paddle;
	Paddle opponentPaddle;
	Ball ball;
	
	//Count Vars
	
		//for paddleHit()
		int paddleHit = 0; 
	
		//for resetField()
		int hitTop = 0;
		int hitBottom = 0;
	
	//Art Assets
		Image backGround;
		Image ballGraphic;
		Image paddleGraphic;
		Image opponentPaddleGraphic;
		Image menu;
		
	//Audio Objects
		AudioClip blip;
		AudioClip blip2;
		AudioClip gameStart;
		AudioClip score;
		
	
	Image offScreen; //backBuffer
	Graphics2D offScreenGrp;
	
	Thread gameLoop;
	
	int arenaHeight = 600, arenaWidth = 800;
	int userScore = 0, opponentScore = 0;
	
	
	
	//used to play gameStart sound once
	int pressPlay = 0;

	//This method gets bitmap locations
	private URL getURL(String filename) 
	{
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        }
        catch (Exception e) { }
        return url;
    }
	
	
	//Applet Stuff
	
		public void init()
		{
			resize(800, 600);
			offScreen = createImage(arenaWidth, arenaHeight); //BackBuffer
			
			offScreenGrp = (Graphics2D) offScreen.getGraphics();
			
			addKeyListener(this);
			
			//ToolKit for bitmap Imgs
				Toolkit tk = Toolkit.getDefaultToolkit();
			
			//Images
				backGround = tk.getImage(getURL("GameBoy_Background.png"));
				ballGraphic = tk.getImage(getURL("ballGraphic.png"));
				paddleGraphic = tk.getImage(getURL("paddleGraphic.png"));
				opponentPaddleGraphic = tk.getImage(getURL("opponentPaddlegraphic.png"));
				menu = tk.getImage(getURL("menuGraphic.png"));
				
	
			//Initial methods
			
				//Ball
					ball = new Ball();
					
					//Ball Coordinates
						ball.setX(490);
						ball.setY(250);
						
					//Ball Velocity
						ball.setVelX(3);
						ball.setVelY(3);
					
				
					
					
				//player Paddle
					paddle = new Paddle();
					
					//paddle coordinates
						paddle.setX(670);
						paddle.setY(530);
						
				//Opponent Paddle
					opponentPaddle = new Paddle();
					
					//opponent Paddle coordinates
						opponentPaddle.setX(450);
						opponentPaddle.setY(40);
						
					//Audio
					blip = getAudioClip(getCodeBase(), ("blip.wav"));
					blip2 = getAudioClip(getCodeBase(), ("blip2.wav"));
					gameStart = getAudioClip(getCodeBase(), ("gameStart.wav"));
					score = getAudioClip(getCodeBase(), ("score.wav"));
					
				
		}
		
		public void start()
		{
			
			gameLoop = new Thread(this);
			
			gameLoop.start();
			
		}
		
		public void run()
		{
			
			Thread t = Thread.currentThread();
			
			while (t == gameLoop)
			{
				
				try
				{
					
					
					
					
						//Update game()
						//Includes ball move, paddle move,Opponent Artifical Intelligence and collision checking
						//--------------------------------------------------------------------------------------
						
						updateGame();
						
						//--------------------------------------------------------------------------------------
						
						//End game if score is met
						if (userScore == 5 || opponentScore == 5)
						{
							gameLoop = null;
							
						}
					
					
					
					Thread.sleep(20); //Apparently 20 is 50fps
					
				}
				catch(Exception e) {}
				
				repaint();
			}
			
		}
		
		public void stop()
		{
			
			gameLoop = null;
			blip.stop();
			blip2.stop();
			
		}
		
	//End Applet Stuff
	
	
	public void paint(Graphics g)
	{
		//Initiate Affine transforms
			AffineTransform identity = new AffineTransform(); //IDK if I need to use this
			
			AffineTransform bgTrans = new AffineTransform();
			AffineTransform ballTrans = new AffineTransform();
			AffineTransform paddleTrans = new AffineTransform();
			AffineTransform opponentPaddleTrans = new AffineTransform();
			AffineTransform menuTrans = new AffineTransform();
			
		
		//create instance of G2D
			Graphics g2d = (Graphics2D) g;
			
		//Transform Background
			bgTrans.setTransform(identity);
			
			bgTrans.translate(0,0);
			
		//Transform menu
			menuTrans.setTransform(identity);
			
			menuTrans.translate(0,0);
		
		//Transform Ball
			ballTrans.setTransform(identity);
			
			ballTrans.translate(ball.getX(),ball.getY());
			
		//Transform Paddle
			paddleTrans.setTransform(identity);
			
			paddleTrans.translate(paddle.getX(), paddle.getY());
			
		//Transform opponent paddle
			opponentPaddleTrans.setTransform(identity);
			
			opponentPaddleTrans.translate(opponentPaddle.getX(), opponentPaddle.getY());
			
		
		//Draw Images
			
			//Static Assets
				offScreenGrp.drawImage(backGround, bgTrans, this);
				offScreenGrp.drawImage(menu, menuTrans, this);
				
				Color gameBoyBlack = new Color(35, 67, 49);
				
				offScreenGrp.setColor(gameBoyBlack);
				offScreenGrp.setFont(new Font("Verdana", Font.BOLD, 36));
				offScreenGrp.drawString(userScore + "", 45, 238);
				offScreenGrp.drawString(opponentScore + "", 128, 238);
				
			//Active Assets
				offScreenGrp.drawImage(ballGraphic, ballTrans, this);
				offScreenGrp.drawImage(paddleGraphic, opponentPaddleTrans, this);
				offScreenGrp.drawImage(opponentPaddleGraphic, paddleTrans, this);
				
				
			//GAME OVER ASSETS
				if (userScore > 4)
				{
					
					offScreenGrp.drawString("GAME OVER. YOU WON" , 200, 300);
					
				}
				else if (opponentScore > 4)
				{
					
					offScreenGrp.drawString("GAME OVER. YOU LOST" , 200, 300);
					
				}
			
			//DEBUG STRINGS
				
				//Ball coordinates
			/*	offScreenGrp.drawString("ball X: ", 500,350);
				offScreenGrp.drawString(ball.getX() + "", 650, 350);
				
				offScreenGrp.drawString("ball Y: ", 500, 380);
				offScreenGrp.drawString(ball.getY() + "", 650, 380);
			*/	
				
				//ball velocity
				/*
				offScreenGrp.drawString("B VelX:" + "", 500, 420);
				offScreenGrp.drawString(ball.getVelX() + "", 650, 420);
				offScreenGrp.drawString("B VelY:" + "", 500, 490);
				offScreenGrp.drawString(ball.getVelY() + "", 650, 490);
				
				//counts times paddle collides with ball
				offScreenGrp.drawString("Paddle hit:" + "" , 500, 520);
				offScreenGrp.drawString(paddleHit + "", 730, 520);
				*/
			//END DEBUG STRINGS
				
				
				if(!spacePressed)
				{
					
					offScreenGrp.drawString("PRESS SPACE TO PLAY", 250, 300);
					
					
				}
				
				
		//Draw to Applet
			g2d.drawImage(offScreen,0,0,this);
		
	}
	
	
	//Checks ball collision with arena as well as moves ball
	public void moveBall()
	{
		//Move Ball X
			ball.incX(ball.getVelX() );
		
		//Check collision on sides of arena
			//Moving Right
			if (ball.getX() < 201)
			{
				
				ball.setVelX(5);
				
			}
			
			//Moving Left
			else if (ball.getX() > arenaWidth - ball.getWidth())
			{
				
				ball.setVelX(-5);
				
			}
			
		//Move Ball Y
			ball.incY(ball.getVelY() );
			
		//Check collision on top of arena
			
			//Moving Down
			if (ball.getY() < 0)
			{
				hitTop++;
				
				ball.setVelY(5);
				userScore++;
				score.play();
				
			}//Check Collision on bottom
			
			//Moving Up
			else if (ball.getY() > arenaHeight - ball.getHeight())
			{
				hitBottom++;
				
				ball.setVelY(-5);
				opponentScore++;
				score.play();
				
			}
			
			
	}
	
	
	public void movePaddle()
	{
		//move left
			if( leftPressed == true && paddle.getX() > 204)
			{
				paddle.incX(-8);
				
			}
		
		//move right
			if (rightPressed == true && paddle.getX() < 695)
			{
			
				paddle.incX(8);
				
			}
			
			
		
		
	}//end movePaddle()
	
	public void paddleHit(){
		//counts how many times the ball has collided with player and opponents paddle using paddleHit and increases Velocity
	
		
		if (paddleHit >= 3)
		{
			
			ball.incVelX(ball.getVelX() + 1);
			ball.incVelY(ball.getVelY() + 1);
			paddleHit = 0;
		
		}
		
	}
	
	public void resetField()
	
		//Repositions ball and paddles after volley
	{
		if(hitBottom >= 1)
		{
		//Ball Position
		ball.setX(490);
		ball.setY(250);
		opponentPaddle.setX(450);
		hitBottom = 0;
		}
		else if(hitTop >= 1)
		{
			ball.setX(490);
			ball.setY(250);
			opponentPaddle.setX(450);
			hitTop = 0;
		}
		//Player and opponent paddle positions
		
	}
	
	public void opponentAI()
	{
		int ballX = ball.getX(), ballY = ball.getY();
		
		 
				
				//STANDARD
				if (opponentPaddle.getX() > ballX && ballY < 200)
				{
					//offScreenGrp.drawString("STANDARD MODE", 400, 400);
					if(opponentPaddle.getX() > 204 && opponentPaddle.getY() < ballX)
					{
						
					opponentPaddle.incX(-7);
					
					}
				}
				else if (opponentPaddle.getX() < ballX && ballY < 200)
				{
					//offScreenGrp.drawString("STANDARD MODE", 400, 400);
					if(opponentPaddle.getX() < 695 && opponentPaddle.getY() < ballX)
					{
						offScreenGrp.drawString("STANDARD MODE", 400, 400);
					opponentPaddle.incX(7);
					
					}
				}
			}
	
			
	
	
	public void checkCollision()
	{
		Rectangle ballRect = ball.getBounds();
		
		//Player Collision
			if (ballRect.intersects(paddle.getBounds() ))
			{
				
				paddleHit++;
				ball.setVelY(-(ball.getVelY() ));
				
				blip.play();
				
				
			}
			
		//opponent collision
			if (ballRect.intersects(opponentPaddle.getBounds() ))
			{
				
				paddleHit++;
				ball.setVelY(-(ball.getVelY() ));
				
				blip2.play();
				
				
			}
		
	}
	
	public void updateGame()
	{
		
		if (spacePressed)
		{
			moveBall();
			
			movePaddle();
			
			opponentAI();
			
			checkCollision();
			
			resetField();
			
			//NEED TO FIX BOUNDING BOX ON PADDLES BEFORE USE
				paddleHit();
		}
	}
	public void keyPressed(KeyEvent arg0) 
	{
		switch(arg0.getKeyCode())
		{
			case KeyEvent.VK_LEFT: leftPressed = true;
				break;
			case KeyEvent.VK_RIGHT: rightPressed = true;
				break;
			case KeyEvent.VK_SPACE: spacePressed = true;
			pressPlay++;
			if (pressPlay == 1)
			{
			
				gameStart.play();
				
			}
				break;
		}
		
	}

	
	public void keyReleased(KeyEvent arg0) 
	{
		switch(arg0.getKeyCode())
		{
			case KeyEvent.VK_LEFT: leftPressed = false;
				break;
			case KeyEvent.VK_RIGHT: rightPressed = false;
			break;

		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



} // End Class
