package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

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
