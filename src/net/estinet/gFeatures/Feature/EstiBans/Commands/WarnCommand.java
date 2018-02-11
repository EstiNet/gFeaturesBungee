package net.estinet.gFeatures.Feature.EstiBans.Commands;

import java.util.UUID;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Listeners;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

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

public class WarnCommand extends EstiCommand{

	public WarnCommand(gFeature feature) {
		super("warn", "gFeatures.EstiBans.warn", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix + "/warn [Player] [Length] [Reason]");
		}
		else{
			try{
				String reason = "";
				for(int i = 2; i < args.length; i++){
					reason += args[i] + " ";
				}
				long time = System.currentTimeMillis();
				long numeral = Long.parseLong(args[1].replaceAll("\\D+",""));
				if(args[1].contains("n")){
					time += numeral * 1e-6;
				}
				else if(args[1].contains("s")){
					time += numeral * 1000;
				}
				else if(args[1].contains("m")){
					time += numeral * 60000;
				}
				else if(args[1].contains("h")){
					time += numeral * 3.6e+6;
				}
				else if(args[1].contains("d")){
					time += numeral * 8.64e+7;
				}
				else{
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Incorrect timestamp."));
					return;
				}
					EstiBans.warnPlayer(args[0], EstiBans.getNextWarnID(UUID.fromString(ResolverFetcher.getUUIDfromName(args[0]))), time, reason);
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Warned player " + args[0] + " for " + args[1] + " on " + args[2] + " because of \"" + reason + "\""));
			}
			catch(Exception e){
				if(Listeners.debug){
					e.printStackTrace();
				}
				sender.sendMessage(EstiBans.estiBansPrefix + ChatColor.RED + "Error with your input, try again!");
			}
		}
	}


}
