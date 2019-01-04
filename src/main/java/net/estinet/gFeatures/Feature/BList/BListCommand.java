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
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BListCommand extends EstiCommand {

    public BListCommand(gFeature feature) {
        super(new String[]{"blist", "list"}, "basic", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        if (gFeatures.getInstance().getProxyServer().getAllPlayers().size() == 1) {
            sender.sendMessage(TextComponent.of("--------", TextColor.GRAY).decoration(TextDecoration.STRIKETHROUGH, true).
                    append(TextComponent.of("There is 1 player on!", TextColor.DARK_AQUA).decoration(TextDecoration.STRIKETHROUGH, false)).resetStyle().
                    append(TextComponent.of("--------", TextColor.GRAY).decoration(TextDecoration.STRIKETHROUGH, true)));
        } else {
            sender.sendMessage(TextComponent.of("--------", TextColor.GRAY).decoration(TextDecoration.STRIKETHROUGH, true).
                    append(TextComponent.of("There are " + gFeatures.getInstance().getProxyServer().getAllPlayers().size() + " players on!", TextColor.DARK_AQUA).decoration(TextDecoration.STRIKETHROUGH, false)).resetStyle().
                    append(TextComponent.of("--------", TextColor.GRAY).decoration(TextDecoration.STRIKETHROUGH, true)));
        }

        if (gFeatures.getInstance().getProxyServer().getAllPlayers().size() == 0) {
            sender.sendMessage(TextComponent.of("No players on right now.", TextColor.DARK_AQUA));
        }
        HashMap<String, ArrayList<String>> servers = BList.getPlayersWithServer();

        // print out
        for (String server : servers.keySet()) {
            sender.sendMessage(TextComponent.of(server + ":", TextColor.DARK_AQUA));
            for (String player : servers.get(server)) {
                sender.sendMessage(TextComponent.of(player, TextColor.AQUA));
            }
        }

    }

}