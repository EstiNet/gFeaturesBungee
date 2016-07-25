package net.estinet.gFeatures.Feature.ServerQuery;

import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;

public class EventHub {

	public void onPlayerJoin(PostLoginEvent event) {
		CliotePing cp = new CliotePing();
		cp.sendMessage("info online " + ProxyServer.getInstance().getOnlineCount(), "all");
	}
	
	public void onPlayerLeave(PlayerDisconnectEvent event){
		CliotePing cp = new CliotePing();
		cp.sendMessage("info online " + ProxyServer.getInstance().getOnlineCount(), "all");
	}
}
