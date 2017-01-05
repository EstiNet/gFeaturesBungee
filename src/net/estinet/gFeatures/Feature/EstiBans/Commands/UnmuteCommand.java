package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class UnmuteCommand extends EstiCommand{

	public UnmuteCommand(gFeature feature) {
		super("unmute", "gFeatures.EstiBans.mute", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length != 2){
			sender.sendMessage(EstiBans.estiBansPrefix + "/unmute [Player] [Server]");
		}
		else{
			if(EstiBans.isMutedOn(args[0], args[1])){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Player already muted!"));
			}
			else{
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ));
				EstiBans.unmutePlayer(args[0], args[1]);
			}
		}
	}	
}
