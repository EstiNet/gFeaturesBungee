package net.estinet.gFeatures.Feature.EstiBans.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.CommandSender;

public class MuteCommand extends EstiCommand{

	public MuteCommand(gFeature feature) {
		super("mute", "gFeatures.EstiBans.mute", new String[0], feature);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
	}
}
