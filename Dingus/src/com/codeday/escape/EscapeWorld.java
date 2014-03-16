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
				//Background
				int tileWidth = atlas.findRegion("circuitBoard2").getRegionWidth();
				int tileHeight = atlas.findRegion("circuitBoard2").getRegionHeight();
				Image background;
				for (int x = 0; x * tileWidth < this.getWidth(); x++)
				{
					for (int y = 0; y * tileHeight < this.getHeight(); y++)
					{

						background = new Image(this.getAtlas().findRegion(
								"circuitBoard2"));
						background.setPosition(x * tileWidth, y * tileHeight);
						addActor(background);
					}
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
