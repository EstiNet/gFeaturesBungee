package net.estinet.gFeatures.Feature.EstiChat;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

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
            String previousServer = event.getPlayer().getCurrentServer().get().getServerInfo().getName(),
            targetServer = EstiChat.getServerName(event.getPlayer().getCurrentServer().get().getServerInfo().getName());
            for (Player player : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
                player.sendMessage(TextComponent.of("[", TextColor.GOLD).append(
                        TextComponent.of("Switch", TextColor.DARK_AQUA)
                ).append(TextComponent.of("] ", TextColor.GOLD)
                ).append(TextComponent.of("(" + previousServer + " -> " + targetServer + ") " + event.getPlayer().getUsername())));
            }
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(previousServer + " [Switch] (" + previousServer + " -> " + targetServer + ") " + event.getPlayer().getUsername()), "consolechat", "all");

        } else { // join proxy
            for (Player player : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
                if (player.getCurrentServer().isPresent() && !player.getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(event.getOriginalServer().getServerInfo().getName())) {
                    player.sendMessage(TextComponent.of("[" + EstiChat.getServerName(event.getResult().getServer().get().getServerInfo().getName()) + "] ").append(
                            TextComponent.of("[", TextColor.GOLD)
                    ).append(
                            TextComponent.of("Join", TextColor.DARK_AQUA)
                    ).append(
                            TextComponent.of("] ", TextColor.GOLD)
                    ).append(
                            TextComponent.of(event.getPlayer().getUsername())
                    ));
                }
            }
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(EstiChat.getServerName(event.getResult().getServer().get().getServerInfo().getName()) + " [Join] " + event.getPlayer().getUsername()), "consolechat", "all");
        }
    }

    @Subscribe
    public void onPlayerDisconnect(DisconnectEvent event) {
        if (!event.getPlayer().getCurrentServer().isPresent()) return;
        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(EstiChat.getServerName(event.getPlayer().getCurrentServer().get().getServerInfo().getName()) + " [Leave] " + event.getPlayer().getUsername()), "consolechat", "all");

        for (Player player : gFeatures.getInstance().getProxyServer().getAllPlayers()) {

            if (player.getCurrentServer().isPresent() && !player.getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(event.getPlayer().getCurrentServer().get().getServerInfo().getName())) {
                player.sendMessage(TextComponent.of(
                        "[" + EstiChat.getServerName(event.getPlayer().getCurrentServer().get().getServerInfo().getName()) + "] "
                ).append(TextComponent.of(
                        "[", TextColor.GOLD
                )).append(TextComponent.of(
                        "Leave", TextColor.DARK_AQUA
                )).append(TextComponent.of(
                        "] ", TextColor.GOLD
                )).append(TextComponent.of(
                        event.getPlayer().getUsername()
                )));
            }

        }
    }

    @Subscribe
    public void onServerChat(PlayerChatEvent event) {
        if (event.getMessage().charAt(0) != '/') {
            Player p = event.getPlayer();
            if (!p.getCurrentServer().isPresent()) return;
            String server = p.getCurrentServer().get().getServerInfo().getName();
            if (server.equals("SurvivalPink") || server.equals("SkyAdventures")) {
                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(p.getUsername() + " " + p.getUsername() + " : " + event.getMessage()), "chat", "Bungee");
            }
        }
    }
}
