package com.codeday.selfdestructminigame;

import com.badlogic.gdx.Gdx;
import com.codeday.dingus.AbstractMinigame;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;

public class SelfDestructMinigame extends AbstractMinigame
{

	public SelfDestructMinigame(Dingus game, AbstractWorld world,long millisecondLength) 
	{
		super(game, world, millisecondLength);
	}
	@Override
	public void dispose()
	{
		super.dispose();
		Gdx.app.log(Dingus.LOG, "Disposing game");


	}
}
