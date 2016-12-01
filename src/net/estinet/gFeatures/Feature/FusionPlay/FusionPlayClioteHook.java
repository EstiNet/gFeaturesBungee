package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;

public class FusionPlayClioteHook extends ClioteHook{

	public FusionPlayClioteHook(gFeature feature) {
		super(feature, "fusionplay");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			if(!clioteName.equals("Bungee")){
				CliotePing cp = new CliotePing();
				switch(args.get(0)){
				case "online":
					if(FusionPlay.hasConnection(clioteName)){
						FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.NOTASSIGNED);
						FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(-1);
						FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setCurrentType("NONE");
						FusionPlay.queueConnections.add(FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)));
						Debug.print("[FusionPlay] Added " + clioteName + " to waiting queue.");
					}
					else{
						FusionCon fc = new FusionCon(clioteName);
						fc.setID(0);
						fc.setCurrentType("NONE");
						fc.setStatus(FusionStatus.NOTASSIGNED);
						FusionPlay.addConnection(fc);
					}
					break; //Don't make it enable every time, use other if requested
				case "otherup":
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.WAITING);
					
					break;
				case "done":
					FusionPlay.replaceConnection(clioteName);
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

