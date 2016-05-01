package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class ConfirmClioteHook extends ClioteHook{

	public ConfirmClioteHook(gFeature feature) {
		super(feature, "mghello");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			MinigameAssister.servers.remove(new MGServer(clioteName, categoryName));
			MinigameAssister.servers.put(new MGServer(clioteName, categoryName), MGState.WAIT);
			SendAll sa = new SendAll();
			sa.sendAllInfo();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
