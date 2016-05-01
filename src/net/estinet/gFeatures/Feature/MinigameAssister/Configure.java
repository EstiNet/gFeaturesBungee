package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.Feature.MinigameAssister.Commands.SlashListgames;

public class Configure{
	@Configs
	public static void onSetup(){
		MinigameAssister base = new MinigameAssister("MinigameAssister", "1.0.1");
		Basic.addFeature(base);
		
		MAClioteHook grch = new MAClioteHook(base);
		ClioteSky.addClioteHook(grch);
		
		ConfirmClioteHook cch = new ConfirmClioteHook(base);
		ClioteSky.addClioteHook(cch);
		StartClioteHook mgsch = new StartClioteHook(base);
		ClioteSky.addClioteHook(mgsch);
		MGGetClioteHook mgch = new MGGetClioteHook(base);
		ClioteSky.addClioteHook(mgch);
		MGMapClioteHook mmap = new MGMapClioteHook(base);
		ClioteSky.addClioteHook(mmap);
		Basic.addCommand(new SlashListgames(base));
	}
}
