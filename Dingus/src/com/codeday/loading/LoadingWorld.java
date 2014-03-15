package com.codeday.loading;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

public class LoadingWorld extends AbstractWorld
{

	private int oldLives;
	private int newLives;

	public LoadingWorld(Dingus game, int oldLives, int newLives) 
	{
		super(game);
		this.oldLives = oldLives;
		this.newLives = newLives;
		
		setupLevel();
	}

	@Override
	protected void setupLevel() 
	{
		for (int i = 0; i < getActors().size; )
			getActors().removeIndex(i).remove();
		
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
		System.out.println(newLives + ", " + oldLives);
		for (int i = 0; i < newLives; i++)
		{
			Image image = new Image(atlas.findRegion("player"));
			image.setPosition(100 + i * image.getWidth() + 50, getHeight() / 2 - image.getHeight() / 2);
			addActor(image);
		}
		if (newLives < oldLives)
		{
			Image image = new Image(atlas.findRegion("player"));
			image.setPosition(100 + newLives * image.getWidth() + 50, getHeight() / 2 - image.getHeight() / 2);
			image.addAction(Actions.sequence(Actions.fadeOut(1.5f), Actions.removeActor()));
			addActor(image);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() 
	{
		game.nextMinigame();
	}

}
