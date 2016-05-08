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
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;

public class EventHub{
	@SuppressWarnings("deprecation")
	public void onPlayerJoin(PostLoginEvent event){
		ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
            public void run() {
            	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
        			if(!player.getServer().getInfo().getName().equalsIgnoreCase(event.getPlayer().getServer().getInfo().getName())){
        				player.sendMessage("[" + event.getPlayer().getServer().getInfo().getName() + "] " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Join" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName());
        			}
        		}
        		CliotePing cp = new CliotePing();
        		cp.sendMessage("consolechat " + event.getPlayer().getServer().getInfo().getName() + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Join" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName(), "all");   
            }
         }, 1, TimeUnit.SECONDS);
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
	public void onServerConnect(ServerConnectEvent event){
		EstiChat.switcher.remove(event.getPlayer().getName());
		try{
			EstiChat.switcher.put(event.getPlayer().getName(), event.getPlayer().getServer().getInfo().getName());
		}
		catch(NullPointerException e){
			EstiChat.switcher.put(event.getPlayer().getName(), null);
		}
	}
	@SuppressWarnings({ "deprecation", "unused" })
	public void onServerSwitch(ServerSwitchEvent event){
		String previous;
		previous = event.getPlayer().getServer().getInfo().getName();
		try{
			String test = EstiChat.switcher.get(event.getPlayer().getName());
		}
		catch(NullPointerException e){
			return;
		}
		if(!previous.equals(EstiChat.switcher.get(event.getPlayer().getName())) && !(EstiChat.switcher.get(event.getPlayer().getName()) == null)){
			for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
				player.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Switch" + ChatColor.GOLD + "] " + ChatColor.RESET + "(" + EstiChat.switcher.get(event.getPlayer().getName()) + " -> " + previous + ") " + event.getPlayer().getName());
			}
			CliotePing cp = new CliotePing();
			cp.sendMessage("consolechat " + EstiChat.switcher.get(event.getPlayer().getName()) + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Switch" + ChatColor.GOLD + "] " + ChatColor.RESET + "(" + EstiChat.switcher.get(event.getPlayer().getName()) + " -> " + previous + ") " + event.getPlayer().getName(), "all");
		}
	}
}
