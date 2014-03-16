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
	
	public static final int NO_ACTION = 0;
	public static final int ACHIEVEMENTS_ACTION = 1;
	public static final int LEADERBOARDS_ACTION = 2;
	private int pendingAction = NO_ACTION;
	
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
		gameHelper.setup(this);
	}
	
	@Override
	public boolean isLoggingIn() {
		return (pendingAction == NO_ACTION ? false : true);
	}
	
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		loginGPGS(NO_ACTION);
	}

	@Override
	public void loginGPGS(int action) {
		// TODO Auto-generated method stub
		pendingAction = action;
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
		System.out.println("Login FAILED! :-(");
		Toast.makeText(this, "Sign in failed", Toast.LENGTH_LONG).show();
		pendingAction = NO_ACTION;
	}

	@Override
	public void onSignInSucceeded() {
		System.out.println("Login Succeeded!");
		pendingAction = NO_ACTION;
		// TODO Auto-generated method stub
		Toast.makeText(this, "Sign in succeeded!", Toast.LENGTH_LONG).show();
		switch (pendingAction) {
		case ACHIEVEMENTS_ACTION:
			getAchievementsGPGS();
			break;
		case LEADERBOARDS_ACTION:
			getLeaderboardGPGS();
			break;
		default:
			break;
		}

	}
}