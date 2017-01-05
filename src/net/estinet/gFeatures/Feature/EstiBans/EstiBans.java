package net.estinet.gFeatures.Feature.EstiBans;

import java.util.UUID;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Event;

public class EstiBans extends gFeature implements Events{
	
	EventHub eh = new EventHub();
	
	public EstiBans(String featurename, String d) {
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
	
	public static void banPlayer(String name, String server, double millis){
		banPlayer(ResolverFetcher.getUUIDfromName(name), server, millis);
	}
	public static void banPlayer(UUID uuid, String server, double millis){
		
	}
	public static void unbanPlayer(String name, String server){
		unbanPlayer(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static void unbanPlayer(UUID uuid, String server){
		
	}
	public static void mutePlayer(String name, String server, double millis){
		mutePlayer(ResolverFetcher.getUUIDfromName(name), server, millis);
	}
	public static void mutePlayer(UUID uuid, String server, double millis){
		
	}
	public static void unmutePlayer(String name, String server){
		unmutePlayer(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static void unmutePlayer(UUID uuid, String server){
		
	}
	public static void warnPlayer(String name, String server, double millis){
		warnPlayer(ResolverFetcher.getUUIDfromName(name), server, millis);
	}
	public static void warnPlayer(UUID uuid, String server, double millis){
		
	}
	public static void unwarnPlayer(String name, String server){
		unwarnPlayer(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static void unwarnPlayer(UUID uuid, String server){
		
	}
	public static void kickPlayer(String name, String server, double millis){
		
	}
}
