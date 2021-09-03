package net.estinet.gFeatures.Feature.EstiBans.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
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

public class UnmuteCommand extends EstiCommand{

	public UnmuteCommand(gFeature feature) {
		super(new String[] {"unmute"}, "gFeatures.EstiBans.mute", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		if(args.length != 2){
			sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("/unmute [Player] [Server]")));
		}
		else{
			if(!EstiBans.isMutedOn(args[0], args[1])){
				sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("Player is not muted!", NamedTextColor.RED)));
			}
			else{
				sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("Player " + args[0] + " has been unmuted on " + args[1] + ".")));
				EstiBans.unmutePlayer(args[0], args[1]);
			}
		}
	}
}
