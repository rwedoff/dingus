package com.codeday.escape;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
