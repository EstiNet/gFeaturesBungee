package net.estinet.gFeatures.Feature.EstiChat;

import java.util.HashMap;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Event;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2018 EstiNet

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
		else if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("chatevent")){
			eh.onServerChat((ChatEvent)event);
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
	@Override
	@Retrieval
	public void onChat(){}

	public static String getServerName(String name) {
		/*if (name.equals("Survival")) {
			return "SurvivalLime";
		} else if (name.equals("SurvivalO")) {
			return "SurvivalCyan";
		}*/
		return name;
	}
}
