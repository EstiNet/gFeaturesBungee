package net.estinet.gFeatures.Feature.EstiChat;
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

import java.util.concurrent.TimeUnit;

import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;

public class EventHub{
	@SuppressWarnings("deprecation")
	public void onPlayerJoin(PostLoginEvent event){
		for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
			if(!player.getServer().getInfo().getName().equalsIgnoreCase(event.getPlayer().getServer().getInfo().getName())){
				player.sendMessage("[" + event.getPlayer().getServer().getInfo().getName() + "] " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Join" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName());
			}
		}
		CliotePing cp = new CliotePing();
		cp.sendMessage("consolechat " + event.getPlayer().getServer().getInfo().getName() + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Join" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName(), "all");
	}
	@SuppressWarnings("deprecation")
	public void onPlayerDisconnect(PlayerDisconnectEvent event){
		for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
			if(!player.getServer().getInfo().getName().equalsIgnoreCase(event.getPlayer().getServer().getInfo().getName())){
				player.sendMessage("[" + event.getPlayer().getServer().getInfo().getName() + "] " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Leave" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName());
			}
		}
		CliotePing cp = new CliotePing();
		cp.sendMessage("consolechat " + event.getPlayer().getServer().getInfo().getName() + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Leave" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName(), "all");
	}
	@SuppressWarnings("deprecation")
	public void onServerSwitch(ServerSwitchEvent event){
		String previous = new String(event.getPlayer().getServer().getInfo().getName());
		ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
            public void run() {
            	if(!previous.equals(event.getPlayer().getServer().getInfo().getName())){
            	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
        			player.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Switch" + ChatColor.GOLD + "] " + ChatColor.RESET + "(" + previous + " -> " + event.getPlayer().getServer().getInfo().getName() + ") " + event.getPlayer().getName());
        		}
        		CliotePing cp = new CliotePing();
        		cp.sendMessage("consolechat " + event.getPlayer().getServer().getInfo().getName() + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Switch" + ChatColor.GOLD + "] " + ChatColor.RESET + "(" + previous + " -> " + event.getPlayer().getServer().getInfo().getName() + ") " + event.getPlayer().getName(), "all");
            	}
            }
         }, 1, TimeUnit.SECONDS);
	}
}
