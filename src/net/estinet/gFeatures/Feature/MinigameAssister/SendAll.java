package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.ClioteSky.API.CliotePing;

public class SendAll {
	public void sendAllInfo(){
		CliotePing cp = new CliotePing();
		cp.sendMessage("mgstart", "MinigameHubs");
		for(String mgs : MinigameAssister.servers.keySet()){
			cp.sendMessage("mgrecieve " + mgs + " " + MinigameAssister.servers.get(mgs), "MinigameHubs");
		}
		cp.sendMessage("mgdone", "MinigameHubs");
	}
}
