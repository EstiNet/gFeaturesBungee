package net.estinet.gFeatures.Feature.EstiMail;
import java.io.File;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;

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

public class EventHub{
	public void onPlayerJoin(PostLoginEvent event){
		ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures").getProxy().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
			public void run(){
				File f = new File("plugins/gFeatures/EstiMail/" + event.getPlayer().getUniqueId().toString());
				if(!f.isDirectory()){
					f.mkdir();
				}
				EstiMail.getMail(event.getPlayer());
			}});
	}
}
