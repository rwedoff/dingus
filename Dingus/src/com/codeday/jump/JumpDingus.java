package com.codeday.jump;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.asteroids.AsteroidsWorld;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Point;

public class JumpDingus  extends Image
{
	private ShapeRenderer renderer = new ShapeRenderer();
	private float playerAngle;
	private Vector2 velocity;
	public static boolean collided = false;
	
	
	public JumpDingus(TextureRegion texture, AbstractWorld world)
	{
		
		
		super(texture);

		setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
	
	public void update(float delta, JumpWorld world)
	{
		System.out.println("test");
		Point cCenter = new Point(world.getCar().getX() + world.getCar().getWidth()/2 , world.getCar().getY() + world.getCar().getHeight()/2);
		Point pCenter = new Point(this.getX() + this.getWidth()/2, this.getY() + this. getHeight()/2);
		//Inntiates YOU LOSE
		if(cCenter.distance(pCenter) < this.getWidth()/2 + world.getCar().getHeight()/2)
		{
			System.out.println("Car Collided");
			collided = true;
			System.out.println(collided);

		}
	}
	public static boolean collided()
	{
		return collided;
	}
		
}
