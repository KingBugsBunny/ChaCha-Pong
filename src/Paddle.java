import java.awt.*;

public class Paddle {

	
	private int x, y;				//Coordinates for initial paddle location
	private int height = 18, width = 100;		//Dimensions of paddle
	
	
	//Bounding Box
	public Rectangle getBounds()
	{
		
		Rectangle r;
		r = new Rectangle(getX(), getY(), width , height); // Creates rectangle for bound
		return r;
		
	}
	
	//Set Methods
		public void setX(int x)
		{
			this.x = x; //interesting way to write this, you can tell I was referencing your code.
			
		}
		
		public void setY(int y)
		{
			
			this.y = y;
			
		}
		
		public void setWidth(int w)
		{
			width = w;
			
		}
		
		public void setHeight(int h)
		{
			
			height = h;
			
		}
		
	
	//Get Methods
	
		public int getX()
		{
			
			return x;
			
		}
		
		public int getY()
		{
			
			return y;
			
		}
		
		public int getWidth()
		{
			
			return width;
			
		}
		
		public int getHeight()
		{
			
			return height;
			
		}
		
	
	//Move Method
		public void incX(int i)
		{
			
		x += i;
			
		}
		
		
	
}//end Class
