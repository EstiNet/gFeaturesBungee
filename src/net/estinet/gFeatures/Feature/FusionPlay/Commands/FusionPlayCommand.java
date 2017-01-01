package net.estinet.gFeatures.Feature.FusionPlay.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.FusionPlay.FusionCon;
import net.estinet.gFeatures.Feature.FusionPlay.FusionPlay;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2017 EstiNet

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
		super("fusionplay", "gFeatures.admin", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 1){
			args[0] = args[0].toLowerCase();
			switch(args[0]){
			case "help":
				sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "----------" + ChatColor.RESET + "" + ChatColor.AQUA + "FusionPlay Help" + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "----------");
				sender.sendMessage(ChatColor.DARK_GRAY + "/fusionplay list : Lists all connection data.");
				break;
			case "list":
				sender.sendMessage(ChatColor.AQUA + "FusionPlay List:");
				for(FusionCon fc : FusionPlay.getConnections()){
					sender.sendMessage(ChatColor.DARK_AQUA + fc.getClioteName() + "| ID:" + fc.getID() + " Status:" + fc.getStatus() + " Type:" + fc.getCurrentType());
				}
				break;
			}
		}
	}

}
