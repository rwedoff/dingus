package com.codeday.loading;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.dingus.Laser;

public class LoadingWorld extends AbstractWorld
{
	private SpriteWalker walker;
	private int oldLives;
	private int newLives;
	private TextureRegion sheet;
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
			walker = new SpriteWalker(regions, .15f);
			walker.setPosition( (i-1.5f)*walker.getWidth() + this.getWidth()/2, 
					getHeight() / 2 - walker.getHeight() / 2);
			addActor(walker);
			sprites.add(walker);
		}
		
		if (newLives < oldLives)
		{
			Image stopped = new Image(regions[0]);
			stopped.setPosition((newLives-1.5f)*walker.getWidth() + this.getWidth()/2, 
					getHeight() / 2 - walker.getHeight() / 2);
			stopped.addAction(Actions.sequence(Actions.fadeOut(1.5f), Actions.removeActor()));
			addActor(stopped);
			System.out.println("Hello!");
		}

		TextureRegion[] regions2 = 
			{
				new TextureRegion(atlas.findRegion("Explosion1")),
				new TextureRegion(atlas.findRegion("Explosion2")),
				new TextureRegion(atlas.findRegion("Explosion3")),
				new TextureRegion(atlas.findRegion("Explosion4")),
				new TextureRegion(atlas.findRegion("Explosion5")),
				new TextureRegion(atlas.findRegion("Explosion6")),
				new TextureRegion(atlas.findRegion("Explosion7")),
				new TextureRegion(atlas.findRegion("Explosion8")),
				new TextureRegion(atlas.findRegion("Explosion9")),
				new TextureRegion(atlas.findRegion("Explosion10")),
				new TextureRegion(atlas.findRegion("Explosion11")),
				new TextureRegion(atlas.findRegion("Explosion12")),
				new TextureRegion(atlas.findRegion("Explosion13")),
				new TextureRegion(atlas.findRegion("Explosion14")),
				new TextureRegion(atlas.findRegion("Explosion15")),
				new TextureRegion(atlas.findRegion("Explosion16")),
				new TextureRegion(atlas.findRegion("Explosion17")),
				new TextureRegion(atlas.findRegion("Explosion18")),
				new TextureRegion(atlas.findRegion("Explosion19")),
				new TextureRegion(atlas.findRegion("Explosion20")),
				};
		walker = new SpriteWalker(regions2, .05f);
		walker.setPosition( -1*walker.getWidth()/2 + this.getWidth()/2, 
				getHeight() / 2 - walker.getHeight() / 2);
		System.out.println(sprites);
		sprites.add(walker);
		addActor(walker);
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
	
	public void act(float delta)
	{
		super.act(delta);
			
		for (int i = 0; i < sprites.size();i++)
		{
			SpriteWalker w = sprites.get(i);
			w.update(delta, this);
		}
	}

}
