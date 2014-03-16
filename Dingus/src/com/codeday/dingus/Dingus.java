package com.codeday.dingus;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.codeday.asteroids.AsteroidsWorld;
import com.codeday.asteroids.asteroidsMinigame;
import com.codeday.escape.EscapeMinigame;
import com.codeday.escape.EscapeWorld;

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
	
	private int lives = 3;
	
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

		// create the level manager
		levelManager = new LevelManager();
		
		soundManager = new SoundManager();
		soundManager.setEnabled(true);

		// create the helper objects
		fpsLogger = new FPSLogger();
	}

	
	//SELECTS MINI GAME
	public void nextMinigame() 
	{
		int rand = (int) (Math.random() * 100);
		
		if (rand > 66)
		{
			setScreen(new SelfDestructMinigame(this, new SelfDestructWorld(this), 5000));
		}
		else if(rand < 33)
		{
			setScreen(new asteroidsMinigame(this, new AsteroidsWorld(this), 5000));
		}
		else
		{
			setScreen(new EscapeMinigame(this, new EscapeWorld(this), 5000));
		}
	}

	public void minigameLost() 
	{
		setScreen(new LoadingScreen(this, new LoadingWorld(this, lives, lives - 1), 3000));
		lives--;
		if (lives==0)
		{
			setScreen(new MenuScreen(this));
		}
	}
	public void minigameWon()
	{
		System.out.println(lives);
		setScreen(new LoadingScreen(this, new LoadingWorld(this, lives, lives), 3000));
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
}
