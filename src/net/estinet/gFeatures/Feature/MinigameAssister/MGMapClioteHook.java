package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class MGMapClioteHook extends ClioteHook{

	public MGMapClioteHook(gFeature feature) {
		super(feature, "mgmap");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			Debug.print("Comparing mgmap " + clioteName + " " + categoryName);
			if(!MinigameAssister.maps.containsKey(clioteName)){
				MinigameAssister.maps.put(clioteName, args.get(0));
			}
			else{
				Debug.print("Was found.");
				MinigameAssister.maps.replace(clioteName, args.get(0));
			}
			SendAll sa = new SendAll();
			sa.sendAllInfo();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
