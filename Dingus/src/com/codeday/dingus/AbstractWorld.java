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
	private long minigameLength;
	protected TextureAtlas atlas;
	private Image timerBar;
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
		foregroundTimer.setSize(getWidth() / 2 - 20, getHeight() / 20 - 10);
		foregroundTimer.setPosition(getWidth() / 2 - foregroundTimer.getWidth() / 2, getHeight() - foregroundTimer.getHeight() * 2.5f);
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
