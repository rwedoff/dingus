package com.codeday.dingus;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Asteroid extends WrapAroundImage
{

	public static float INITIAL_SPEED = 25;
	public static int MAX_HEALTH = 5;

	protected boolean destroyed = false;
	protected int health;

	public Asteroid(TextureRegion texture, World world)
	{
		super(texture, world);
		health = MAX_HEALTH;
		randomizeDirection();
	}

	protected Asteroid(Drawable drawable, World world)
	{
		super(drawable, world);
		setVelocity(new Vector2());
	}

	public void update(float delta, World world, ArrayList<Laser> lasers,
			ArrayList<Asteroid> asteroids)
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
			if (((Asteroid) a).checkLaserCollision(lasers))
				collision = true;
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

		if (destroyed && !(this instanceof SmallAsteroid))
		{
			SmallAsteroid small1 = new SmallAsteroid(world.getAtlas()
					.findRegion("meteorSmall"), world);
			SmallAsteroid small2 = new SmallAsteroid(world.getAtlas()
					.findRegion("meteorSmall"), world);

			small1.setPosition(getX() + getOriginX(), getY() + getOriginY());
			small2.setPosition(getX() + getOriginX(), getY() + getOriginY());

			asteroids.add(small1);
			world.addActor(small1);
			asteroids.add(small2);
			world.addActor(small2);

		}
	}

	public boolean checkLaserCollision(ArrayList<Laser> lasers)
	{
		for (int i = 0; i < lasers.size();)
		{
			Laser laser = lasers.get(i);

			Point laserPoint = new Point(laser.getX() + laser.getOriginX(),
					laser.getY() + laser.getOriginX());
			if (laserPoint.distance(new Point(getX() + getOriginX(), getY()
					+ getOriginY())) < getWidth() / 2 + laser.getWidth() / 2)
			{
				float x = laserPoint.getX();
				float y = laserPoint.getY();

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

	private void addLaserSplash(float x, float y)
	{
		Image image = new Image(world.getAtlas().findRegion("laserRedShot"));
		image.setPosition(x - image.getWidth() / 2, y - image.getHeight() / 2);
		world.addActor(image);
		image.addAction(Actions.sequence(Actions.fadeOut(.5f),
				Actions.removeActor()));
	}

	public void act(float delta)
	{
		super.act(delta);
		if (isChild)
			return;
		
		float rotation = 360 * 1.0f / 20.0f * delta;
		rotate(rotation);
	}

	public void randomizeDirection()
	{
		float num = ((float) Math.random() * 360);

		setVelocity(new Vector2((float) Math.cos(Math.toRadians(num))
				* INITIAL_SPEED, (float) Math.sin(Math.toRadians(num))
				* INITIAL_SPEED));
	}

	public boolean isDestroyed()
	{
		if (destroyed)
			return true;
		for (WrapAroundImage a : children)
			if (((Asteroid) a).isDestroyed())
				return true;
		return false;
	}

	protected WrapAroundImage createChild()
	{
		Asteroid a = new Asteroid(getDrawable(), world);
		a.setHealth(health);
		return a;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public float getCollisionRadius()
	{
		return getHeight() / 2;
	}

}