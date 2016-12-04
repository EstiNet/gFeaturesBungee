package net.estinet.gFeatures.Feature.FusionPlay.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.CommandSender;

public class FusionPlayCommand extends EstiCommand{

	public FusionPlayCommand(gFeature feature) {
		super("fusionplay", "gFeatures.admin", new String[0], feature);
	}

	@Override
	public void execute(CommandSender arg0, String[] args) {
		if(args.length == 1){
			switch(args[0]){
			
			}
		}
	}

}
