package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class KickCommand extends EstiCommand{

	public KickCommand(gFeature feature) {
		super("kick", "gFeatures.EstiBans.kick", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length < 2){
			sender.sendMessage(EstiBans.estiBansPrefix + "/kick [Player] [Reason]");
		}
		else{
			if(ProxyServer.getInstance().getPlayer(args[0]) == null){
				sender.sendMessage(EstiBans.estiBansPrefix + "No one is online with that username.");
			}
			else{
				String reason = "";
				for(int i = 1; i < args.length; i++){
					reason += args[i] + " ";
				}
				EstiBans.kickPlayer(args[0], reason);
				sender.sendMessage(EstiBans.estiBansPrefix + "Kicked Player " + args[0] + " for \"" + reason + "\"");
			}
		}
	}	
}
