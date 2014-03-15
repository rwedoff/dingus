package com.codeday.selfdestructminigame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

public class SelfDestructWorld extends AbstractWorld
{

	private Button button;

	public SelfDestructWorld(Dingus game) 
	{
		super(game);
	}

	@Override
	protected void setupLevel() 
	{
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
		
		button = new Button(atlas.findRegion("selfDestruct"), this);
		addActor(button);
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println(screenX + " , " + screenY);
		if (this.button.clicked(screenX, getHeight() - screenY))
		{
			System.out.println("OH NO!");
		}
		return true;
	}
	
	public void act(float delta)
	{
		super.act(delta);
		
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() {
		
	}
	

}
