package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.plugin.Event;

public class MinigameAssister extends gFeature implements Events{
	
	EventHub eh = new EventHub();
	
	public MinigameAssister(String featurename, String d) {
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
	public void eventTrigger(Event event) {}
}
