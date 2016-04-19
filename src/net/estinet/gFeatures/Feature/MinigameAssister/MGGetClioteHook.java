package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;

public class MGGetClioteHook extends ClioteHook{

	public MGGetClioteHook(gFeature feature) {
		super(feature, "mgget");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		String category = args.get(0);
		try{
			CliotePing cp = new CliotePing();
			for(MGServer mgs : MinigameAssister.servers.keySet()){
				if(mgs.getCategory().equals(category)){
					cp.sendMessage("mgreturn recieve " + mgs.getName() + " " + MinigameAssister.servers.get(mgs), clioteName);
				}
			}
			cp.sendMessage("mgreturn done", clioteName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
