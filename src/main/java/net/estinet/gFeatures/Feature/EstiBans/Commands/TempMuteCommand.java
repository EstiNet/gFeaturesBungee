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

public class TempMuteCommand extends EstiCommand{

	public TempMuteCommand(gFeature feature) {
		super(new String[]{"tempmute"}, "gFeatures.EstiBans.mute", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("/tempmute [Player] [Length] [Server] [Reason]")));
		}
		else{
			try{
				if(EstiBans.isMutedOn(args[0], args[1])){
					sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Player already muted on this server!", TextColor.RED)));
				}
				else{
					StringBuilder reason = new StringBuilder();
					for(int i = 3; i < args.length; i++){
						reason.append(args[i]).append(" ");
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
					else if(args[1].contains("f")){
						EstiBans.mutePlayer(args[0], args[2], reason.toString());
						return;
					}
					else{
						sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Incorrect timestamp.", TextColor.RED)));
						return;
					}
					sender.sendMessage(EstiBans.estiBansPrefix.append(TextComponent.of("Muted player " + args[0] + " for " + args[1] + " on " + args[1] + " because of \"" + reason + "\"")));
					EstiBans.mutePlayer(args[0], args[2], Double.toString(time), reason.toString());
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
