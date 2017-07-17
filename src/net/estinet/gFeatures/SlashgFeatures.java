package net.estinet.gFeatures;

import java.util.List;

import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.ClioteSky.Network.NetworkThread;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2017 EstiNet

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

public class SlashgFeatures extends Command{
	public SlashgFeatures(){
		super("gFeaturesBungee", "gFeatures.admin", "gfb");
	}
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		try {
			if(args.length == 0){
				sender.sendMessage(ChatColor.GRAY + "Please do /gFeaturesBungee help.");
			}
			else if(args.length == 1){
				switch(args[0]){
				case "version":
					sender.sendMessage(ChatColor.GRAY + "gFeatures Version " + Listeners.version);
					break;
				case "help":
					sender.sendMessage(ChatColor.GRAY + "------Help------");
					sender.sendMessage(ChatColor.GRAY + "/gFeaturesBungee version : States the version.");
					sender.sendMessage(ChatColor.GRAY + "/gFeaturesBungee list : Lists all features with their states and versions also.");
					sender.sendMessage(ChatColor.GRAY + "/gFeaturesBungee featurestate <Feature> : Gets the state of the feature.");
					sender.sendMessage(ChatColor.GRAY + "/gFeaturesBungee send <Message> : Sends a manual message to the ClioteSky server.");
					sender.sendMessage(ChatColor.GRAY + "/gFeaturesBungee lookup <Player Name> : Lookup player info (UUID and previous names).");
					sender.sendMessage(ChatColor.GRAY + "/gFeaturesBungee debug : Turns on debug messages.");
					break;
				case "list":
					List<gFeature> features = gFeatures.getFeatures();
					sender.sendMessage(ChatColor.GRAY + "Features:");
					sender.sendMessage(ChatColor.GRAY + "Enabled:");
					for(gFeature feature : features){
						if(feature.getState().equals(FeatureState.ENABLE)){
							sender.sendMessage(ChatColor.GRAY + " - " + feature.getName() + " " + feature.getVersion());
						}
					}
					sender.sendMessage(ChatColor.GRAY + "Disabled:");
					for(gFeature feature : features){
						if(feature.getState().equals(FeatureState.DISABLE)){
							sender.sendMessage(ChatColor.GRAY + " - " + feature.getName() + " " + feature.getVersion());
						}
					}
					break;
				case "featurestate":
					sender.sendMessage(ChatColor.GRAY + "Usage: /gFeaturesBungee featurestate <Plugin>");
					break;
				case "debug":
					if(Listeners.debug == true){
						Listeners.debug = false;
						sender.sendMessage(ChatColor.GRAY + "Turned off debugging.");
					}
					else{
						Listeners.debug = true;
						sender.sendMessage(ChatColor.GRAY + "Turned on debugging.");
					}
					break;
				default:
					sender.sendMessage(ChatColor.GRAY + "Please do /gFeaturesBungee help.");
					break;
				}
			}
			else if(args.length == 2){
				switch(args[0]){
				case "featurestate":
					gFeature feature = gFeatures.getFeature(args[1]);
					sender.sendMessage(ChatColor.GRAY + "Feature " + args[1] + " state is " + feature.getState().toString());
					break;
				case "send":
					NetworkThread nt = new NetworkThread();
					nt.sendOutput(args[1]);
					sender.sendMessage(ChatColor.GRAY + "Sent message " + args[1] + " to ClioteSky.");
					break;
					case "lookup":
						sender.sendMessage(ChatColor.GRAY + "----- Player info for " + args[1] + " -----");
						sender.sendMessage(ChatColor.GRAY + "UUID: " + ResolverFetcher.getUUIDfromName(args[1]));
						String prev = ChatColor.GRAY + "Previous names: ";
						List<String> names = ResolverFetcher.getAllNames(ResolverFetcher.getUUIDfromName(args[1]));
						for(int i = 1; i < names.size(); i++){
							prev += names.get(i) + " ";
						}
						sender.sendMessage(prev);
					break;
				default:
					sender.sendMessage(ChatColor.GRAY + "Please do /gFeaturesBungee help.");
					break;
				}
			}
			else{
				switch(args[0]){
				case "send":
					String output = "";
					for(int i = 0; i < args.length-1; i++){
						output += args[i+1] + " ";
					}
					NetworkThread nt = new NetworkThread();
					nt.sendOutput(output);
					sender.sendMessage(ChatColor.GRAY + "Sent message " + output + "to ClioteSky.");
					break;
				default:
					sender.sendMessage(ChatColor.GRAY + "Please do /gFeaturesBungee help.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
