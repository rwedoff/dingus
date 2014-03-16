package com.codeday.asteroids;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Asteroid;
import com.codeday.dingus.Laser;
import com.codeday.dingus.Player;
import com.codeday.dingus.PowerUp;
import com.codeday.dingus.World;
import com.codeday.dingus.WrapAroundImage;

public class SpaceShip extends Image
{

	private float playerAngle;
	private Vector2 velocity;
	
	public SpaceShip(TextureRegion texture, AbstractWorld world)
	{
		super(texture);

		
	}
	
	
	
	
	public void update(float delta, AsteroidsWorld world,  float thrust, ArrayList<asteroids> asteroids)
	{
		
	
		
		float dxA = (float) (Math.cos(Math.toRadians(playerAngle)) * thrust);
		float dyA = (float) (Math.sin(Math.toRadians(playerAngle)) * thrust);

		float xPotential = getX() + dxA * delta;
		float yPotential = getY() + dyA * delta;
		setX(xPotential);
		setY(yPotential);
		
	/*	if (checkCollisionAsteroids(asteroids))
			world.playerDeath(getX() + getWidth() / 2, getY() + getHeight() / 2);*/
		
	}


	public Vector2 getVelocity()
	{
		return velocity;
	}
	
}
