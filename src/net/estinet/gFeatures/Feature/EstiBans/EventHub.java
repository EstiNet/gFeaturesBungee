package net.estinet.gFeatures.Feature.EstiBans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectEvent;

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
	public void onPlayerJoin(ServerConnectEvent event){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId());
		if(!f.exists()){
			try {
				f.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				PrintWriter pw = new PrintWriter(f);
				pw.write("bansiqs false");
				pw.write("mutesiqs false");
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(EstiBans.isBannedOn(event.getPlayer().getUniqueId(), event.getTarget().getName())){
			event.getPlayer().disconnect(new TextComponent(EstiBans.getBanReason(event.getPlayer().getUniqueId())));
			event.setCancelled(true);
		}
	}
}
