package net.estinet.gFeatures;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.plugin.Event;

/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2016 EstiNet

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

public class Library implements Listener{
	@EventHandler
	public void onPlayerJoin(PlayerHandshakeEvent event){
		check("onPlayerHandshake", event);
	}
	@EventHandler
	public void onPlayerLeave(PlayerDisconnectEvent event){
		check("onPlayerDisconnect", event);
	}
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent event){
		check("onServerSwitch", event);
	}
    public void check(String methodname, Event event){
    	List<gFeature> features = Basic.getFeatures();
		for(gFeature feature : features){
			try {
				if(!(feature.getClass().getDeclaredMethod(methodname).equals(null))){
					if(feature.getState().equals(FeatureState.ENABLE)){
					feature.eventTrigger(event);
					}
				}
			} catch (NoSuchMethodException e) {} 
			catch (SecurityException e) {}
			catch (ConcurrentModificationException e) {}
		}
    }
}
