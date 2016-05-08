package net.estinet.gFeatures.Feature.EstiMail;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Event;

public class EstiMail extends gFeature implements Events{
	
	EventHub eh = new EventHub();
	
	public EstiMail(String featurename, String d) {
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
		if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("playerhandshakeevent")){
			eh.onPlayerJoin((PlayerHandshakeEvent)event);
		}
	}
	@Override
	@Retrieval
	public void onPlayerHandshake(){}
	
	public static void sendMail(String uuid, String mail){
		
	}
}
