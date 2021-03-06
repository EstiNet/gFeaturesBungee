package net.estinet.gFeatures.Feature.ServerQuery;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;

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

public class EventHub {

	@Subscribe
	public void onPlayerJoin(PostLoginEvent event) {
        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("online " + gFeatures.getInstance().getProxyServer().getAllPlayers().size()), "info", "all");
	}

	@Subscribe
	public void onPlayerLeave(DisconnectEvent event){
        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("online " + (gFeatures.getInstance().getProxyServer().getAllPlayers().size())), "info", "all");
	}
}
