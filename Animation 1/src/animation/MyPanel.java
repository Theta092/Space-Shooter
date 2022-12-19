/*
 * Nicholas Rempel
 * Dec 19 2022
 * An infinite space shooter game
 *  _____                         _____ _                 _            
 * / ____|                       / ____| |               | |           
 *| (___  _ __   __ _  ___ ___  | (___ | |__   ___   ___ | |_ ___ _ __ 
 * \___ \| '_ \ / _` |/ __/ _ \  \___ \| '_ \ / _ \ / _ \| __/ _ \ '__|
 * ____) | |_) | (_| | (_|  __/  ____) | | | | (_) | (_) | ||  __/ |   
 *|_____/| .__/ \__,_|\___\___| |_____/|_| |_|\___/ \___/ \__\___|_|   
 *       | |                                                           
 *       |_|                                                           
 */
package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MyPanel extends JPanel{
	final int WIDTH = 1000;
	final int HEIGHT = 500;
	Image bgImage, bgImage2, ship, rocketImage, enemyImage,
	laserImageBlue, laserImageRed, laserImageGreen, laserImageYellow, projectileImage,
	powerupSingleImage, powerupDoubleImage, powerupTrippleImage, powerupBeamImage, powerupRocketImage, powerupSpeedImage, powerupPlaceholder, powerupCurrentImage;
	Timer timer;
	Entity rocket;
	ArrayList<Entity> rockets = new ArrayList<Entity>();
	ArrayList<Entity> enemies = new ArrayList<Entity>();
	ArrayList<Entity> powerup = new ArrayList<Entity>();
	//for ship
	int speed = 4;
	int x = 50;
	int y = 250 - 64;
	//for background
	int bgx = 0;
	int bgScrollSpeed = 1;
	//for enemy spawn
	double spawnRate = 1;
	int enemySpeed = 2;
	//for levels
	int level = 0;
	double levelSpeed = 30;
	//timers
	double fireRateTimer, spawnTimer, levelTimer;
	final double TimerDelay = 0.015;
	//
	boolean dead = false;
	//for movement
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;
	boolean fireing = false;
	Weapon currentWeapon;
	
	int points = 0;
	
	MyPanel()
	{
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		
		Random r = new Random();
		
		bgImage = new ImageIcon("spaceBG.png").getImage();
		bgImage2 = new ImageIcon("spaceBG.png").getImage();
		ship = new ImageIcon("SpaceShip.png").getImage();
		enemyImage = new ImageIcon("enemy.png").getImage();
		
		rocketImage = new ImageIcon("rocket.png").getImage();
		laserImageBlue = new ImageIcon("LaserBlue.png").getImage();
		laserImageRed = new ImageIcon("LaserRed.png").getImage();
		laserImageGreen = new ImageIcon("LaserGreen.png").getImage();
		laserImageYellow = new ImageIcon("LaserYellow.png").getImage();
		
		powerupSingleImage = new ImageIcon("SingleLaserPowerup.png").getImage();
		powerupDoubleImage = new ImageIcon("DoubleLaserPowerup.png").getImage();
		powerupTrippleImage = new ImageIcon("TrippleLaserPowerup.png").getImage();
		powerupBeamImage = new ImageIcon("LaserBeamPowerup.png").getImage();
		powerupRocketImage = new ImageIcon("RocketPowerup.png").getImage();
		powerupSpeedImage = new ImageIcon("SpeedBoostPowerup.png").getImage();
		powerupPlaceholder = new ImageIcon("powerupPlaceholder.png").getImage();
		
		Weapon singleLaser = new Weapon(laserImageBlue, 10, 1, 5);
		Weapon doubleLaser = new Weapon(laserImageGreen, 15, 2, 10);
		Weapon trippleLaser = new Weapon(laserImageYellow, 20, 3, 20);
		Weapon laserbeam = new Weapon(laserImageRed, 30, 1, 100);
		Weapon rocket = new Weapon(rocketImage, 20, 2, 7);
		
		currentWeapon = singleLaser;
		powerupCurrentImage = powerupSingleImage;
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				switch(e.getKeyCode())
				{
				case 65: left = true;break;
				case 68: right = true;break;
				case 87: up = true;break;
				case 83: down = true;break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
				switch(e.getKeyCode())
				{
				case 65: left = false;break;
				case 68: right = false;break;
				case 87: up = false;break;
				case 83: down = false;break;
				}
			}
			
		});
		timer = new Timer(5, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//input
				if(left)
				{
					if(x > 0)
					{
						x = x - speed;
					}
				}
				if(right)
				{
					if(x < WIDTH - 80)
					{
						x = x + speed;
					}
				}
				
				if(up)
				{
					if(y > 0)
					{
						y = y - speed;
					}
				}
				if(down)
				{
					if(y < HEIGHT - 96)
					{
						y = y + speed;
					}
				}
				
				//background scroll
				if(bgx <=-1000)
				{
					bgx = 0;
				}
				else
				{
					bgx = bgx - bgScrollSpeed;
				}
				
				//fires over interval
				if(!dead)
				{
					//fire rate timer
					if(fireRateTimer <= 0)
					{
						fireRateTimer = 1/currentWeapon.getFireRate();
						for(int i = 0; i < currentWeapon.getCount(); i++)
						{
							rockets.add(new Entity(currentWeapon.getImage(), x, (y + (ship.getHeight(null) / 2)) + (i * currentWeapon.getImage().getHeight(null) * 2) - ((((currentWeapon.getCount() * 2) - 1) * currentWeapon.getImage().getHeight(null)) / 2)));
						}
						fireing = true;
					}
					else
					{
						fireRateTimer -= TimerDelay;
					}
					
					//spawns enemies over interval
					if(spawnTimer <= 0)
					{
						spawnTimer = 1/spawnRate;
	
						enemies.add(new Entity(enemyImage, WIDTH, r.nextInt(HEIGHT/enemyImage.getHeight(null)) * enemyImage.getHeight(null), false));
					}
					else
					{
						spawnTimer -= TimerDelay;
					}
					for(int i = 0; i < enemies.size(); i++)
					{
						for(int j = 0; j < rockets.size(); j++)
						{
							//kill enemy
							if(rockets.get(j).getX() < enemies.get(i).getX() + enemies.get(i).getImage().getWidth(null) && enemies.get(i).getX() < rockets.get(j).getX() + rockets.get(j).getImage().getWidth(null) && rockets.get(j).getY() < enemies.get(i).getY() + enemies.get(i).getImage().getHeight(null) && enemies.get(i).getY() < rockets.get(j).getY() + rockets.get(j).getImage().getHeight(null))
							{
								rockets.get(j).setActive(false);
								enemies.get(i).setActive(false);
							}
						}
					}
					
					//changes the intensity after an amount of time
					if(levelTimer <= 0)
					{
						levelTimer = levelSpeed;
						level++;
						
						spawnRate += 5;
						enemySpeed += 2;
						bgScrollSpeed += 2;
					}
					else
					{
						levelTimer -= TimerDelay;
					}
				}
				
				//deletes the enemies and adds points
				for(int i = 0; i < enemies.size(); i++)
				{
					if(!enemies.get(i).getActive())
					{
						if(!dead)
						{
							points++;
							int rand = r.nextInt(1000)+1;
							//1 in 10 chance to get a powerup
							
							//25%
							if(rand < 25)
							{
								powerup.add(new Entity(powerupSpeedImage, 5, enemies.get(i).getX(), enemies.get(i).getY(), false));
							}
							//25%
							else if(rand <= 50)
							{
								powerup.add(new Entity(powerupSingleImage, 0, enemies.get(i).getX(), enemies.get(i).getY(), false));
							}
							//20%
							else if(rand <= 70)
							{
								powerup.add(new Entity(powerupDoubleImage, 1, enemies.get(i).getX(), enemies.get(i).getY(), false));
							}
							//15%
							else if(rand <= 85)
							{
								powerup.add(new Entity(powerupTrippleImage, 2, enemies.get(i).getX(), enemies.get(i).getY(), false));
							}
							//10%
							else if(rand <= 95)
							{
								powerup.add(new Entity(powerupBeamImage, 3, enemies.get(i).getX(), enemies.get(i).getY(), false));
							}
							//5%
							else if(rand <= 100)
							{
								powerup.add(new Entity(powerupRocketImage, 4, enemies.get(i).getX(), enemies.get(i).getY(), false));
							}
						}
						enemies.remove(i);
						i--;
					}
				}
				//deletes rockets
				for(int j = 0; j < rockets.size(); j++)
				{
					if(!rockets.get(j).getActive())
					{
						rockets.remove(j);
						j--;
					}
				}
				
				//move rockets
				for(int i = 0; i < rockets.size(); i++)
				{
					rockets.get(i).move(currentWeapon.getSpeed());
				}
				
				//move enemies
				for(int i = 0; i < enemies.size(); i++)
				{
					enemies.get(i).move(enemySpeed);
					if(x < enemies.get(i).getX() + enemies.get(i).getImage().getWidth(null) && enemies.get(i).getX() < x + ship.getWidth(null) && y < enemies.get(i).getY() + enemies.get(i).getImage().getHeight(null) && enemies.get(i).getY() < y + ship.getHeight(null))
					{
						dead = true;
					}
				}
				
				//move powerups
				for(int i = 0; i < powerup.size(); i++)
				{
					powerup.get(i).move(10);
					if(x < powerup.get(i).getX() + powerup.get(i).getImage().getWidth(null) && powerup.get(i).getX() < x + ship.getWidth(null) && y < powerup.get(i).getY() + powerup.get(i).getImage().getHeight(null) && powerup.get(i).getY() < y + ship.getHeight(null))
					{
						switch(powerup.get(i).getType())
						{
						case 0: currentWeapon = singleLaser; powerupCurrentImage = powerupSingleImage; break;
						case 1: currentWeapon = doubleLaser; powerupCurrentImage = powerupDoubleImage; break;
						case 2: currentWeapon = trippleLaser; powerupCurrentImage = powerupTrippleImage; break;
						case 3: currentWeapon = laserbeam; powerupCurrentImage = powerupBeamImage; break;
						case 4: currentWeapon = rocket; powerupCurrentImage = powerupRocketImage; break;
						case 5: speed += 1; break;
						case 6: break;
						case 7: break;
						case 8: break;
						case 9: break;
						default: break;
						}
						powerup.remove(i);
					}
				}
				
				repaint();
			}
			
		});
		
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D)g;
		Font font = new Font("sans-serif", Font.PLAIN, 40);

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2D.setRenderingHints(rh);
	    
		g2D.drawImage(bgImage, bgx, 0, null);
		g2D.drawImage(bgImage2, bgx + 1000, 0, null);
		//if rockets are out of bounds
		for(int i = 0; i < rockets.size(); i++)
		{
			if(rockets.get(i).getX() < WIDTH + 10)
			{
				g2D.drawImage(rockets.get(i).getImage(), rockets.get(i).getX(), rockets.get(i).getY(), null);
			}
			else
			{
				rockets.remove(i);
				
			}
		}
		//if enemies are out of bounds
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).getX() > - 10 - enemyImage.getWidth(null))
			{
				g2D.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), enemies.get(i).getY(), null);
			}
			else
			{
				enemies.remove(i);
				if(!dead)
				{
					points--;
				}
			}
		}
		//if powerup is out of bounds
		for(int i = 0; i < powerup.size(); i++)
		{
			if(powerup.get(i).getX() > - 10 - powerup.get(i).getImage().getWidth(null))
			{
				g2D.drawImage(powerup.get(i).getImage(), powerup.get(i).getX(), powerup.get(i).getY(), null);
			}
			else
			{
				powerup.remove(i);
			}
		}
		g2D.drawImage(ship, x, y, null);
		g2D.setFont(font);
		g2D.setColor(Color.WHITE);
		g2D.drawString(Integer.toString(points), 900, 50);
		g2D.setColor(Color.yellow);
		g2D.drawString(Integer.toString(level), 50, 50);
		g2D.drawImage(powerupCurrentImage, 100, 20, null);
		
		//on defeat
		if(dead)
		{
			g2D.setFont(new Font("Italic", Font.PLAIN, 100));
			g2D.setColor(Color.WHITE);
			g2D.drawString("Game_Over", 240, HEIGHT/2);
			speed = 0;
		}
	}
}
