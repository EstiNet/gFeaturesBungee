package net.estinet.gFeatures.Feature.FusionPlay.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.FusionPlay.FusionCon;
import net.estinet.gFeatures.Feature.FusionPlay.FusionPlay;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;

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

public class FusionPlayCommand extends EstiCommand{

	public FusionPlayCommand(gFeature feature) {
		super(new String[]{"fusionplay"}, "gFeatures.admin", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		if(args.length == 1){
			args[0] = args[0].toLowerCase();
			switch(args[0]){
				case "help":
					sender.sendMessage(ComponentSerializers.LEGACY.deserialize("&8&m----------&r&bFusionPlay Help&8&m----------", '&'));
					sender.sendMessage(ComponentSerializers.LEGACY.deserialize("&8/fusionplay list : Lists all connection data.", '&'));
					break;
				case "list":
					sender.sendMessage(TextComponent.of("FusionPlay List:", TextColor.AQUA));
					for(FusionCon fc : FusionPlay.getConnections()){
						sender.sendMessage(TextComponent.of(fc.getClioteName() + "| ID:" + fc.getID() + " Status:" + fc.getStatus() + " Type:" + fc.getCurrentType(), TextColor.DARK_AQUA));
					}
					break;
			}
		}
	}
}
