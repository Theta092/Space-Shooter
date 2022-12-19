/*
 * Nicholas Rempel
 * Dec 19 2022
 * Stores data for individual weapons
 */
package animation;

import java.awt.Image;

public class Weapon {

	double fireRate;
	int count;
	int speed;
	Image image;
	
	Weapon(Image i, int s, int c, double f)
	{
		image = i;
		speed = s;
		count = c;
		fireRate = f;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public double getFireRate()
	{
		return fireRate;
	}
}
