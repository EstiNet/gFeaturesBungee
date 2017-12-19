package net.estinet.gFeatures.Feature.gRanksNew;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2017 EstiNet

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

public class gRanksNew extends gFeature implements Events{
	
	public gRanksNew(String featurename, String version) {
		super(featurename, version);
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
		if(event.getEventName().equalsIgnoreCase("playerjoinevent")){
			EventHub.onPlayerJoin((PlayerJoinEvent)event);
		}
	}
	@Retrieval
	public void onPlayerJoin(){}
}