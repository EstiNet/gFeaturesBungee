package net.estinet.gFeatures.Feature.EstiChat;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Event;

public class EstiChat extends gFeature implements Events{
	
	EventHub eh = new EventHub();
	
	public EstiChat(String featurename, String d) {
		super(featurename, d);
	}
	@Override
	public void enable(){
		Enable.onEnable();
	}
	@Override
	public void disable(){
		Disable.onDisable();
	}
	@Override
	public void eventTrigger(Event event) {
		ProxyServer.getInstance().getLogger().info(event.getClass().getName());
		if(event.getClass().getName().equalsIgnoreCase("postloginevent")){
			eh.onPlayerJoin((PostLoginEvent)event);
		}
		else if(event.getClass().getName().equalsIgnoreCase("playerdisconnectevent")){
			eh.onPlayerDisconnect((PlayerDisconnectEvent)event);
		}
		else if(event.getClass().getName().equalsIgnoreCase("serverswitchevent")){
			eh.onServerSwitch((ServerSwitchEvent)event);
		}
	}
	@Override
	@Retrieval
	public void onPostLogin(){}
	@Override
	@Retrieval
	public void onPlayerDisconnect(){}
	@Override
	@Retrieval
	public void onServerSwitch(){}
}
