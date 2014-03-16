package com.codeday.dingus;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import dingusInterfaces.ActionResolver;

public class DingusDesktop
{
	public static void main(String[] args)
	{
		Utilities.packTextures();
		new LwjglApplication(new Dingus(new ActionResolver() {
			
			@Override
			public void unlockAchievementGPGS(String achievementId) {
				// TODO Auto-generated method stub
				System.out.println("Unlocked Achievement!");
			}
			
			@Override
			public void submitScoreGPGS(int score) {
				// TODO Auto-generated method stub
				System.out.println("What a score! " + score);
			}
			
			@Override
			public void loginGPGS() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean getSignedInGPGS() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void getLeaderboardGPGS() {
				// TODO Auto-generated method stub
				System.out.println("Not supported");
			}
			
			@Override
			public void getAchievementsGPGS() {
				// TODO Auto-generated method stub
				System.out.println("Not supported");
			}
		}), "Dingus", 1280, 720, true);
	}
}
