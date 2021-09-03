package net.estinet.gFeatures.Feature.SlashServer.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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
        super(new String[]{"hub"}, "basic", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            for (String str : Arrays.asList("Factions", "SurvivalLime", "SurvivalCyan", "SurvivalPink", "Skyblock", "Creative", "Development", "gWars", "Hub")) {
                if (player.getCurrentServer().get().getServerInfo().getName().equals(str)) {
                    player.createConnectionRequest(gFeatures.getInstance().getProxyServer().getServer("Hub").get()).fireAndForget();
                    return;
                }
            }

            player.createConnectionRequest(gFeatures.getInstance().getProxyServer().getServer("MinigameHub").get()).fireAndForget();
        } else {
            sender.sendMessage(Component.text("This command can only be run by a player!", NamedTextColor.AQUA));
        }
    }
}