package net.estinet.gFeatures.Feature.EstiChat;

import java.util.HashMap;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Event;

public class EstiChat extends gFeature implements Events{
	
	public static HashMap<String, String> switcher = new HashMap<>();
	
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
		if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("postloginevent")){
			eh.onPlayerJoin((PostLoginEvent)event);
		}
		else if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("playerdisconnectevent")){
			eh.onPlayerDisconnect((PlayerDisconnectEvent)event);
		}
		else if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("serverswitchevent")){
			eh.onServerSwitch((ServerSwitchEvent)event);
		}
		else if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("serverconnectevent")){
			eh.onServerConnect((ServerConnectEvent)event);
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
	public void onServerConnect(){}
	@Override
	@Retrieval
	public void onServerSwitch(){}
}
