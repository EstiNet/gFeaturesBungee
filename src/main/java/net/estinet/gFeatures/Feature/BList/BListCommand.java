package net.estinet.gFeatures.Feature.BList;

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

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;

public class BListCommand extends EstiCommand {

    public BListCommand(gFeature feature) {
        super("blist", "basic", new String[0], feature);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.RESET + ChatColor.DARK_AQUA + "There are " + ProxyServer.getInstance().getPlayers().size() + " " + (ProxyServer.getInstance().getPlayers().size() == 1 ? "player" : "players") + " on!" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------"));
        if (ProxyServer.getInstance().getPlayers().size() == 0) {
            sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "No players on right now."));
        }
        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            if (server.getPlayers().size() != 0) {
                sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + server.getName() + ":"));
                for (ProxiedPlayer player : server.getPlayers()) {
                    sender.sendMessage(new TextComponent(ChatColor.AQUA + player.getName()));
                }
            }
        }
    }

}