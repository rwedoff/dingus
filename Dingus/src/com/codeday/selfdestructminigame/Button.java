package com.codeday.selfdestructminigame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.codeday.dingus.AbstractWorld;
import com.codeday.dingus.Point;

public class Button extends Image
{
	private AbstractWorld world;

	public Button(TextureRegion texture, AbstractWorld world)
	{
		super(texture);
		this.world = world;
	}
	
	public boolean clicked(float x, float y)
	{
		Point point = new Point(x, y);
		Point center = new Point(getX() + getImageWidth() / 2, getY() + getImageHeight() / 2);
		System.out.println("Dist: " + point.distance(center));
		if (point.distance(center) < getImageHeight() / 2)
			return true;
		return false;
	}

}
