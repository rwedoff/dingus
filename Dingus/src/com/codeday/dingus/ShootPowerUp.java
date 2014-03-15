package com.codeday.dingus;



import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ShootPowerUp extends PowerUp 
{
	protected Player player;
	public enum SHOOT_POWERUP
	{
		SIDE_SHOT,
		BACK_SHOT,
		RAPID_SHOT,
	}


	private SHOOT_POWERUP type;
	public ShootPowerUp(TextureRegion texture, World world, Player player)
	{
		super(texture, world, player);
	}
	public ShootPowerUp(Drawable texture, World world)
	{
		super(texture, world);
	}
	
	protected WrapAroundImage createChild() 
	{
		return new ShootPowerUp(getDrawable(), world);
	}
	
	public void activate(Player player)
	{
		double rand = Math.random();
	
		if(rand <= .50)
		{
			time = new Timer(15 * 1000);
			type = SHOOT_POWERUP.BACK_SHOT;
			System.out.println("Back shot");
			
			Player.backShoot = true;
		}
		else if(rand >= .50 && rand <= .90)
		{
			time = new Timer(10 * 1000);
			type = SHOOT_POWERUP.SIDE_SHOT;
			System.out.println("side shot");;
			Player.sideShot = true;
		}
		else
		{
			time = new Timer(5 * 1000);
			type = SHOOT_POWERUP.RAPID_SHOT;
			System.out.println("Rapid shot");
			Player.rapidShoot = true;
		}
	}
	@Override
	public void deactivate()
	{
		if (type.equals(SHOOT_POWERUP.BACK_SHOT))
		{
			Player.backShoot = false;
		}
		if (type.equals(SHOOT_POWERUP.RAPID_SHOT))
		{
			Player.rapidShoot = false;
		}
		if (type.equals(SHOOT_POWERUP.SIDE_SHOT))
		{
			Player.sideShot = false;
		}
	}


		
}
