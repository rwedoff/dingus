package com.codeday.dingus;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.codeday.asteroids.AsteroidsWorld;
import com.codeday.asteroids.asteroidsMinigame;
import com.codeday.escape.EscapeMinigame;
import com.codeday.escape.EscapeWorld;
import com.codeday.flies.FliesMinigame;
import com.codeday.flies.FliesWorld;

import com.codeday.jump.JumpMinigame;
import com.codeday.jump.JumpWorld;
import com.codeday.loading.LoadingScreen;
import com.codeday.loading.LoadingWorld;
import com.codeday.selfdestructminigame.SelfDestructMinigame;
import com.codeday.selfdestructminigame.SelfDestructWorld;

import dingusInterfaces.ActionResolver;

/**
 * The game's main class, called as application events are fired.
 */
public class Dingus extends Game
{
	// constant useful for logging
	public static final String LOG = Dingus.class.getSimpleName();

	// whether we are in development mode
	public static final boolean DEV_MODE = false;

	// a libgdx helper class that logs the current FPS each second
	private FPSLogger fpsLogger;

	// services
	private PreferencesManager preferencesManager;
	
	private MusicManager musicManager;
	private SoundManager soundManager;

	private int width;

	private int height;
	
	private int lives = 3;
	
	private int games = 0;
	
	ActionResolver actionResolver;

	public Dingus(ActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
	}

	// Game-related methods

	

	@Override
	public void create()
	{
		Gdx.app.log(Dingus.LOG, "Creating game on " + Gdx.app.getType());

		// create the preferences manager
		preferencesManager = new PreferencesManager();

		// create the music manager
		musicManager = new MusicManager();
		musicManager.setVolume(preferencesManager.getVolume());
		musicManager.setEnabled(preferencesManager.isMusicEnabled());

	
		
		soundManager = new SoundManager();
		soundManager.setEnabled(true);

		// create the helper objects
		fpsLogger = new FPSLogger();
	}

	
	//SELECTS MINI GAME
	public void nextMinigame() 
	{
		int rand = (int) (Math.random() * 100);
		
		float rand2 = (float) Math.random();
		float offset = 0;
		if (rand < 10 )
		{
			if(games<=6)
				offset = games*(rand2-.5f)*(1200);
			else 
				offset = (rand2-.5f)*(3000);
			setScreen(new SelfDestructMinigame(this, new SelfDestructWorld(this), (long) (5000 + offset) ));
			games++;
		}
		else if(rand > 10 && rand < 30   )
		{
			if(games<=6)
				offset = games*rand2*(800);
			else 
				offset = rand2*(3000);
			setScreen(new asteroidsMinigame(this, new AsteroidsWorld(this), (long) (5000 + offset) ));
			games++;
		}
		
		else if(rand > 30 && rand < 50)
		{
			if(games<=6)
				offset = games*rand2*(-500);
			else 
				offset = rand2*(-3000);
			setScreen(new EscapeMinigame(this, new EscapeWorld(this), (long) (5000 + offset) ));
			games++;
		}
		else if(rand > 50 && rand < 75)
		{
			if(games<=6)
				offset = games*rand2*(-500);
			else 
				offset = rand2*(-3000);
			setScreen(new FliesMinigame(this, new FliesWorld(this), (long) (5000 + offset) ));
		}
		else if(rand > 75)
		{
			setScreen(new JumpMinigame(this, new JumpWorld(this), 5000));
		}
	}

	public void minigameLost() 
	{
		setScreen(new LoadingScreen(this, new LoadingWorld(this, getLives(), getLives() - 1), 3000));
		setLives(getLives() - 1);
	}
	public void minigameWon()
	{
		System.out.println(getLives());
		setScreen(new LoadingScreen(this, new LoadingWorld(this, getLives(), getLives()), 3000));
	}
	


	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		this.width = width;
		this.height = height;
		Gdx.app.log(Dingus.LOG, "Resizing game to: " + width + " x " + height);

		// show the splash screen when the game is resized for the first time;
		// this approach avoids calling the screen's resize method repeatedly
		if (getScreen() == null)
		{
			if (DEV_MODE)
			{
				nextMinigame();
			}
			else
			{
				setScreen(new SplashScreen(this));
			}
		}
	}

	@Override
	public void render()
	{
		super.render();

		// output the current FPS
		if (DEV_MODE)
			fpsLogger.log();
	}

	@Override
	public void pause()
	{
		super.pause();
		Gdx.app.log(Dingus.LOG, "Pausing game");
	}

	@Override
	public void resume()
	{
		super.resume();
		Gdx.app.log(Dingus.LOG, "Resuming game");
	}

	@Override
	public void setScreen(Screen screen)
	{
		super.setScreen(screen);
		Gdx.app.log(Dingus.LOG, "Setting screen: "
				+ screen.getClass().getSimpleName());
	}

	@Override
	public void dispose()
	{
		super.dispose();
		Gdx.app.log(Dingus.LOG, "Disposing game");

		// dipose some services
		musicManager.dispose();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	// Services' getters

	public PreferencesManager getPreferencesManager()
	{
		return preferencesManager;
	}


	public MusicManager getMusicManager()
	{
		return musicManager;
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
