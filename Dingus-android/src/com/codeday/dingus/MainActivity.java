package com.codeday.dingus;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import dingusInterfaces.ActionResolver;

public class MainActivity extends AndroidApplication 
			implements GameHelperListener, ActionResolver
{
	private GameHelper gameHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		gameHelper = new GameHelper(this);
        gameHelper.enableDebugLog(true, "GPGS");
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass = false;
		config.useGL20 = true;
		initialize(new Dingus(this), config);
	}
	
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		gameHelper.getGamesClient().submitScore(getString(R.string.leaderboard_id), score);		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		gameHelper.getGamesClient().unlockAchievement(achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
		startActivityForResult(gameHelper.getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 100);		
	}

	@Override
	public void getAchievementsGPGS() {
		startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Sign in failed", Toast.LENGTH_LONG);
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Sign in succeeded!", Toast.LENGTH_LONG);

	}
}