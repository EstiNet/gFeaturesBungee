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
	//hi 2017 :DDDDDDDD
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			if(!clioteName.equals("Bungee")){
				CliotePing cp = new CliotePing();
				switch(args.get(0)){
				case "online": //[type] parameter
					if(FusionPlay.hasConnection(clioteName)){
						if(FusionPlay.getConnection(clioteName).getStatus().equals(FusionStatus.INGAME) || FusionPlay.getConnection(clioteName).getStatus().equals(FusionStatus.WAITING)){
							FusionPlay.replaceConnection(clioteName);
						}
						else if(FusionPlay.checkIfServerNeed()){
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.WAITING);
							cp.sendMessage("fusionplay start", clioteName);
							for(int i = 0; true; i++){
								if(!FusionPlay.isValidID(i)){
									FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(i);
									FusionPlay.addID(i);
									break;
								}
							}
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setCurrentType(args.get(1));
							FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getID(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + clioteName);
							Debug.print("[FusionPlay] Added " + clioteName + " into ID server pool.");
						}
						else{
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.NOTASSIGNED);
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(-1);
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setCurrentType(args.get(1));
							FusionPlay.queueConnections.add(FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)));
							Debug.print("[FusionPlay] Added " + clioteName + " to waiting queue.");
						}
					}
					else{
						FusionCon fc = new FusionCon(clioteName);
						if(FusionPlay.checkIfServerNeed()){
							fc.setStatus(FusionStatus.WAITING);
							cp.sendMessage("fusionplay start", clioteName);
							for(int i = 0; true; i++){
								if(!FusionPlay.isValidID(i)){
									fc.setID(i);
									FusionPlay.addID(i);
									break;
								}
							}
							fc.setCurrentType(args.get(1));
							FusionPlay.addConnection(fc);
							FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getID(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + clioteName);
							Debug.print("[FusionPlay] Added " + clioteName + " into ID server pool.");
						}
						else{
							fc.setID(-1);
							fc.setCurrentType(args.get(1));
							fc.setStatus(FusionStatus.NOTASSIGNED);
							FusionPlay.queueConnections.add(fc);
							FusionPlay.addConnection(fc);
							Debug.print("[FusionPlay] Added " + clioteName + " to waiting queue.");
						}
					}
					break; //Don't make it enable every time, use other if requested
				case "otherup": //[currentminigametype] sent parameter
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.WAITING);
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setCurrentType(args.get(1));
					ServerInfo cur = BungeeCord.getInstance().getServerInfo(FusionPlay.getPairedConFromID(clioteName).getClioteName());
					ServerInfo si = BungeeCord.getInstance().getServerInfo(clioteName);
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(clioteName).getClioteName())).setID(-1);
					cp.sendMessage("fusionplay start", clioteName);
					for(ProxiedPlayer pp : cur.getPlayers()){
						pp.connect(si); //make sure the other server restarts after everyone logs off
					} //if the server just came back from a forced minigame switch
					FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(FusionPlay.getPairedConFromID(clioteName).getClioteName()).getID(), FusionPlay.getConnection(FusionPlay.getPairedConFromID(clioteName).getClioteName()).getCurrentType() + " " + FusionPlay.getPairedConFromID(clioteName).getClioteName());
					break;
				case "started": //the server has started the minigame
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.INGAME);
					break;
				case "done":
					FusionPlay.replaceConnection(clioteName); //when a minigame has finished
					break;
				case "otheralive"://when checking restarted cache servers
					FusionCon fcs = null;
					for(int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++){
						if(FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(clioteName)){
							fcs = FusionPlay.cliotesOnCheck.get(i);
							FusionPlay.cliotesOnCheck.remove(i);
						}
					}
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.OFFLINE);
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(fcs.getID());
					break;
				case "cachealive"://when checking cached servers
					FusionCon fc = null;
					for(int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++){
						if(FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(clioteName)){
							fc = FusionPlay.cliotesOnCheck.get(i);
							FusionPlay.cliotesOnCheck.remove(i);
						}
					}
					fc.setStatus(FusionStatus.WAITING);
					ServerInfo curs = BungeeCord.getInstance().getServerInfo(FusionPlay.getPairedConFromID(clioteName).getClioteName());
					ServerInfo sis = BungeeCord.getInstance().getServerInfo(fc.getClioteName());
					for(ProxiedPlayer pp : curs.getPlayers()){
						pp.connect(sis);
					}
					FusionPlay.syncCommands.set("server-" + fc.getID(), fc.getCurrentType() + " " + fc.getClioteName());
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(clioteName).getClioteName())).setID(-1); //Make sure that the server restart 
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

