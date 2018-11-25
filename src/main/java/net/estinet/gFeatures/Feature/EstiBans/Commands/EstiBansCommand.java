package net.estinet.gFeatures.Feature.EstiBans.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Feature.EstiBans.ConfigHub;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.estinet.gFeatures.gFeature;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import java.util.concurrent.ConcurrentHashMap;

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

public class EstiBansCommand extends EstiCommand {

    public EstiBansCommand(gFeature feature) {
        super(new String[]{"estibans"}, "gFeatures.admin", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(TextComponent.of("----- EstiBans -----", TextColor.AQUA));
                sender.sendMessage(TextComponent.of("/estibans info [Player] - Obtains player information.", TextColor.AQUA));
                sender.sendMessage(TextComponent.of("/estibans reload - Reloads from the config.", TextColor.AQUA));

            } else if (args[0].equalsIgnoreCase("reload")) {
                EstiBans.bans = new ConcurrentHashMap<>();
                EstiBans.mutes = new ConcurrentHashMap<>();
                EstiBans.warnings = new ConcurrentHashMap<>();

                ConfigHub.setupConfig();
                sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Completed reload.")));
            } else {
                sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("/estibans help")));
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Player info for " + args[1])));
                sender.sendMessage((TextComponent.of("Bans:", TextColor.DARK_GRAY)));

                for (String str : EstiBans.getBans(args[1])) {
                    if (!str.trim().equals("")) sender.sendMessage(TextComponent.of("- " + str, TextColor.DARK_GRAY));
                }

                sender.sendMessage(TextComponent.of("Mutes:", TextColor.DARK_GRAY));
                for (String str : EstiBans.getMutes(args[1])) {
                    if (!str.trim().equals("")) sender.sendMessage(TextComponent.of("- " + str, TextColor.DARK_GRAY));
                }

                sender.sendMessage(TextComponent.of("Warnings:", TextColor.DARK_GRAY));
                for (String str : EstiBans.getWarnings(args[1])) {
                    if (!str.trim().equals("")) sender.sendMessage(TextComponent.of("- " + str, TextColor.DARK_GRAY));
                }
            } else {
                sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("/estibans help")));
            }
        } else {
            sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("/estibans help")));
        }
    }
}
