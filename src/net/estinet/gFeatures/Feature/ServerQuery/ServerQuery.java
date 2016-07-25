package net.estinet.gFeatures.Feature.ServerQuery;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Event;

public class ServerQuery extends gFeature implements Events{
	
	EventHub eh = new EventHub();
	
	public ServerQuery(String featurename, String d) {
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
			eh.onPlayerLeave((PlayerDisconnectEvent)event);
		}
	}
	@Override
	@Retrieval
	public void onPostLogin(){}
	
	@Override
	@Retrieval
	public void onPlayerDisconnect(){}
}
