package net.estinet.gFeatures.Feature.EstiBans.Commands;

import java.util.UUID;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class UnwarnCommand extends EstiCommand{

	public UnwarnCommand(gFeature feature) {
		super("unwarn", "gFeatures.EstiBans.warn", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length != 2){
			sender.sendMessage(EstiBans.estiBansPrefix + "/unwarn [Player] [ID]");
		}
		else{
			if(!EstiBans.isValidWarnID(UUID.fromString(ResolverFetcher.getUUIDfromName(args[0])), args[1])){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Invalid ID!"));
			}
			else{
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Player " + args[0] + " has been unwarned from id " + args[1] + "."));
				EstiBans.unmutePlayer(args[0], args[1]);
			}
		}
	}	

}
