package net.estinet.gFeatures.Feature.EstiChat;

import java.util.concurrent.TimeUnit;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;

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
    public void onPlayerJoin(ServerPreConnectEvent event) {
        if (!event.getResult().getServer().isPresent()) return;

        if (event.getPlayer().getCurrentServer().isPresent()) { // server switching
            String previous; // TODO SERVER SWITCHING
            previous = EstiChat.getServerName(event.getPlayer().getServer().getInfo().getName());
            try {
                String test = EstiChat.switcher.get(event.getPlayer().getName());
            } catch (NullPointerException e) {
                return;
            }
            if (!previous.equals(EstiChat.switcher.get(event.getPlayer().getName())) && !(EstiChat.switcher.get(event.getPlayer().getName()) == null)) {
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    player.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Switch" + ChatColor.GOLD + "] " + ChatColor.RESET + "(" + EstiChat.switcher.get(event.getPlayer().getName()) + " -> " + previous + ") " + event.getPlayer().getName());
                }

                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(EstiChat.switcher.get(event.getPlayer().getName()) + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Switch" + ChatColor.GOLD + "] " + ChatColor.RESET + "(" + EstiChat.switcher.get(event.getPlayer().getName()) + " -> " + previous + ") " + event.getPlayer().getName()), "consolechat", "all");

            }
        } else { // join proxy
            for (Player player : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
                if (!player.getCurrentServer().isPresent() && !player.getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(event.getOriginalServer().getServerInfo().getName())) {
                    player.sendMessage("[" + EstiChat.getServerName(event.getPlayer().getServer().getInfo().getName()) + "] " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Join" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName());
                }
            }
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(EstiChat.getServerName(event.getPlayer().getServer().getInfo().getName()) + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Join" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName()), "consolechat", "all");
        }
    }

    @Subscribe
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        if (event.getPlayer().getServer() == null) return;
        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(EstiChat.getServerName(event.getPlayer().getServer().getInfo().getName()) + " " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Leave" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName()), "consolechat", "all");

        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {

            if (!player.getServer().getInfo().getName().equalsIgnoreCase(event.getPlayer().getServer().getInfo().getName())) {
                player.sendMessage("[" + EstiChat.getServerName(event.getPlayer().getServer().getInfo().getName()) + "] " + ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Leave" + ChatColor.GOLD + "] " + ChatColor.RESET + event.getPlayer().getName());
            }

        }
    }

    @Subscribe
    public void onServerConnect(ServerConnectEvent event) {
        EstiChat.switcher.remove(event.getPlayer().getName());
        try {
            EstiChat.switcher.put(event.getPlayer().getName(), EstiChat.getServerName(event.getPlayer().getServer().getInfo().getName()));
        } catch (NullPointerException e) {
            EstiChat.switcher.put(event.getPlayer().getName(), null);
        }
    }

    @Subscribe
    public void onServerChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer && event.getMessage().charAt(0) != '/') {
            ProxiedPlayer p = (ProxiedPlayer) event.getSender();
            if (p.getServer().getInfo().getName().equals("SurvivalPink") || p.getServer().getInfo().getName().equals("SkyAdventures")) {
                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(p.getName() + " " + p.getDisplayName() + ": " + event.getMessage()), "chat", "Bungee");
            }
        }
    }
}
