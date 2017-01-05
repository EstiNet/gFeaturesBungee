package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Listeners;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class BanCommand extends EstiCommand{

	public BanCommand(gFeature feature) {
		super("ban", "gFeatures.EstiBans.ban", new String[0], feature);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix + "/ban [Player] [Length] [Server] [Reason] || /ban [Player] [Server] [Reason]");
		}
		else if(args.length == 3){
			
		}
		else{
			try{
			String reason = "";
			for(int i = 3; i < args.length; i++){
				reason += args[i];
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
				
			}
			EstiBans.banPlayer(args[0], args[2], time, reason);
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
