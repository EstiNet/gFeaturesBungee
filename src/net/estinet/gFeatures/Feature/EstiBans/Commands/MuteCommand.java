package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Listeners;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class MuteCommand extends EstiCommand{

	public MuteCommand(gFeature feature) {
		super("mute", "gFeatures.EstiBans.mute", new String[0], feature);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix + "/mute [Player] [Length] [Server] [Reason] || /mute [Player] [Server] [Reason]");
		}
		else if(args.length == 3){
			if(EstiBans.isMutedOn(args[0], args[1])){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Player already muted on this server!"));
			}
			else{
				try{
					String reason = "";
					for(int i = 2; i < args.length; i++){
						reason += args[i] + " ";
					}
					EstiBans.mutePlayer(args[0], args[1], reason);
				}
				catch(Exception e){
					if(Listeners.debug){
						e.printStackTrace();
					}
					sender.sendMessage(EstiBans.estiBansPrefix + ChatColor.RED + "Error with your input, try again!");
				}
			}
		}
		else{
			try{
				if(EstiBans.isMutedOn(args[0], args[1])){
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + ChatColor.RED + "Player already muted on this server!"));
				}
				else{
					String reason = "";
					for(int i = 3; i < args.length; i++){
						reason += args[i] + " ";
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
						EstiBans.mutePlayer(args[0], args[2], reason);
						return;
					}
					EstiBans.mutePlayer(args[0], args[2], time, reason);
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
