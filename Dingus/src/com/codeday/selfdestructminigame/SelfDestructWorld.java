package com.codeday.selfdestructminigame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.loading.SpriteWalker;

public class SelfDestructWorld extends AbstractWorld
{

	private Button button;
	private SpriteWalker walker;
	private SpriteWalker explosion;

	public SelfDestructWorld(Dingus game) 
	{
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
		
		button = new Button(atlas.findRegion("RedButton-Active"), this);
		addActor(button);
		button.setPosition(getWidth() / 2 - button.getWidth() / 2, getHeight()/ 2 - button.getWidth()/2);
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println(screenX + " , " + screenY);
		if (this.button.clicked(screenX, getHeight() - screenY))
		{
			System.out.println("OH NO!");
			
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
			explosion = new SpriteWalker(regions2, .05f);
			explosion.setPosition( -1*explosion.getWidth()/2 + this.getWidth()/2, 
					getHeight() / 2 - explosion.getHeight() / 2);
			explosion.addAction(Actions.sequence(Actions.delay(1f), new Action()
			{
				public boolean act(float delta) 
				{
					game.minigameLost();
					return true;
				}
				
			}));
			addActor(explosion);
				
		}
		return true;
	}
	
	public void act(float delta)
	{
		super.act(delta);
		
		if (explosion != null)
		{
			//explosion.update(delta);
		}
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void minigameOver() 
	{
		game.minigameWon();
	}
	

}
