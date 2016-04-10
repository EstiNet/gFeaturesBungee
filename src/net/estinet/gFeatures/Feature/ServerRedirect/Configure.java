package net.estinet.gFeatures.Feature.ServerRedirect;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.ClioteSky.ClioteSky;

public class Configure{
	@Configs
	public static void onSetup(){
		ServerRedirect base = new ServerRedirect("ServerRedirect", "1.0.0");
		Basic.addFeature(base);
		SRClioteHook grch = new SRClioteHook(base);
		ClioteSky.addClioteHook(grch);
	}
}
