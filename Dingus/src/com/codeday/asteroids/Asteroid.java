package com.codeday.asteroids;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

public class Asteroid extends Image
{
	private Vector2 velocity;
	private AbstractWorld world;
	private ShapeRenderer renderer = new ShapeRenderer();
	
	public Asteroid(TextureRegion texture, AbstractWorld world)
	{
		super(texture);
		this.world = world;
		
		//Sets direction of the asteroids
		velocity = new Vector2(0, (float) Math.sin(Math.toRadians(-90))* 225);
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
	
	/*public void draw(SpriteBatch batch, float parentAlpha)
	{
		if (Dingus.DEV_MODE)
		{
			batch.end();
			renderer.setProjectionMatrix(batch.getProjectionMatrix());
			renderer.setTransformMatrix(batch.getTransformMatrix());
			renderer.translate(getX(), getY(), 0);

			renderer.begin(ShapeType.Line);
			renderer.circle(getOriginX(), getOriginY(), getWidth() / 2);
			renderer.end();
			batch.begin();
		}
		super.draw(batch, parentAlpha);
	}
	*/
	public float getCollisionRadius()
	{
		return getHeight() / 2;
	}
	

	public Vector2 getVelocity()
	{
		return velocity;
	}
}
