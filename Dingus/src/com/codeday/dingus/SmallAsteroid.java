package com.codeday.dingus;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SmallAsteroid extends Asteroid {
	
	public static float INITIAL_SPEED;

	public SmallAsteroid(TextureRegion texture, World world) {
		super(texture, world);
		
		SmallAsteroid.INITIAL_SPEED = Asteroid.INITIAL_SPEED * 2;
		health = MAX_HEALTH / 2;
	}
	
	public void update(float delta, World world, ArrayList<Laser> lasers, ArrayList<Asteroid> asteroids)
	{
		super.update(delta, world, lasers, asteroids);
		if (destroyed)
			if (Math.random() < 1.2f)
				world.addPowerUp(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
}
