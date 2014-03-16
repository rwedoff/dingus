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
	private TextureRegion[] regions;
	private Texture sheet;
	private float stateTime;
	public SpriteWalker(TextureRegion[] regions)
	{	
		super(regions[0]);
	    
	    walkAnimation = new Animation(.125f,regions);
	}
	
	public void update(float delta, LoadingWorld world)
	{
        TextureRegion frame;

        // find the appropriate frame of the tilt animation to be drawn
        frame = walkAnimation.getKeyFrame( (stateTime += delta)%1, false );
		
        // there is no performance issues when setting the same frame multiple
        // times as the current region (the call will be ignored in this case)
        this.setDrawable(new TextureRegionDrawable(frame));
	}
}
