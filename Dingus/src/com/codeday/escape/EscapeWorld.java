package com.codeday.escape;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.asteroids.Asteroid;
import com.codeday.asteroids.SpaceShip;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.loading.SpriteWalker;


public class EscapeWorld extends AbstractWorld
{


	private int clicked = 0;
	private SpriteWalker player;
	
	
	public EscapeWorld(Dingus game) {
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
				int floorWidth = atlas.findRegion("ground_cave").getRegionWidth();
				int floorHeight = atlas.findRegion("ground_cave").getRegionHeight();
				Image floor;
				for (int j = 0; j * floorWidth < this.getWidth(); j++)
				{
					floor = new Image(this.getAtlas().findRegion("ground_cave"));
					floor.setPosition(j * floorWidth, 0);
					addActor(floor);
				}
				
				
				//Creates Blob
				for(int k = 0; k < 7; k++)
				{
					Blob b = new Blob(atlas.findRegion("Blob"), this, 225);
					b.setPosition((float)Math.random() * -this.getWidth()/2 , floorHeight);
					b.setRotation(0);
					addActor(b);
				}
				//Creates Dingus
				TextureRegion[] regions = 
					{
						new TextureRegion(atlas.findRegion("playerSprite1")),
						new TextureRegion(atlas.findRegion("playerSprite2")),
						new TextureRegion(atlas.findRegion("playerSprite3")),
						new TextureRegion(atlas.findRegion("playerSprite4")),
						new TextureRegion(atlas.findRegion("playerSprite5")),
						new TextureRegion(atlas.findRegion("playerSprite6")),
						new TextureRegion(atlas.findRegion("playerSprite7")),
						new TextureRegion(atlas.findRegion("playerSprite8")),
					};
				
				player = new SpriteWalker(regions, .125f);
				player.setPosition(game.getWidth()/6 - player.getWidth()/2, floorHeight);
				addActor(player);
				
				
				//Creates Space Ship
				Image ship = new Image(atlas.findRegion("enemyUFO"));
				ship.setPosition(this.getWidth() - ship.getHeight(), floorHeight);
				addActor(ship);
				
				//Creates Text
				Image tap = new Image(atlas.findRegion("Tap"));
				tap.setPosition(this.getWidth()/2 - tap.getWidth()/2, this.getHeight()/2);
				addActor(tap);
				
				//Creates Cloud
				for(int k = 0; k < 7; k++)
				{
					Blob cloud = new Blob(atlas.findRegion("cloud1"), this, -50);
					cloud.setPosition((float)Math.random() * this.getWidth() ,(float) Math.random() * this.getHeight() + (floorHeight*3) );
					cloud.setRotation(0);
					
					addActor(cloud);
				}
				
				
				
				
	}

	
	

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println(screenX + " , " + screenY);
		
			
			clicked++;
			System.out.println("Clicked: "+ clicked);			
			//Checks to see if lost, then goes to load screen
			float xPotential = player.getX() + 50;
			player.setX(xPotential);
		return true;
	
	}
	public void act(float delta)
	{
		super.act(delta);
				
		player.update(delta);
		/*float xPotential = player.getX() + 230 * delta;
		player.setX(xPotential);*/
			
	}
	
	
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() 
	{
		if(clicked < 15)
		{
			System.out.println("LOOSSSSEEEE");
			game.minigameLost();
			clicked = 0;
		}
		else
		{
			game.minigameWon();
			clicked =0;
		}
		
	}

}
