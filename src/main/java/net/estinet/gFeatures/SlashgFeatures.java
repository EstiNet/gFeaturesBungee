package net.estinet.gFeatures;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

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

public class SlashgFeatures implements Command {

    @Override
    public void execute(CommandSource sender, String[] args) {
        if (!sender.hasPermission("gFeatures.admin")) return;

        try {
            if (args.length == 0) {
                sender.sendMessage(TextComponent.of("Please do /gfp help.", TextColor.GRAY));
            } else if (args.length == 1) {
                switch (args[0]) {
                    case "version":
                        sender.sendMessage(TextComponent.of("gFeatures Version " + gFeatures.version, TextColor.GRAY));
                        break;
                    case "help":
                        sender.sendMessage(TextComponent.of("------Help------", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("/gfp version : States the version.", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("/gfp list : Lists all features with their states and versions also.", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("/gfp featurestate <Feature> : Gets the state of the feature.", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("/gfp send <Cliote> <Identifier> <Message> : Sends a manual message to the ClioteSky server.", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("/gfp lookup <Player Name> : Lookup player info (UUID and previous names).", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("/gfp debug : Turns on debug messages.", TextColor.GRAY));
                        break;
                    case "list":
                        List<gFeature> features = gFeatures.getFeatures();
                        sender.sendMessage(TextComponent.of("Features:", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("Enabled:", TextColor.GRAY));
                        for (gFeature feature : features) {
                            if (feature.isEnabled()) {
                                sender.sendMessage(TextComponent.of(" - " + feature.getName() + " " + feature.getVersion(), TextColor.GRAY));
                            }
                        }
                        sender.sendMessage(TextComponent.of("Disabled:", TextColor.GRAY));
                        for (gFeature feature : features) {
                            if (!feature.isEnabled()) {
                                sender.sendMessage(TextComponent.of(" - " + feature.getName() + " " + feature.getVersion(), TextColor.GRAY));
                            }
                        }
                        break;
                    case "featurestate":
                        sender.sendMessage(TextComponent.of("Usage: /gfp featurestate <Plugin>", TextColor.GRAY));
                        break;
                    case "debug":
                        if (gFeatures.debug) {
                            gFeatures.debug = false;
                            sender.sendMessage(TextComponent.of("Turned off debugging.", TextColor.GRAY));
                        } else {
                            gFeatures.debug = true;
                            sender.sendMessage(TextComponent.of("Turned on debugging.", TextColor.GRAY));
                        }
                        break;
                    default:
                        sender.sendMessage(TextComponent.of("Please do /gFeaturesBungee help.", TextColor.GRAY));
                        break;
                }
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "featurestate":
                        gFeature feature = gFeatures.getFeature(args[1]);
                        sender.sendMessage(TextComponent.of("Feature " + args[1] + " state is " + feature.isEnabled(), TextColor.GRAY));
                        break;
                    case "lookup":
                        sender.sendMessage(TextComponent.of("----- Player info for " + args[1] + " -----", TextColor.GRAY));
                        sender.sendMessage(TextComponent.of("UUID: " + ResolverFetcher.getUUIDfromName(args[1]), TextColor.GRAY));
                        StringBuilder prev = new StringBuilder("Previous names: ");
                        List<String> names = ResolverFetcher.getAllNames(ResolverFetcher.getUUIDfromName(args[1]));
                        for (int i = 1; i < names.size(); i++) {
                            prev.append(names.get(i)).append(" ");
                        }
                        sender.sendMessage(TextComponent.of(prev.toString(), TextColor.GRAY));
                        break;
                    default:
                        sender.sendMessage(TextComponent.of("Please do /gfp help.", TextColor.GRAY));
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
                        sender.sendMessage(TextComponent.of("Sent message " + output + " to ClioteSky.", TextColor.GRAY));
                        break;
                    default:
                        sender.sendMessage(TextComponent.of("Please do /gfp help.", TextColor.GRAY));
                        break;
                }
            } else {
                sender.sendMessage(TextComponent.of("Please do /gfp help.", TextColor.GRAY));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
