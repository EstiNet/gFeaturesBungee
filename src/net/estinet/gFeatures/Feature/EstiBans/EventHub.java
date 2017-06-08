package net.estinet.gFeatures.Feature.EstiBans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;

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
		File bans = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId() + "-bans");
		File mutes = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId() + "-mutes");
		File warnings = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId() + "-warnings");
		if(!bans.exists()){
			try {
				bans.createNewFile();
				EstiBans.bans.put(event.getPlayer().getUniqueId(), new ArrayList<>());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!mutes.exists()){
			try{
				mutes.createNewFile();
				EstiBans.mutes.put(event.getPlayer().getUniqueId(), new ArrayList<>());
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		if(!warnings.exists()){
			try{
				warnings.createNewFile();
				EstiBans.warnings.put(event.getPlayer().getUniqueId(), new ArrayList<>());
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		if(EstiBans.isBannedOn(event.getPlayer().getUniqueId(), event.getTarget().getName())){
			event.getPlayer().disconnect(new TextComponent(EstiBans.getBanReason(event.getPlayer().getUniqueId(), event.getTarget().getName())));
			event.setCancelled(true);
		}
	}
	public void onChat(ChatEvent event){
		ProxiedPlayer player = (ProxiedPlayer)event.getSender();
		if(EstiBans.isMutedOn(player.getUniqueId(), player.getServer().getInfo().getName())){
			event.setCancelled(true);
			player.sendMessage("You are currently muted on this server!");
		}
	}
	public void onServerSwitch(ServerSwitchEvent event){
		if(EstiBans.isBannedOn(event.getPlayer().getUniqueId(), event.getPlayer().getServer().getInfo().getName())){
			event.getPlayer().disconnect(new TextComponent(EstiBans.getBanReason(event.getPlayer().getUniqueId(), event.getPlayer().getServer().getInfo().getName())));
		}
	}
}
