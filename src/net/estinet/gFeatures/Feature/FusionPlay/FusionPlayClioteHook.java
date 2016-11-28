package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
					break;
				case "done":
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.OFFLINE);
					ServerInfo si = BungeeCord.getInstance().getServerInfo(clioteName);
					for(ProxiedPlayer pp : si.getPlayers()){
						
					}
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

