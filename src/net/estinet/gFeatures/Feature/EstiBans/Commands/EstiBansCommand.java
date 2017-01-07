package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class EstiBansCommand extends EstiCommand{

	public EstiBansCommand(gFeature feature) {
		super("estibans", "gFeatures.admin", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("help")){
				sender.sendMessage(ChatColor.AQUA + "----- EstiBans -----");
				sender.sendMessage(ChatColor.AQUA + "/estibans info [Player] - Obtains player information.");
				sender.sendMessage(ChatColor.AQUA + "/estibans reload - Reloads from the config.");
			}
			else if(args[0].equalsIgnoreCase("reload")){
				
			}
			else{
				sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
			}
		}
		else if(args.length == 1){
			if(args[0].equalsIgnoreCase("info")){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Player info for " + args[1]));
				sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Bans:"));
				for(String str : EstiBans.getBans(args[0])){
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
				}
				sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Mutes"));
				for(String str : EstiBans.getMutes(args[0])){
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
				}
				sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Warnings"));
				for(String str : EstiBans.getWarnings(args[0])){
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
				}
			}
			else{
				sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
			}
		}
		else{
			sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
		}
	}	
}
