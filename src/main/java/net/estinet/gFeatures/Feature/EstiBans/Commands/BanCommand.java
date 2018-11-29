package net.estinet.gFeatures.Feature.EstiBans.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

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

public class BanCommand extends EstiCommand{

	public BanCommand(gFeature feature) {
		super(new String[]{"ban"}, "gFeatures.EstiBans.ban", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("/ban [Player] [Server] [Reason]")));
		}
		else{
			try{
				if(EstiBans.isBannedOn(args[0], args[1])){
					sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of( "Player already banned on this server!", TextColor.RED)));
				}
				else{
					StringBuilder reason = new StringBuilder();
					for(int i = 2; i < args.length; i++){
						reason.append(args[i]).append(" ");
					}
					EstiBans.banPlayer(args[0], args[1], reason.toString());
					sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Banned player " + args[0] + " for a long time on " + args[1] + " because of \"" + reason + "\"")));
				}
			}
			catch(Exception e){
				if(gFeatures.debug){
					e.printStackTrace();
				}
				sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Error with your input, try again!", TextColor.RED)));
			}
		}
	}
}
