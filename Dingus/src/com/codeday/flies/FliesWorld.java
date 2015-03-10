package com.codeday.flies;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.codeday.asteroids.Asteroid;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Dingus;
import com.codeday.escape.Blob;

import dingusInterfaces.DingusConsts;

public class FliesWorld extends AbstractWorld
{

	private ArrayList<Fly> flies;

	
	
	public FliesWorld(Dingus game) {
		super(game);
	
	}

	@Override
	protected void setupLevel() {
	
		//Creates Background
		int tileWidth = atlas.findRegion("SkyBackground").getRegionWidth();
		int tileHeight = atlas.findRegion("SkyBackground").getRegionHeight();
		Image background;
		for (int x = 0; x * tileWidth < this.getWidth(); x++)
		{
			for (int y = 0; y * tileHeight < this.getHeight(); y++)
			{

				background = new Image(this.getAtlas().findRegion(
						"SkyBackground"));
				background.setPosition(x * tileWidth, y * tileHeight);
				addActor(background);
			}
		}
		
		//Creates Floor
		int floorWidth = atlas.findRegion("grass").getRegionWidth();
		int floorHeight = atlas.findRegion("grass").getRegionHeight();
		Image floor;
		for (int j = 0; j * floorWidth < this.getWidth(); j++)
		{
			floor = new Image(this.getAtlas().findRegion("grass"));
			floor.setPosition(j * floorWidth, 0);
			addActor(floor);
		}
		
		
		
		//Creates Cloud
		for(int k = 0; k < 7; k++)
		{
			Blob cloud = new Blob(atlas.findRegion("cloud1"), this, -50);
			cloud.setPosition((float)Math.random() * this.getWidth() ,(float) Math.random() * this.getHeight() + (floorHeight*3) );
			cloud.setRotation(0);
			
			addActor(cloud);
		}
		
		//Creates Fly
		flies = new ArrayList<Fly>();
		
		for(int i= 0; i < 4; i++)
		{
			
			Fly f = new Fly(atlas.findRegion("Fly"), this);
			
			f.setPosition( (float)Math.random()*this.getWidth()/2 - f.getHeight() + this.getWidth()/3 ,(float)Math.random()*this.getHeight()/2 + f.getHeight() + this.getHeight()/5);
			f.setRotation(0);
			
			flies.add(f);
			addActor(f);
			if(f.isRight())
				f.setScale(-1f,1f);
		}
		
		//Creates Dingus
		Image dingus = new Image(atlas.findRegion("p1_hurt"));
		dingus.setPosition(this.getWidth()/2 - dingus.getHeight(), floorHeight);
		addActor(dingus);
		
		//Creates Ship
		Image ship = new Image(atlas.findRegion("enemyUFO"));
		ship.setPosition(this.getWidth()- 2*ship.getHeight(), floorHeight);
		addActor(ship);
		
		//Creates Plant
		for(int pl=0; pl < 8; pl++)
		{
		Image plant = new Image(atlas.findRegion("plant"));
		plant.setPosition((float)Math.random()*this.getWidth()- 2*plant.getHeight(), floorHeight);
		addActor(plant);
		}
		//Creates Shroom
		for(int sh = 0; sh < 4; sh++)
		{
			Image shroom = new Image(atlas.findRegion("shroom"));
			shroom.setPosition((float)Math.random()*this.getWidth(), floorHeight);
			addActor(shroom);
		}
		
		//Creates Text
		Image kill = new Image(atlas.findRegion("Kill"));
		kill.setPosition(this.getWidth()/4 , this.getHeight()/1.5f);
		addActor(kill);
	}
	
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println(screenX + " , " + screenY);
		
		for (int i = 0; i < getFlies().size(); )
		{
			Fly f = getFlies().get(i);
			if (f.clicked(screenX, getHeight() - screenY))
			{
				System.out.println("OH NO!");
				f.remove();
				flies.remove(i);
			}
			else
				i++;
		}
		
		
		return false;
		
		
		
		
	}
	public ArrayList<Fly> getFlies()
	{
		return flies;
	}
	@Override
	public void pause() {
	
		// TODO Auto-generated method stub
	}

	@Override
	protected void minigameOver()
	{
		if(flies.isEmpty())
		{
			game.minigameWon(DingusConsts.FLIES_SCORE);
			
		}
		else
		{
			System.out.println("LOOSSSSEEEE");
			game.minigameLost();
			
		}
		
	}

}
