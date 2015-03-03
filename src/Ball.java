import java.awt.*;

public class Ball {

	//Coordinates
	private int x , y;
	
	//Dimensions
	private int width = 20, height = 20;
	
	//Velocity
	private int velX = 0, velY = 0;
	
	public int currentVelX;
	public int currentVelY;
	
	
	//Bounding box
	public Rectangle getBounds()
	{
		
		Rectangle r;
		r = new Rectangle(getX(), getY() , width, height);
		return r;
		
	}
	//Set Methods
		public void setX (int x)
		{
			
			this.x = x;
			
		}
		
		public void setY(int y)
		{
			
			this.y = y;
			
		}
		
		public void setWidth(int width)
		{
			
			this.width = width;
			
		}
		
		public void setHeight(int height)
		{
			
			this.height = height;
			
		}
		
		
		public void setVelX (int x)
		{
			
			velX = x;
		}
		public void setVelY (int y)
		{
			
			velY = y;
			
		}
	
	//Get methods
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
		
		public int getVelX()
		{
			
			return velX;
			
		}
		
		public int getVelY()
		{
		
			return velY;
			
		}
		
	//Increment methods
		
		public void incX(int i)
		{
			
			x += i;
			
			
		}
		
		public void incY(int i)
		{
			
			y += i;
			
		}
		
		public void incVelX(int i)
		{
			
			velX += i;
			
		}
		
		public void incVelY(int i)
		{
			
			velY += i;
			
		}

		
	
	
}//end class
