package net.estinet.gFeatures.Feature.FusionPlay;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;

public class Configure{
	@Configs
	public static void onSetup(){
		FusionPlay base = new FusionPlay("FusionPlay", "1.0.0");
		Basic.addFeature(base);
	}
}
