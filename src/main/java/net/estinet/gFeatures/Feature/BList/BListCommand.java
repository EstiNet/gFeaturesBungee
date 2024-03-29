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

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.HashMap;

public class BListCommand extends EstiCommand {

    public BListCommand(gFeature feature) {
        super(new String[]{"blist", "list"}, "basic", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        if (gFeatures.getInstance().getProxyServer().getAllPlayers().size() == 1) {
            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&7&m--------&r&3There is 1 player on!&7&m--------"));
        } else {
            int size = gFeatures.getInstance().getProxyServer().getAllPlayers().size();
            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&7&m--------&r&3There are " + size + " players on!&7&m--------"));
        }

        if (gFeatures.getInstance().getProxyServer().getAllPlayers().size() == 0) {
            sender.sendMessage(Component.text("No players on right now.", NamedTextColor.DARK_AQUA));
        }
        HashMap<String, ArrayList<String>> servers = BList.getPlayersWithServer();

        // print out
        for (String server : servers.keySet()) {
            sender.sendMessage(Component.text(server + ":", NamedTextColor.DARK_AQUA));
            for (String player : servers.get(server)) {
                sender.sendMessage(Component.text(player, NamedTextColor.AQUA));
            }
        }

    }

}