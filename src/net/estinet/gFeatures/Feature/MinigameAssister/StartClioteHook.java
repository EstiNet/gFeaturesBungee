package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class StartClioteHook extends ClioteHook{

	public StartClioteHook(gFeature feature) {
		super(feature, "mgstart");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			Debug.print("Comparing " + clioteName + " " + categoryName);
			if(!MinigameAssister.servers.containsKey(new MGServer(clioteName, categoryName))){
				MinigameAssister.servers.put(clioteName, MGState.STARTED);
			}
			else{
				Debug.print("Was found.");
				MinigameAssister.servers.replace(clioteName, MGState.STARTED);
			}
			SendAll sa = new SendAll();
			sa.sendAllInfo();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
