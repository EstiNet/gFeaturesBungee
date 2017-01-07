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

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 3){
			sender.sendMessage(EstiBans.estiBansPrefix + "/mute [Player] [Server] [Reason]");
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
					sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Muted player " + args[0] + " for a long time on " + args[1] + " because of \"" + reason + "\""));
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
	}
}
