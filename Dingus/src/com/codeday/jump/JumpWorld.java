package com.codeday.jump;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.asteroids.SpaceShip;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.escape.Blob;

import dingusInterfaces.DingusConsts;

public class JumpWorld extends AbstractWorld
{
	private int clicked = 0;
	private Car car;
	private JumpDingus dingus;
	
	public JumpWorld(Dingus game) {
		super(game);
			}

	@Override
	protected void setupLevel() 
	{
		//Creates Background
				int tileWidth = atlas.findRegion("SkyBackground").getRegionWidth();
				int tileHeight = atlas.findRegion("SkyBackground").getRegionHeight();
				Image background;
				for (int x = 0; x * tileWidth < this.getWidth(); x++)
				{
					for (int y = 0; y * tileHeight < this.getHeight(); y++)
					{

						background = new Image(this.getAtlas().findRegion(
								"SkyBackground"));
						background.setPosition(x * tileWidth, y * tileHeight);
						addActor(background);
					}
				}
				
				//Creates Floor
				int floorWidth = atlas.findRegion("block").getRegionWidth();
				int floorHeight = atlas.findRegion("block").getRegionHeight();
				Image floor;
				for (int j = 0; j * floorWidth < this.getWidth(); j++)
				{
					floor = new Image(this.getAtlas().findRegion("block"));
					floor.setPosition(j * floorWidth, 0);
					addActor(floor);
				}
				
				
				
				//Creates Cloud
				for(int k = 0; k < 7; k++)
				{
					Blob cloud = new Blob(atlas.findRegion("cloud1"), this, -50);
					cloud.setPosition((float)Math.random() * this.getWidth() ,(float) Math.random() * this.getHeight() + (floorHeight*3) );
					cloud.setRotation(0);
					
					addActor(cloud);
				}
				
				//Creates  Car
				car = new Car(atlas.findRegion("Car"), this, 500);
				car.setPosition(0, floorHeight);
				car.setRotation(0);
				addActor(car);
		
				//Creates Dingus
				dingus = new JumpDingus(atlas.findRegion("playerSprite1"), this);
				addActor(dingus);
				dingus.setPosition(getWidth() / 2 - dingus.getWidth() / 2, floorHeight);
				
				
				//Creates Text
				Image text = new Image(this.getAtlas().findRegion("Quick"));
				text.setPosition(this.getWidth()/2, this.getHeight()/2);
				addActor(text);
	}

	public Car getCar()
	{
		return car;
	}
	
	
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println(screenX + " , " + screenY);
		clicked++;
		return true;
	
	}
	public void act(float delta)
	{
		super.act(delta);
		
			dingus.update(delta, this);
			
		//Checks to see if lost, then goes to load screen
		if(dingus.collided())
		{
			System.out.println("LOOSSSSEEEE");
			game.minigameLost();
		}
		else if(clicked >= 1)
		{
			game.minigameWon(DingusConsts.JUMP_SCORE);
			clicked =0;
		}
		
		dingus.collided = false;
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() 
	{
		if(clicked < 1)
		{
			System.out.println("LOOSSSSEEEE");
			game.minigameLost();
			clicked = 0;
		}
		else
		{
			game.minigameWon(DingusConsts.JUMP_SCORE);
			clicked =0;
		}
		
	}

}
