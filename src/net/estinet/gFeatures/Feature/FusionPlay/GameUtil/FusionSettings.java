package net.estinet.gFeatures.Feature.FusionPlay.GameUtil;

public class FusionSettings {

	/*
	 * FusionSettings Holder Variables
	 */

	public int secLeft = 0;
	public int waitingSecLeft = 0;
	protected int defaultWaitingSecLeft = 0;

	/*
	 * FusionGame provided settings.
	 */

	protected boolean usesSpawns = true; //Whether or not to use the built-in spawn mechanics.
	protected boolean antiDeath = true; //Prevents death screen if enabled.
	protected boolean autoLoadMap = true; //Whether or not to use built in map-loader (loads before server is fully setup)
	protected boolean dependsOnTimer = true; //Whether or not the game depends on the timer, or winning circumstance.
	protected boolean allToSpectator = true; //Whether or not to set all players to Spectator after the game ends.
	protected boolean alwaysSetTime = true; //Whether or not to always keep the current time.
	protected boolean usesPlayerCollision = true; //Whether or not to have player collision
	protected int startTimeOfDay = 6000; //Starting time of the game (day, night). Default is noon.
	protected String defaultMapName = "world";
	protected String coolGameName = "[CoolGame]";
	protected TimeManager timeManager = null;

	/*
	 * FusionGame Settings
	 */

	public boolean usesSpawns(){
		return usesSpawns;
	}
	public void setUsesSpawns(boolean usesSpawns){
		this.usesSpawns = usesSpawns;
	}
	public boolean usesAntiDeathScr(){
		return antiDeath;
	}
	public void setUsesAntiDeathScr(boolean antiDeath){
		this.antiDeath = antiDeath;
	}
	public boolean usesAutoLoadMap(){
		return autoLoadMap;
	}
	public void setAutoLoadMap(boolean autoLoadMap){
		this.autoLoadMap = autoLoadMap;
	}
	public String getDefaultMapName(){
		return defaultMapName;
	}
	public void setDefaultMapName(String defaultMapName){
		this.defaultMapName = defaultMapName;
	}
	public String getCoolGameName(){
		return coolGameName;
	}
	public void setCoolGameName(String alias){
		this.coolGameName = alias;
	}
	public int getWaitingSecLeft(){
		return waitingSecLeft;
	}
	public void setWaitingSecLeft(int sec){
		this.waitingSecLeft = sec;
	}
	public TimeManager getTimeManager(){
		return timeManager;
	}
	public void setTimeManager(TimeManager timeManager){
		this.timeManager = timeManager;
	}
	public boolean usesDependsOnTimer(){
		return dependsOnTimer;
	}
	public void setDependsOnTimer(boolean value){
		dependsOnTimer = value;
	}
	public boolean getAllToSpectator() {
		return allToSpectator; 
	}
	public void setAllToSpectator(boolean value){
		allToSpectator = value; 
	}
	public void setDefaultWaitingSecLeft(int defaultWaitingSec){
		this.defaultWaitingSecLeft = defaultWaitingSec;
	}
	public int getDefaultWaitingSecLeft(){
		return defaultWaitingSecLeft;
	}
	public void setStartTimeOfDay(int startTime){
		startTimeOfDay = startTime;
	}
	public int getStartTimeOfDay(){
		return startTimeOfDay;
	}
	public void setAlwaysSetTime(boolean alwaysSetTime){
		this.alwaysSetTime = alwaysSetTime;
	}
	public boolean usesAlwaysSetTime(){
		return alwaysSetTime;
	}
	public void setPlayerCollision(boolean usesPlayerCollision){
		this.usesPlayerCollision = usesPlayerCollision;
	}
	public boolean usesPlayerCollision(){
		return usesPlayerCollision;
	}
}
