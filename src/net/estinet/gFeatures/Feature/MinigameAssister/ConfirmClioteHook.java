package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class ConfirmClioteHook extends ClioteHook{

	public ConfirmClioteHook(gFeature feature) {
		super(feature, "mghello");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			Debug.print("Comparing " + clioteName + " " + categoryName);
			if(!MinigameAssister.servers.containsKey(clioteName)){
				MinigameAssister.servers.put(clioteName, MGState.WAIT);
			}
			else{
				Debug.print("Was found.");
				MinigameAssister.servers.replace(clioteName, MGState.WAIT);
			}
			SendAll sa = new SendAll();
			sa.sendAllInfo();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
