package net.estinet.gFeatures.Feature.EstiChat;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.ClioteSky.ClioteSky;

public class Configure{
	@Configs
	public static void onSetup(){
		EstiChat base = new EstiChat("EstiChat", "1.0.0");
		Basic.addFeature(base);
		
		ChatClioteHook mmap = new ChatClioteHook(base);
		ClioteSky.addClioteHook(mmap);
	}
}
