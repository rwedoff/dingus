package com.codeday.dingus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractWorld extends Stage implements InputProcessor 
{
	protected WorldController controller;
	protected Dingus game;
	private long startTime;
	private long minigameLength;
	protected TextureAtlas atlas;

	public AbstractWorld(Dingus game)
	{
		this.game = game;
		this.controller = new WorldController();
		Gdx.input.setInputProcessor(this);
		startTime = System.currentTimeMillis();
		
		String textureFile = "images/pages-info.atlas";
		atlas = new TextureAtlas(Gdx.files.internal(textureFile),
				Gdx.files.internal("images"));

		setupLevel();
	}

	protected abstract void setupLevel();

	public abstract void pause();
	
	public void act(float delta)
	{
		super.act(delta);
		
		if (System.currentTimeMillis() - startTime > minigameLength)
		{
			minigameOver();
		}
	}
	
	protected abstract void minigameOver();
	
	public void setTime(long millisecondLength) 
	{
		this.minigameLength = millisecondLength;
	}
	
	public TextureAtlas getAtlas()
	{
		return atlas;
	}
}
