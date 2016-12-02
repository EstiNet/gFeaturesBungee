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
					break; //Don't make it enable every time, use other if requested
				case "otherup":
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.WAITING);
					ServerInfo cur = BungeeCord.getInstance().getServerInfo(FusionPlay.getPairedConFromID(clioteName).getClioteName());
					ServerInfo si = BungeeCord.getInstance().getServerInfo(clioteName);
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(clioteName).getClioteName())).setID(-1);
					for(ProxiedPlayer pp : cur.getPlayers()){
						pp.connect(si); //make sure the other server restarts after everyone logs off
					} //if the server just came back from a forced minigame switch
					break;
				case "started": //the server has started the minigame
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.INGAME);
					break;
				case "done":
					FusionPlay.replaceConnection(clioteName); //when a minigame has finished
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

