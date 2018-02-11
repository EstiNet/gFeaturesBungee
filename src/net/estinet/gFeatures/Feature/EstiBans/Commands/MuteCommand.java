package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Listeners;
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

public class MuteCommand extends EstiCommand{

	public MuteCommand(gFeature feature) {
		super("mute", "gFeatures.EstiBans.mute", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix + "/mute [Player] [Server] [Reason]");
		}
		else if(args.length == 3){
			if(EstiBans.isMutedOn(args[0], args[1])){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Player already muted on this server!"));
			}
			else{
				try{
					String reason = "";
					for(int i = 2; i < args.length; i++){
						reason += args[i] + " ";
					}
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Muted player " + args[0] + " for a long time on " + args[1] + " because of \"" + reason + "\""));
					EstiBans.mutePlayer(args[0], args[1], reason);
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
}
