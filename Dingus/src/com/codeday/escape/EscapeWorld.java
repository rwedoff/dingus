package com.codeday.escape;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.asteroids.Asteroid;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

public class EscapeWorld extends AbstractWorld
{

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
				Blob b = new Blob(atlas.findRegion("meteorBig"), this);
				b.setPosition((float)Math.random()*this.getWidth() , (float)Math.random()*this.getHeight() + this.getHeight()  - b.getWidth()/2);
				b.setRotation((float)Math.random()*360);
				
				addActor(b);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() {
		// TODO Auto-generated method stub
		
	}

}
