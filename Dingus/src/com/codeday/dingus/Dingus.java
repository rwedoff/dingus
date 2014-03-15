package com.codeday.dingus;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.codeday.selfdestructminigame.SelfDestructMinigame;
import com.codeday.selfdestructminigame.SelfDestructWorld;

/**
 * The game's main class, called as application events are fired.
 */
public class Dingus extends Game
{
	// constant useful for logging
	public static final String LOG = Dingus.class.getSimpleName();

	// whether we are in development mode
	public static final boolean DEV_MODE = true;

	// a libgdx helper class that logs the current FPS each second
	private FPSLogger fpsLogger;

	// services
	private PreferencesManager preferencesManager;
	private LevelManager levelManager;
	private MusicManager musicManager;
	private SoundManager soundManager;

	private int width;

	private int height;

	public Dingus()
	{
	}

	// Services' getters

	public PreferencesManager getPreferencesManager()
	{
		return preferencesManager;
	}

	public LevelManager getLevelManager()
	{
		return levelManager;
	}

	public MusicManager getMusicManager()
	{
		return musicManager;
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
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

		// create the level manager
		levelManager = new LevelManager();
		
		soundManager = new SoundManager();
		soundManager.setEnabled(true);

		// create the helper objects
		fpsLogger = new FPSLogger();
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
				setScreen(new SelfDestructMinigame(this, new SelfDestructWorld(this), 2000));
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

	public void nextMinigame() 
	{
		//TODO: start next minigame
	}

	public void minigameLost() 
	{
		//TODO: do something with lives or a cute dying sound
		
		nextMinigame();
	}
}
