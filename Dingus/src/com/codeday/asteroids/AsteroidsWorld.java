package com.codeday.asteroids;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

import dingusInterfaces.DingusConsts;


public class AsteroidsWorld extends AbstractWorld
{

	private ArrayList<Asteroid> asteroidList;
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
		asteroidList = new ArrayList<Asteroid>();
			
		for(int i = 0; i < 10; i++)
		{
			Asteroid a = new Asteroid(atlas.findRegion("meteorBig"), this);
			a.setPosition((float)Math.random()*this.getWidth() , (float)Math.random()*this.getHeight() + this.getHeight()  - a.getWidth()/2);
			a.setRotation((float)Math.random()*360);
			asteroidList.add(a);
			addActor(a);
		}
			
		
		
		//Makes Spaceship
		s = new SpaceShip(atlas.findRegion("DingusShip"), this);
		addActor(s);
		s.setPosition(getWidth() / 2 - s.getWidth() / 2, s.getWidth()/2);

	
		//Creates Text
		Image dodge = new Image(atlas.findRegion("Dodge"));
		dodge.setPosition(this.getWidth()/2 - dodge.getWidth()/2, this.getHeight()/2);
		addActor(dodge);
	}

	public ArrayList<Asteroid> getAsteroids()
	{
		return asteroidList;
	}
	
	
	
	
	private void spaceControls(float delta)
	{
		
		float thrust = 0;
		
		float tilt = Gdx.input.getAccelerometerY();
		if(tilt > 0)
		{
			if(s.getX() < this.getWidth()-s.getHeight())
			{
				thrust += 200 * tilt;
			}
		
		}
		if(tilt < 0)
		{
			if(s.getX() > 0 + s.getHeight())
				thrust += 200* tilt;
		}
		
		if (controller.isKeyDown(Keys.RIGHT) && (!Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen) || Gdx.input.isTouched()))
		{
			if(s.getX() < this.getWidth()-s.getHeight())
				thrust = 300;
		}
		if (controller.isKeyDown(Keys.LEFT) && (!Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen) || Gdx.input.isTouched()))
		{
			if(s.getX() > 0)
				thrust = -300;
		}
		
			s.update( delta, this, thrust);
		
		
			
	
		
	}

	
	
	public void act(float delta)
	{
		super.act(delta);
		
			spaceControls(delta);
		
		
		
		
		
		//Checks to see if lost, then goes to load screen
		if(SpaceShip.collided())
		{
			System.out.println("LOOSSSSEEEE");
			game.minigameLost();
		}
		SpaceShip.collided = false;
		
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
		game.minigameWon(DingusConsts.ASTEROIDS_SCORE);
	}

}
