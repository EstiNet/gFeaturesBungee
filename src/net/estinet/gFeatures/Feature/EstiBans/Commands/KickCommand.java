package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.CommandSender;

public class KickCommand extends EstiCommand{

	public KickCommand(gFeature feature) {
		super("kick", "gFeatures.EstiBans.kick", new String[0], feature);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
	}	
}
