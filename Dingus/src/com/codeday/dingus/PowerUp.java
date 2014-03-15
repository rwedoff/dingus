package com.codeday.dingus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class PowerUp extends WrapAroundImage
{
	public static final float INITIAL_SPEED = 50;
	private boolean collected;
	protected Player player;
	protected Timer time;
	
	public PowerUp(TextureRegion texture, World world, Player player)
	{
		super(texture, world);
		
		this.player = player;		
		randomizeDirection();
	}
	
	public float getCollisionRadius()
	{
		return getWidth() / 2;
	}	
	
	protected PowerUp(Drawable drawable, World world)
	{
		super(drawable, world);
	}
	
	public void randomizeDirection()
	{
		float num = ((float) Math.random() * 360);
		
		setVelocity(new Vector2((float) Math.cos(Math.toRadians(num)) * INITIAL_SPEED,
				(float) Math.sin(Math.toRadians(num)) * INITIAL_SPEED));
	}

	public abstract void deactivate();
	public abstract void activate(Player player);


	public boolean isFinished() 
	{
		return time.isReady();
	}

	public void setCollected()
	{
		collected = true;
	}
	
	public boolean isCollected()
	{
		return collected;
	}
}
