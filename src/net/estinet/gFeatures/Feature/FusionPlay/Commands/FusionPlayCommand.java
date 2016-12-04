package net.estinet.gFeatures.Feature.FusionPlay.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.FusionPlay.FusionCon;
import net.estinet.gFeatures.Feature.FusionPlay.FusionPlay;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

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
