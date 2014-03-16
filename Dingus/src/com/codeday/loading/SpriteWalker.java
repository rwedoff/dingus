package com.codeday.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SpriteWalker extends Image {

	
	public final Animation walkAnimation;
	public TextureRegion[] regions;
	private Texture sheet;
	private float stateTime;
	public SpriteWalker(TextureRegion[] regions, float frametime)
	{	
		super(regions[0]);

        stateTime = (float) Math.random();
	    walkAnimation = new Animation(frametime,regions);
	}
	
	public void act(float delta)
	{
		TextureRegion frame;
        
        // find the appropriate frame of the tilt animation to be drawn
        frame = walkAnimation.getKeyFrame( (stateTime += delta)%1, false );
		
        // there is no performance issues when setting the same frame multiple
        // times as the current region (the call will be ignored in this case)
        this.setDrawable(new TextureRegionDrawable(frame));
	}
	
	public void update(float delta)
	{
        TextureRegion frame;
        
        // find the appropriate frame of the tilt animation to be drawn
        frame = walkAnimation.getKeyFrame( (stateTime += delta)%1, false );
		
        // there is no performance issues when setting the same frame multiple
        // times as the current region (the call will be ignored in this case)
        this.setDrawable(new TextureRegionDrawable(frame));
	}
}
