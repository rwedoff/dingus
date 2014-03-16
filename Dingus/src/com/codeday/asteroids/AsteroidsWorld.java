package com.codeday.asteroids;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Asteroid;
import com.codeday.dingus.Dingus;
import com.codeday.dingus.World;
import com.codeday.selfdestructminigame.Button;

public class AsteroidsWorld extends AbstractWorld
{

	private ArrayList<asteroids> asteroidList = new ArrayList<asteroids>();
	private SpaceShip ship;
	private SpaceShip s;
	
	
	public AsteroidsWorld(Dingus game) {
		
		super(game);
		
	}

	@Override
	protected void setupLevel() 
	{
		//Makes Background
		int tileWidth = atlas.findRegion("starBackground").getRegionWidth();
		int tileHeight = atlas.findRegion("starBackground").getRegionHeight();
		Image background;
		for (int x = 0; x * tileWidth < this.getWidth(); x++)
		{
			for (int y = 0; y * tileHeight < this.getHeight(); y++)
			{

				background = new Image(this.getAtlas().findRegion(
						"starBackground"));
				background.setPosition(x * tileWidth, y * tileHeight);
				addActor(background);
			}
		}
		
		//Makes Asteroids
		asteroidList = new ArrayList<asteroids>();
			
		for(int i = 0; i < 10; i++)
		{
		asteroids a = new asteroids(atlas.findRegion("meteorBig"), this);
		addActor(a);
		a.setPosition((float)Math.random()*this.getWidth() , (float)Math.random()*this.getHeight() + getHeight() - a.getWidth()/2);
		asteroidList.add(a);
		a.setRotation((float)Math.random()*360);
		}
		
		
		
		//Makes Spaceship
		s = new SpaceShip(atlas.findRegion("enemyUFO"), this);
		addActor(s);
		s.setPosition(getWidth() / 2 - s.getWidth() / 2, s.getWidth()/2);
	}

	
	
	
	
	
	private void spaceControls(float delta)
	{
		float rotate = 0;
		if (controller.isKeyDown(Keys.LEFT))
			rotate += 270;
		if (controller.isKeyDown(Keys.RIGHT))
			rotate -= 270;
		
		rotate -= 45 * Gdx.input.getAccelerometerY();
		
		float thrust = 0;
		if (controller.isKeyDown(Keys.UP) || (!Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen) || Gdx.input.isTouched()))
		{
			
			thrust = 2;
		}
		if (controller.isKeyDown(Keys.DOWN) && (!Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen) || Gdx.input.isTouched()))
		{
			
			thrust = -2;
		}
		s.update( delta, this, rotate,  thrust, asteroidList);

		

		
		
		
	}

	
	public void act(float delta)
	{
		super.act(delta);
		spaceControls(delta);
	}
	@Override
	public boolean keyDown(int keycode)
	{
		controller.setKeyDown(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		controller.setKeyUp(keycode);
		return true;
	}
	
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() {
		game.minigameWon();
		
	}

}
