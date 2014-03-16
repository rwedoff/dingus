package com.codeday.jump;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;

public class Car extends Image 
{
	private Vector2 velocity;
	private AbstractWorld world;
	private ShapeRenderer renderer = new ShapeRenderer();

	public Car(TextureRegion texture, AbstractWorld world, int speed)
	{
		super(texture);
		this.world = world;
		
		//Sets direction of the Blobs
		velocity = new Vector2((float) Math.sin(Math.toRadians(90))* speed, 0);
		setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);		
		renderer = new ShapeRenderer();
	}


	public void act(float delta)
	{
		super.act(delta);
		//Makes the Blobs move
		float xPotential = getX() + velocity.x * delta;
		float yPotential = getY() + velocity.y * delta;
		setX(xPotential);
		setY(yPotential);		
		
	}
	public float getCollisionRadius()
	{
		return getHeight() / 2;
	}
	

	public Vector2 getVelocity()
	{
		return velocity;
	}
	

}
