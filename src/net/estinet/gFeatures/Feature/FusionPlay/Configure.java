package net.estinet.gFeatures.Feature.FusionPlay;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.Feature.FusionPlay.Commands.FusionPlayCommand;
import net.estinet.gFeatures.Feature.MinigameAssister.ConfirmClioteHook;

public class Configure{
	@Configs
	public static void onSetup(){
		FusionPlay base = new FusionPlay("FusionPlay", "1.0.0");
		Basic.addFeature(base);
		
		FusionPlayCommand fpc = new FusionPlayCommand(base);
		Basic.addCommand(fpc);
		
		FusionPlayClioteHook cch = new FusionPlayClioteHook(base);
		ClioteSky.addClioteHook(cch);
	}
}
