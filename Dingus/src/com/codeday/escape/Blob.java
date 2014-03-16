package com.codeday.escape;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;

public class Blob extends Image
{

	private Vector2 velocity;
	private AbstractWorld world;
	private ShapeRenderer renderer = new ShapeRenderer();

	public Blob(TextureRegion texture, AbstractWorld world)
	{
		super(texture);
		this.world = world;
		
		//Sets direction of the asteroids
		Vector2 velocity = new Vector2(0, (float) Math.sin(Math.toRadians(-90))* 225);
		setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);		
		renderer = new ShapeRenderer();
	}
	
	
	public void act(float delta)
	{
		super.act(delta);
		//Makes the asteroids move
		float xPotential = getX() + velocity.x * delta;
		float yPotential = getY() + velocity.y * delta;
		setX(xPotential);
		setY(yPotential);
		float rotation = 360 * 10.0f / 20.0f * delta;
		rotate(rotation);
		
		
	}
	
	public Vector2 getVelocity()
	{
		return velocity;
	}
	
}
