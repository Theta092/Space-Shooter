/*
 * Nicholas Rempel
 * Dec 19 2022
 * a general class for all objects in the game
 */
package animation;

import java.awt.Image;

public class Entity {

	Image image;
	int x, y, type;
	boolean forward;
	boolean active;
	
	Entity(Image r, int xVal, int yVal)
	{
		image = r;
		x = xVal;
		y = yVal;
		forward = true;
		active = true;
	}
	
	Entity(Image r, int xVal, int yVal, boolean f)
	{
		image = r;
		x = xVal;
		y = yVal;
		forward = f;
		active = true;
	}
	
	Entity(Image r,int t, int xVal, int yVal, boolean f)
	{
		image = r;
		type = t;
		x = xVal;
		y = yVal;
		forward = f;
		active = true;
	}
	
	public void setLocation(int xVal, int yVal)
	{
		x = xVal;
		y = yVal;
	}
	
	public void setDirection(boolean f)
	{
		forward = f;
	}
	public void move(int speed)
	{
		if(forward)
		{
			x += speed;
		}
		else
		{
			x -= speed;
		}
		
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image i)
	{
		image = i;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int t)
	{
		 type = t;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setActive(boolean a)
	{
		active = a;
	}
	
	public boolean getActive()
	{
		return active;
	}
}
