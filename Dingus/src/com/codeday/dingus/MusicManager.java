package com.codeday.dingus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the background music.
 * <p>
 * Only one music may be playing at a given time.
 */
public class MusicManager implements Disposable
{

	private BackgroundMusic musicBeingPlayed;
	private float volume = 1f;
	private boolean enabled = true;
	public MusicManager()
	{
		
	}

	public void play(BackgroundMusic music)
	{
		if (!enabled)
			return;

		if (musicBeingPlayed == music)
			return;

		// do some logging
		Gdx.app.log(Dingus.LOG, "Playing music: " + music.getName());

		// stop any music being played
		stop();

		// start streaming the new music
		FileHandle musicFile = Gdx.files.internal(music.getFileName());
		Music musicResource = Gdx.audio.newMusic(musicFile);
		musicResource.setVolume(volume);
		musicResource.setLooping(true);
		musicResource.play();

		// set the music being played
		musicBeingPlayed = music;
		musicBeingPlayed.setMusicResource(musicResource);
	}

	/**
	 * Stops and disposes the current music being played, if any.
	 */
	public void stop()
	{
		if (musicBeingPlayed != null)
		{
			Gdx.app.log(Dingus.LOG, "Stopping current music");
			Music musicResource = musicBeingPlayed.getMusicResource();
			musicResource.stop();
			musicResource.dispose();
			musicBeingPlayed = null;
		}
	}

	/**
	 * Sets the music volume which must be inside the range [0,1].
	 */
	public void setVolume(float volume)
	{
		Gdx.app.log(Dingus.LOG, "Adjusting music volume to: " + volume);

		// check and set the new volume
		if (volume < 0 || volume > 1f)
		{
			throw new IllegalArgumentException(
					"The volume must be inside the range: [0,1]");
		}
		this.volume = volume;

		// if there is a music being played, change its volume
		if (musicBeingPlayed != null)
		{
			musicBeingPlayed.getMusicResource().setVolume(volume);
		}
	}

	/**
	 * Enables or disabled the music.
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;

		// if the music is being deactivated, stop any music being played
		if (!enabled)
		{
			stop();
		}
	}

	/**
	 * Disposes the music manager.
	 */
	public void dispose()
	{
		Gdx.app.log(Dingus.LOG, "Disposing music manager");
//		stop();
	}
}