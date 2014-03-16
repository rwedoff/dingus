package com.codeday.asteroids;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.dingus.Point;

public class SpaceShip extends Image
{

	private ShapeRenderer renderer = new ShapeRenderer();
	private float playerAngle;
	private Vector2 velocity;
	public static boolean collided = false;
	
	public SpaceShip(TextureRegion texture, AbstractWorld world)
	{
		
		
		super(texture);

		setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
		
	}
	
	
	
	
	public void update(float delta, AsteroidsWorld world,  float thrust)
	{
		
		//Player Movement
		float dxA = (float) (Math.cos(Math.toRadians(playerAngle)) * thrust);
		float dyA = (float) (Math.sin(Math.toRadians(playerAngle)) * thrust);

		float xPotential = getX() + dxA * delta;
		float yPotential = getY() + dyA * delta;
		setX(xPotential);
		setY(yPotential);
	
		
		//Collision Detection
		
		for(Asteroid asteroid : world.getAsteroids())
		{
			Point aCenter = new Point(asteroid.getX() + asteroid.getWidth()/2 , asteroid.getY() + asteroid.getHeight()/2);
			Point pCenter = new Point(this.getX() + this.getWidth()/2, this.getY() + this. getHeight()/2);
			
			//Inntiates YOU LOSE
			if(aCenter.distance(pCenter) < this.getWidth()/2 + asteroid.getHeight()/2)
			{
				System.out.println("Asteroid Collided");
				collided = true;
				System.out.println(collided);

			}
		}
	
		
	}
	
	public static boolean collided()
	{
		return collided;
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

	public Vector2 getVelocity()
	{
		return velocity;
	}
	
}
