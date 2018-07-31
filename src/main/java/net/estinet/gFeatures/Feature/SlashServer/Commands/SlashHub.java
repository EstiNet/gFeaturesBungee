package net.estinet.gFeatures.Feature.SlashServer.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;

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

public class SlashHub extends EstiCommand {
    public SlashHub(gFeature feature) {
        super("hub", "basic", new String[0], feature);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            for (String str : Arrays.asList("Factions", "SurvivalLime", "SurvivalCyan", "SurvivalPink", "Skyblock", "Creative", "Development", "gWars", "Hub")) {
                if (player.getServer().getInfo().getName().equals(str)) {
                    player.connect(ProxyServer.getInstance().getServerInfo("Hub"));
                    return;
                }
            }

            player.connect(ProxyServer.getInstance().getServerInfo("MinigameHub"));
        } else {
            sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.AQUA).create());
        }
    }
}