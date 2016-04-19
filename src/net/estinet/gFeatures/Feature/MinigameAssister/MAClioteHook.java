package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class MAClioteHook extends ClioteHook{

	public MAClioteHook(gFeature feature) {
		super(feature, "mgcomplete");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			MinigameAssister.servers.remove(new MGServer(clioteName, categoryName));
			MinigameAssister.servers.put(new MGServer(clioteName, categoryName), MGState.OFFLINE);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
