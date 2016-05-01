package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class SendAll {
	public void sendAllInfo(){
		CliotePing cp = new CliotePing();
		cp.sendMessage("mgstart", "MinigameHubs");
		for(String mgs : MinigameAssister.servers.keySet()){
			ServerInfo target = ProxyServer.getInstance().getServerInfo(mgs);
			cp.sendMessage("mgrecieve " + mgs + " " + MinigameAssister.servers.get(mgs) + " " + target.getPlayers().size() + " " + MinigameAssister.maps.get(mgs), "MinigameHubs");
		}
		cp.sendMessage("mgdone", "MinigameHubs");
	}
}
