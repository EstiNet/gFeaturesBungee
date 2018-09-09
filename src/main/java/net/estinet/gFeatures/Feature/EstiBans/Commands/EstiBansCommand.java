package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Feature.EstiBans.ConfigHub;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;
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
        super("estibans", "gFeatures.admin", new String[0], feature);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.AQUA + "----- EstiBans -----");
                sender.sendMessage(ChatColor.AQUA + "/estibans info [Player] - Obtains player information.");
                sender.sendMessage(ChatColor.AQUA + "/estibans reload - Reloads from the config.");

            } else if (args[0].equalsIgnoreCase("reload")) {
                EstiBans.bans = new ConcurrentHashMap<>();
                EstiBans.mutes = new ConcurrentHashMap<>();
                EstiBans.warnings = new ConcurrentHashMap<>();

                ConfigHub.setupConfig();
                sender.sendMessage(EstiBans.estiBansPrefix + "Completed reload.");
            } else {
                sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Player info for " + args[1]));
                sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Bans:"));

                for (String str : EstiBans.getBans(args[1])) {
                    if (!str.trim().equals("")) sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
                }

                sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Mutes:"));
                for (String str : EstiBans.getMutes(args[1])) {
                    if (!str.trim().equals("")) sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
                }

                sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Warnings:"));
                for (String str : EstiBans.getWarnings(args[1])) {
                    if (!str.trim().equals("")) sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
                }
            } else {
                sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
            }
        } else {
            sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
        }
    }
}
