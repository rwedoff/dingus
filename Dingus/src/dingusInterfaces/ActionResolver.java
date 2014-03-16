package dingusInterfaces;

public interface ActionResolver {
	  public boolean getSignedInGPGS();
	  public void loginGPGS(int action);
	  public void loginGPGS();
	  public void submitScoreGPGS(int score);
	  public void unlockAchievementGPGS(String achievementId);
	  public void getLeaderboardGPGS();
	  public void getAchievementsGPGS();
}
