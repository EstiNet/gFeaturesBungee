package net.estinet.gFeatures.Feature.EstiBans.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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
		super(new String[]{"mute"}, "gFeatures.EstiBans.mute", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("/mute [Player] [Server] [Reason]")));
		}
		else if(args.length == 3){
			if(EstiBans.isMutedOn(args[0], args[1])){
				sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("Player already muted on this server!", NamedTextColor.RED)));
			}
			else{
				try{
					StringBuilder reason = new StringBuilder();
					for(int i = 2; i < args.length; i++){
						reason.append(args[i]).append(" ");
					}
					sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("Muted player " + args[0] + " for a long time on " + args[1] + " because of \"" + reason + "\"")));
					EstiBans.mutePlayer(args[0], args[1], reason.toString());
				}
				catch(Exception e){
					if(gFeatures.debug){
						e.printStackTrace();
					}
					sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("Error with your input, try again!", NamedTextColor.RED)));
				}
			}
		}
	}
}
