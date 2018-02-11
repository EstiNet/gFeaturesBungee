package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

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

public class KickCommand extends EstiCommand{

	public KickCommand(gFeature feature) {
		super("kick", "gFeatures.EstiBans.kick", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 2){
			sender.sendMessage(EstiBans.estiBansPrefix + "/kick [Player] [Reason]");
		}
		else{
			if(ProxyServer.getInstance().getPlayer(args[0]) == null){
				sender.sendMessage(EstiBans.estiBansPrefix + "No one is online with that username.");
			}
			else{
				String reason = "";
				for(int i = 1; i < args.length; i++){
					reason += args[i] + " ";
				}
				EstiBans.kickPlayer(args[0], reason);
				sender.sendMessage(EstiBans.estiBansPrefix + "Kicked Player " + args[0] + " for \"" + reason + "\"");
			}
		}
	}	
}
