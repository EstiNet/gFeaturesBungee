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

import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BListCommand extends EstiCommand {

    public BListCommand(gFeature feature) {
        super("blist", "basic", (String[]) Arrays.asList("list").toArray(), feature);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (ProxyServer.getInstance().getPlayers().size() == 1) {
            sender.sendMessage(new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.RESET + ChatColor.DARK_AQUA + "There is 1 player on!" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------"));
        } else {
            sender.sendMessage(new TextComponent(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------" + ChatColor.RESET + ChatColor.DARK_AQUA + "There are " + ProxyServer.getInstance().getPlayers().size() + " players on!" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------"));
        }

        if (ProxyServer.getInstance().getPlayers().size() == 0) {
            sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "No players on right now."));
        }
        HashMap<String, ArrayList<String>> servers = new HashMap<>();
        for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
            try {
                String sName = pp.getServer().getInfo().getName();
                if (servers.get(sName) == null) {
                    servers.put(sName, new ArrayList<>(Arrays.asList(pp.getName())));
                } else {
                    servers.get(sName).add(pp.getName());
                }
            } catch (NullPointerException e) { //line 58
            }

        }
        for (String server : servers.keySet()) {
            Debug.print(server);
            sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + server + ":"));
            for (String player : servers.get(server)) {
                sender.sendMessage(new TextComponent(ChatColor.AQUA + player));
            }
        }

    }
}