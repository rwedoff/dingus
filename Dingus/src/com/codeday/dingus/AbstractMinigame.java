package com.codeday.dingus;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public abstract class AbstractMinigame extends AbstractScreen 
{

	public AbstractMinigame(Dingus game, AbstractWorld world, long millisecondLength) 
	{
		super(game);
		stage = world;
		((AbstractWorld)stage).setTime(millisecondLength);
	}

	protected boolean isGameScreen()
	{
		return true;
	}

	@Override
	public void show()
	{
		super.show();
		
		// add a fade-in effect to the whole stage
		stage.getRoot().getColor().a = 0f;
		stage.getRoot().addAction(Actions.fadeIn(0.5f));
	}
	
	protected void minigameWon()
	{
		game.nextMinigame();
	}
	
	protected void minigameLost()
	{
		game.minigameLost();
	}

	@Override
	public void pause()
	{
		((AbstractWorld)stage).pause();
	}
	
}
