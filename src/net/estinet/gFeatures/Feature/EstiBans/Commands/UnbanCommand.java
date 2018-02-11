package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
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

public class UnbanCommand extends EstiCommand{

	public UnbanCommand(gFeature feature) {
		super("unban", "gFeatures.EstiBans.ban", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length != 2){
			sender.sendMessage(EstiBans.estiBansPrefix + "/unban [Player] [Server]");
		}
		else{
			if(!EstiBans.isBannedOn(args[0], args[1])){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Player is not banned!"));
			}
			else{
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Player " + args[0] + " has been unbanned on " + args[1] + "."));
				EstiBans.unbanPlayer(args[0], args[1]);
			}
		}
	}	
}
