package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.ClioteSky.ClioteSky;

public class Configure{
	@Configs
	public static void onSetup(){
		MinigameAssister base = new MinigameAssister("MinigameAssister", "1.0.0");
		Basic.addFeature(base);
		
		MAClioteHook grch = new MAClioteHook(base);
		ClioteSky.addClioteHook(grch);
		
		ConfirmClioteHook cch = new ConfirmClioteHook(base);
		ClioteSky.addClioteHook(cch);
	}
}
