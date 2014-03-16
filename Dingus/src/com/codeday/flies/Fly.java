package com.codeday.flies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Point;

public class Fly extends Image
{
	private Drawable left;
	
	private Drawable right;
	private Vector2 velocity;
	private AbstractWorld world;
	private ShapeRenderer renderer = new ShapeRenderer();
	private float INITIAL_SPEED = 200f;
	private float direction;
	
	public Fly(TextureRegion left, AbstractWorld world)
	{
		super(left);
		this.world = world;
		
		this.left = new TextureRegionDrawable(left);
		
		
		float num = ((float) Math.random() * 360);
		float num2 = ((float) Math.random() * 360);
		direction = num;
		//Sets direction of the asteroids
		velocity = new Vector2((float) Math.sin(Math.toRadians(num))* 50, (float) Math.sin(Math.toRadians(num2))* 50);
		setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);		
		renderer = new ShapeRenderer();
	
	}
	public boolean isRight()
	{
		
		if(direction < 180)
			return true;
		else
			return false;
	}
	
	public void randomizeDirection()
	{
		float num = ((float) Math.random() * 360);
		velocity =(new Vector2((float) Math.cos(Math.toRadians(num))* INITIAL_SPEED, (float) Math.sin(Math.toRadians(num))* INITIAL_SPEED ));
	}
	public void act(float delta)
	{
		super.act(delta);
		//Makes the flies move
		float xPotential = getX() + velocity.x * delta;
		float yPotential = getY() + velocity.y * delta;
		setX(xPotential);
		setY(yPotential);
		
		
		
	}
	
	public boolean clicked(float x, float y)
	{
		Point point = new Point(x, y);
		Point center = new Point(getX() + getImageWidth() / 2, getY() + getImageHeight() / 2);
		System.out.println("Dist: " + point.distance(center));
		if (point.distance(center) < getImageHeight() / 2)
			return true;
		return false;
	}
	

}
