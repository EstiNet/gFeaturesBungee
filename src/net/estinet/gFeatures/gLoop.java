package net.estinet.gFeatures;

import java.util.concurrent.TimeUnit;

import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.Network.Protocol.Output.OutputAlive;
import net.md_5.bungee.api.ProxyServer;

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

public class gLoop {
	public void start(){
		/*
		 * Initialize gFeatures Core loops here.
		 */
		ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
			public void run(){
				OutputAlive oa = new OutputAlive();
				oa.run(null);
				Debug.print("[ClioteSky] Pinging server...");
			}
		}, 5, 5, TimeUnit.SECONDS);
	}
}
