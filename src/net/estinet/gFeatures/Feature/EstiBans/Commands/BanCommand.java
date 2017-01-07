package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Listeners;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class BanCommand extends EstiCommand{

	public BanCommand(gFeature feature) {
		super("ban", "gFeatures.EstiBans.ban", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix + "/ban [Player] [Server] [Reason]");
		}
		else{
			try{
				if(EstiBans.isBannedOn(args[0], args[1])){
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Player already banned on this server!"));
				}
				else{
					String reason = "";
					for(int i = 2; i < args.length; i++){
						reason += args[i] + " ";
					}
					EstiBans.banPlayer(args[0], args[1], reason);
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Banned player " + args[0] + " for a long time on " + args[1] + " because of \"" + reason + "\""));
				}
			}
			catch(Exception e){
				if(Listeners.debug){
					e.printStackTrace();
				}
				sender.sendMessage(EstiBans.estiBansPrefix + ChatColor.RED + "Error with your input, try again!");
			}
		}
	}
}
