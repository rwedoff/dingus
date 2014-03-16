package com.codeday.loading;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

public class LoadingWorld extends AbstractWorld
{
	private SpriteWalker walker;
	private int oldLives;
	private int newLives;
	private TextureRegion sheet;
	
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
			image.setPosition( (i-1.5f)*image.getWidth() + this.getWidth()/2, 
					getHeight() / 2 - image.getHeight() / 2 - 100);
			addActor(image);
		}
		if (newLives < oldLives)
		{
			Image image = new Image(atlas.findRegion("player"));
			image.setPosition( (newLives-1.5f) * image.getWidth() + this.getWidth()/2, 
					getHeight() / 2 - image.getHeight() / 2 - 100);
			image.addAction(Actions.sequence(Actions.fadeOut(1.5f), Actions.removeActor()));
			addActor(image);
		}
		
		atlas = new TextureAtlas(Gdx.files.internal("images/pages-info.atlas"),
				Gdx.files.internal("images"));
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
		walker = new SpriteWalker(regions);
		walker.setPosition(getWidth() / 2 - walker.getWidth()/2, getHeight() / 2 - walker.getHeight()/2);
		this.addActor(walker);
		
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
		walker.update(delta,this);
	}

}
