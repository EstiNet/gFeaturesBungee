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
		try{
			CliotePing cp = new CliotePing();
			for(MGServer mgs : MinigameAssister.servers.keySet()){
				cp.sendMessage("mgrecieve " + mgs.getName() + " " + MinigameAssister.servers.get(mgs), clioteName);
			} //MGS.getName() should be the name of the Cliote as well as server...
			cp.sendMessage("mgdone", clioteName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
