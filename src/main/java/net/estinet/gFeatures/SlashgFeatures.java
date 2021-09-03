package net.estinet.gFeatures;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.InvocableCommand;
import com.velocitypowered.api.command.SimpleCommand;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

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

public class SlashgFeatures implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if (!sender.hasPermission("gFeatures.admin")) return;

        try {
            if (args.length == 0) {
                sender.sendMessage(Component.text("Please do /gfp help.", NamedTextColor.GRAY));
            } else if (args.length == 1) {
                switch (args[0]) {
                    case "version":
                        sender.sendMessage(Component.text("gFeatures Version " + gFeatures.version, NamedTextColor.GRAY));
                        break;
                    case "help":
                        sender.sendMessage(Component.text("------Help------", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("/gfp version : States the version.", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("/gfp list : Lists all features with their states and versions also.", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("/gfp featurestate <Feature> : Gets the state of the feature.", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("/gfp send <Cliote> <Identifier> <Message> : Sends a manual message to the ClioteSky server.", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("/gfp lookup <Player Name> : Lookup player info (UUID and previous names).", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("/gfp debug : Turns on debug messages.", NamedTextColor.GRAY));
                        break;
                    case "list":
                        List<gFeature> features = gFeatures.getFeatures();
                        sender.sendMessage(Component.text("Features:", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("Enabled:", NamedTextColor.GRAY));
                        for (gFeature feature : features) {
                            if (feature.isEnabled()) {
                                sender.sendMessage(Component.text(" - " + feature.getName() + " " + feature.getVersion(), NamedTextColor.GRAY));
                            }
                        }
                        sender.sendMessage(Component.text("Disabled:", NamedTextColor.GRAY));
                        for (gFeature feature : features) {
                            if (!feature.isEnabled()) {
                                sender.sendMessage(Component.text(" - " + feature.getName() + " " + feature.getVersion(), NamedTextColor.GRAY));
                            }
                        }
                        break;
                    case "featurestate":
                        sender.sendMessage(Component.text("Usage: /gfp featurestate <Plugin>", NamedTextColor.GRAY));
                        break;
                    case "debug":
                        if (gFeatures.debug) {
                            gFeatures.debug = false;
                            sender.sendMessage(Component.text("Turned off debugging.", NamedTextColor.GRAY));
                        } else {
                            gFeatures.debug = true;
                            sender.sendMessage(Component.text("Turned on debugging.", NamedTextColor.GRAY));
                        }
                        break;
                    default:
                        sender.sendMessage(Component.text("Please do /gFeaturesBungee help.", NamedTextColor.GRAY));
                        break;
                }
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "featurestate":
                        gFeature feature = gFeatures.getFeature(args[1]);
                        sender.sendMessage(Component.text("Feature " + args[1] + " state is " + feature.isEnabled(), NamedTextColor.GRAY));
                        break;
                    case "lookup":
                        sender.sendMessage(Component.text("----- Player info for " + args[1] + " -----", NamedTextColor.GRAY));
                        sender.sendMessage(Component.text("UUID: " + ResolverFetcher.getUUIDfromName(args[1]), NamedTextColor.GRAY));
                        StringBuilder prev = new StringBuilder("Previous names: ");
                        List<String> names = ResolverFetcher.getAllNames(ResolverFetcher.getUUIDfromName(args[1]));
                        for (int i = 1; i < names.size(); i++) {
                            prev.append(names.get(i)).append(" ");
                        }
                        sender.sendMessage(Component.text(prev.toString(), NamedTextColor.GRAY));
                        break;
                    default:
                        sender.sendMessage(Component.text("Please do /gfp help.", NamedTextColor.GRAY));
                        break;
                }
            } else if (args.length >= 4) {
                switch (args[0]) {
                    case "send":
                        StringBuilder output = new StringBuilder();
                        for (int i = 3; i < args.length; i++) {
                            output.append(args[i]).append(i == args.length-1 ? "" : " ");
                        }

                        ClioteSky.getInstance().send(ClioteSky.stringToBytes(output.toString()), args[2], args[1]);
                        sender.sendMessage(Component.text("Sent message " + output + " to ClioteSky.", NamedTextColor.GRAY));
                        break;
                    default:
                        sender.sendMessage(Component.text("Please do /gfp help.", NamedTextColor.GRAY));
                        break;
                }
            } else {
                sender.sendMessage(Component.text("Please do /gfp help.", NamedTextColor.GRAY));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
