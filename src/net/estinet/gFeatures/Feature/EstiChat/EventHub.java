package net.estinet.gFeatures.Feature.EstiChat;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.estinet.gFeatures.ClioteSky.API.CliotePing;

/*
gFeatures
https://github.com/EstiNet/gFeatures

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

public class EventHub{
	public void onPlayerChat(AsyncPlayerChatEvent event){
		CliotePing cp = new CliotePing();
		cp.sendMessage("chat " + event.getPlayer().getName() + " <" + event.getPlayer().getDisplayName() + "> " + event.getMessage(), "Bungee");
		EstiChat.lastSent = event.getMessage();
	}
	public void onPlayerDeath(PlayerDeathEvent event){
		CliotePing cp = new CliotePing();
		cp.sendMessage("chat "+ event.getEntity().getName() + " " + event.getDeathMessage(), "Bungee");
		EstiChat.lastSent = event.getDeathMessage();
	}
}
