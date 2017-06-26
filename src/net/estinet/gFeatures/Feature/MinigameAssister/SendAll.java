package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

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

public class SendAll {
	public void sendAllInfo(){
		CliotePing cp = new CliotePing();
		cp.sendMessage("mgstart", "MinigameHubs");
		for(String mgs : MinigameAssister.servers.keySet()){
			ServerInfo target = ProxyServer.getInstance().getServerInfo(mgs);
			cp.sendMessage("mgrecieve " + mgs + " " + MinigameAssister.servers.get(mgs) + " " + target.getPlayers().size() + " " + MinigameAssister.maps.get(mgs), "MinigameHubs");
		}
		cp.sendMessage("mgdone", "MinigameHubs");
	}
}