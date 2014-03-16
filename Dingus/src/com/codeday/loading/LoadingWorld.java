package com.codeday.loading;


import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.dingus.Laser;
import com.codeday.dingus.MenuScreen;
import com.codeday.dingus.SoundManager.SoundEffect;

public class LoadingWorld extends AbstractWorld
{
	private SpriteWalker walker;
	private int oldLives;
	private int newLives;
	private ArrayList<SpriteWalker> sprites = new ArrayList<SpriteWalker>();
	
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
		if(oldLives==0 && newLives == 0)//if skipped constructor, is first time
		{
			return;
		}
		
		sprites = new ArrayList<SpriteWalker>();
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
		
		System.out.println(newLives + ", " + oldLives);
		for (int i = 0; i < newLives; i++)
		{
			walker = new SpriteWalker(regions, .125f);
			walker.setPosition( (i-1.5f)*walker.getWidth() + this.getWidth()/2, 
					getHeight() / 2 - walker.getHeight() / 2);
			addActor(walker);
			sprites.add(walker);
		}
		
		if (newLives < oldLives && newLives>-1)
		{
			Image stopped = new Image(regions[0]);
			stopped.setPosition((newLives-1.5f)*stopped.getWidth() + this.getWidth()/2, 
					getHeight() / 2 - stopped.getHeight() / 2);
			stopped.addAction(Actions.sequence(Actions.fadeOut(1.5f), Actions.removeActor()));
			addActor(stopped);
		}

		if(newLives == 0)
		{
			gameOver();
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
	
	private void gameOver()
	{
		Image gameOver = new Image(this.getAtlas().findRegion("gameOver"));
		gameOver.setSize(game.getWidth() * .75f, game.getWidth() / 4);
		gameOver.setPosition(game.getWidth() / 2 - gameOver.getWidth() / 2,
				game.getHeight() / 2 - gameOver.getHeight() / 2);

		addActor(gameOver);
		game.getSoundManager().play(SoundEffect.GAME_OVER);

		gameOver.addAction(Actions.sequence(Actions.fadeIn(.25f),
		Actions.delay(3f), Actions.fadeOut(.25f), new Action()
		{

			@Override
			public boolean act(float delta)
			{
				game.setScreen(new MenuScreen(game));
				return true;
			}
			
		}));
		
		oldLives = 0;
	}
	
	public void act(float delta)
	{
		super.act(delta);
			
		for (int i = 0; i < sprites.size();i++)
		{
			SpriteWalker w = sprites.get(i);
			w.update(delta);
		}
	}

}
