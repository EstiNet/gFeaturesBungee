package net.estinet.gFeatures.Feature.EstiBans.Commands;

import java.util.UUID;

import net.estinet.gFeatures.EstiCommand;
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

public class UnwarnCommand extends EstiCommand{

	public UnwarnCommand(gFeature feature) {
		super("unwarn", "gFeatures.EstiBans.warn", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length != 2){
			sender.sendMessage(EstiBans.estiBansPrefix + "/unwarn [Player] [ID]");
		}
		else{
			if(!EstiBans.isValidWarnID(UUID.fromString(ResolverFetcher.getUUIDfromName(args[0])), args[1])){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Invalid ID!"));
			}
			else{
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Player " + args[0] + " has been unwarned from id " + args[1] + "."));
				EstiBans.unmutePlayer(args[0], args[1]);
			}
		}
	}	

}