package com.codeday.dingus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class AbstractWorld extends Stage implements InputProcessor 
{
	protected WorldController controller;
	protected Dingus game;
	private long startTime;
	protected long minigameLength;
	protected TextureAtlas atlas;
	private float initialWidth;
	private Image foregroundTimer;

	public AbstractWorld(Dingus game)
	{
		this.game = game;
		this.controller = new WorldController();
		Gdx.input.setInputProcessor(this);
		startTime = System.currentTimeMillis();
		
		String textureFile = "images/pages-info.atlas";
		atlas = new TextureAtlas(Gdx.files.internal(textureFile),
				Gdx.files.internal("images"));

		Image backgroundTimer = new Image(atlas.findRegion("backgroundTimerBar"));
		backgroundTimer.setSize(getWidth() / 2, getHeight() / 20);
		backgroundTimer.setPosition(getWidth() / 2 - backgroundTimer.getWidth() / 2, getHeight() - backgroundTimer.getHeight() * 2);
		
		foregroundTimer = new Image(atlas.findRegion("foregroundTimerBar"));
		foregroundTimer.setSize(getWidth() / 2, getHeight() / 20);
		foregroundTimer.setPosition(backgroundTimer.getX(), backgroundTimer.getY());
		initialWidth = foregroundTimer.getWidth();
		
		setupLevel();

		addActor(backgroundTimer);
		addActor(foregroundTimer);
	}

	protected abstract void setupLevel();

	public abstract void pause();
	
	public void act(float delta)
	{
		super.act(delta);
		
		foregroundTimer.setWidth((float) (initialWidth * (1 - ((double)(System.currentTimeMillis() - startTime) / (double)(minigameLength)))));
		
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
