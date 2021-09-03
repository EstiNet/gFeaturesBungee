package net.estinet.gFeatures.Feature.EstiBans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

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
        File bans = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId() + "-bans");
        File mutes = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId() + "-mutes");
        File warnings = new File("plugins/gFeatures/EstiBans/playerdata/" + event.getPlayer().getUniqueId() + "-warnings");
        if (!bans.exists()) {
            try {
                bans.createNewFile();
                EstiBans.bans.put(event.getPlayer().getUniqueId(), new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!mutes.exists()) {
            try {
                mutes.createNewFile();
                EstiBans.mutes.put(event.getPlayer().getUniqueId(), new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!warnings.exists()) {
            try {
                warnings.createNewFile();
                EstiBans.warnings.put(event.getPlayer().getUniqueId(), new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!event.getResult().getServer().isPresent()) return;
        if (EstiBans.isBannedOn(event.getPlayer().getUniqueId(), event.getResult().getServer().get().getServerInfo().getName())) {
            event.getPlayer().disconnect(Component.text(EstiBans.getBanReason(event.getPlayer().getUniqueId(), event.getResult().getServer().get().getServerInfo().getName())));
            event.setResult(ServerPreConnectEvent.ServerResult.denied());
        }
    }

    @Subscribe
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.getCurrentServer().isPresent()) return;
        if (event.getMessage().length() > 0 && event.getMessage().charAt(0) != '/' && EstiBans.isMutedOn(player.getUniqueId(), player.getCurrentServer().get().getServerInfo().getName())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());
            player.sendMessage(Component.text("You are currently muted on this server! Reason: " + EstiBans.getMuteReason(player.getUniqueId(), player.getCurrentServer().get().getServerInfo().getName())).decoration(TextDecoration.BOLD, true));
        }
    }

}
