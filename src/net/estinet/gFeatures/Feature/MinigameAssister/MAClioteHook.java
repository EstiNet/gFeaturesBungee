package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class MAClioteHook extends ClioteHook{

	public MAClioteHook(gFeature feature) {
		super(feature, "mgcomplete");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			Debug.print("Comparing " + clioteName + " " + categoryName);
			if(!MinigameAssister.servers.containsKey(clioteName)){
				MinigameAssister.servers.put(clioteName, MGState.OFFLINE);
			}
			else{
				Debug.print("Was found.");
				MinigameAssister.servers.replace(clioteName, MGState.OFFLINE);
			}
			SendAll sa = new SendAll();
			sa.sendAllInfo();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
