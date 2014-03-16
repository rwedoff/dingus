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
	
	
	public SpaceShip(TextureRegion texture, AbstractWorld world)
	{
		super(texture);

		
	}
	
	
	
	
	public void update(float delta, AsteroidsWorld world, float rotate, float thrust, ArrayList<asteroids> asteroids)
	{
		rotate(rotate * delta);
		update(delta, rotate);
		
	/*	if (checkCollisionAsteroids(asteroids))
			world.playerDeath(getX() + getWidth() / 2, getY() + getHeight() / 2);*/
		
	}

	public void update(float delta, float rotate)
	{
		
		playerAngle += rotate * delta;
	}
	
	
}
