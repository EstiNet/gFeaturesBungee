package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.ClioteSkyOld.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;

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

public class Enable{
	public static void onEnable(){
		ProxyServer.getInstance().getLogger().info("[MinigameAssister] enabled!");
		CliotePing cp = new CliotePing();
		cp.sendMessage("getmginfo", "all");
	}
}
