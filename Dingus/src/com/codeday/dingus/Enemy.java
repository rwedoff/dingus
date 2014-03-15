package com.codeday.dingus;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Enemy extends Asteroid
{
	public static float INITIAL_SPEED = 100;
	public static long SHOOT_DELAY = 500;
	
	protected boolean destroyed = false;
	protected int health;
	private long lastTime;
	private Laser laser;
	
	
	public Enemy(TextureRegion texture, World world)
	{
		super(texture, world);
		health = 5;
		randomizeDirection();
		
	}
	
	protected Enemy(Drawable drawable, World world)
	{
		super(drawable, world);
	}

	public void update(float delta, World world, ArrayList<Laser> lasers, ArrayList<Asteroid> asteroids, ArrayList<Enemy> enemies)
	{
		super.update(delta);
		
		if (isChild)
			return;
		
		boolean collision = false;
		
		if (checkLaserCollision(lasers))
		{
			collision = true;
		}
		for (WrapAroundImage a : children)
		{
			for (int i = 0; i < lasers.size();)
			{
				Laser laser = lasers.get(i);
				
				Point laserPoint = new Point(laser.getX() + laser.getOriginX(), laser.getY() + laser.getOriginX());
				if (a.containsPoint(laserPoint))
				{
					collision = true;
					float x = laser.getX();
					float y = laser.getY();
					laser.remove();
					lasers.remove(i);
					addLaserSplash(x, y);
				}
				else
					i++;
			}
		}
		
		if (collision)
		{
			health--;
			setPosition(getX(), getY());
		}	
		if (health <= 0)
		{
			destroyed = true;
			for (int i = 0; i < children.size();)
				children.remove(i).remove();
		}
	}
	
	public boolean checkLaserCollision(ArrayList<Laser> lasers)
	{
		for (int i = 0; i < lasers.size();)
		{
			Laser laser = lasers.get(i);
			
			Point laserPoint = new Point(laser.getX() + laser.getOriginX(), laser.getY() + laser.getOriginX());
			if (containsPoint(laserPoint))
			{
				float x = laser.getX();
				float y = laser.getY();
				laser.remove();
				lasers.remove(i);
				addLaserSplash(x, y);
				
				return true;
			}
			else
				i++;
		}
		return false;
	}

	public void act(float delta)
	{
		super.act(delta);
		if (isChild)
			return;
		
		setOrigin(getWidth() / 2, getHeight() / 2);
		float rotation = 360 * 1.0f / 30.0f * delta;
		rotate(rotation);
	}

	public void randomizeDirection()
	{
		float num = ((float) Math.random() * 360);
		
		setVelocity(new Vector2((float) Math.cos(Math.toRadians(num)) * INITIAL_SPEED,
				(float) Math.sin(Math.toRadians(num)) * INITIAL_SPEED));
	}

	public boolean isDestroyed()
	{
		if (destroyed)
			return true;
		for (WrapAroundImage a : children)
			if (((Enemy) a).isDestroyed())
				return true;
		return false;
	}
	
	protected WrapAroundImage createChild()
	{
		Enemy a = new Enemy(getDrawable(), world);
		a.setHealth(health);
		return a;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	//SHOOTING METHODS
	
	public void shoot(ArrayList<Laser> lasers, World world)
	{
		if (canShoot())
		{
			laser = new Laser(world.getAtlas().findRegion("laserGreen"), (float) (Math.random() * 360));

			laser.setPosition(getX() + getOriginX() ,getY() + getOriginY());
			world.addActor(laser);
			lasers.add(laser);
		}
	}
		
	private boolean canShoot()
	{
		if (System.currentTimeMillis() - lastTime > SHOOT_DELAY )
		{
			lastTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
	private void addLaserSplash(float x, float y)
	{
		Image image = new Image(world.getAtlas().findRegion("laserRedShot"));
		image.setPosition(x, y);
		world.addActor(image);
		image.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.removeActor()));
	}
}
