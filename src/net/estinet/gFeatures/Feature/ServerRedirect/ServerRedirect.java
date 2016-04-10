package net.estinet.gFeatures.Feature.ServerRedirect;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.plugin.Event;

public class ServerRedirect extends gFeature implements Events{
	public ServerRedirect(String featurename, String d) {
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
