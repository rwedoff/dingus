package com.codeday.dingus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ShieldPowerUp extends PowerUp 
{

	protected Player player;
	public ShieldPowerUp(TextureRegion texture, World world, Player player)
	{
		super(texture, world, player);
		time = new Timer(5000);
	}
	public ShieldPowerUp(Drawable texture, World world)
	{
		super(texture, world);
	}
	
	public void deactivate()
	{
		
	}
	
	public void activate(Player player)
	{
		player.addHealth();
	}
	
	protected WrapAroundImage createChild()
	{
		return new ShieldPowerUp(getDrawable(), world);
	}
}
