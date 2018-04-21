package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSkyOld.API.ClioteHook;
import net.estinet.gFeatures.ClioteSkyOld.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2018 EstiNet

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
				case "online": //[type] parameter
					if(FusionPlay.hasConnection(clioteName)){
						if(FusionPlay.getConnection(clioteName).getStatus().equals(FusionStatus.INGAME) || FusionPlay.getConnection(clioteName).getStatus().equals(FusionStatus.WAITING)){
							Debug.print("[FusionPlay] Otherup for server: " + clioteName);
							FusionPlay.replaceConnection(clioteName);
						}
						else if(FusionPlay.checkIfServerNeed()){
							Debug.print("[FusionPlay] A server joined? Perfect, we need more!");
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.WAITING);
							cp.sendMessage("fusionplay start new", clioteName);
							for(int i = 0; true; i++){
								if(!FusionPlay.isValidID(i)){
									FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(i);
									FusionPlay.addID(i);
									break;
								}
							}
							FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setCurrentType(args.get(1));
							//FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getID(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + clioteName);
							FusionPlay.dumpToRedis();
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
							Debug.print("[FusionPlay] A server joined? Perfect, we need more!");
							fc.setStatus(FusionStatus.WAITING);
							cp.sendMessage("fusionplay start new", clioteName);
							for(int i = 0; true; i++){
								if(!FusionPlay.isValidID(i)){
									fc.setID(i);
									FusionPlay.addID(i);
									break;
								}
							}
							fc.setCurrentType(args.get(1));
							FusionPlay.addConnection(fc);
							//FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getID(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + clioteName);
							FusionPlay.dumpToRedis();
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
					ServerInfo cur = ProxyServer.getInstance().getServerInfo(FusionPlay.getPairedConFromID(clioteName).getClioteName());
					ServerInfo si = ProxyServer.getInstance().getServerInfo(clioteName);
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(clioteName).getClioteName())).setID(-1);
					cp.sendMessage("fusionplay start", clioteName);
					for(ProxiedPlayer pp : cur.getPlayers()){
						pp.connect(si); //make sure the other server restarts after everyone logs off
					} //if the server just came back from a forced minigame switch
					//FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(FusionPlay.getPairedConFromID(clioteName).getClioteName()).getID(), FusionPlay.getConnection(FusionPlay.getPairedConFromID(clioteName).getClioteName()).getCurrentType() + " " + FusionPlay.getPairedConFromID(clioteName).getClioteName());
					FusionPlay.dumpToRedis();
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
					FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(FusionPlay.getConnection(args.get(1)).getID());
					break;
				case "cachealive"://when checking cached servers
					if(args.size() == 1){
						FusionCon fc = null;
						for(int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++){
							if(FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(clioteName)){
								fc = FusionPlay.cliotesOnCheck.get(i);
								FusionPlay.cliotesOnCheck.remove(i);
							}
						}
						fc.setStatus(FusionStatus.WAITING);
						ServerInfo curs = ProxyServer.getInstance().getServerInfo(FusionPlay.getPairedConFromID(clioteName).getClioteName());
						ServerInfo sis = ProxyServer.getInstance().getServerInfo(fc.getClioteName());
						for(ProxiedPlayer pp : curs.getPlayers()){
							pp.connect(sis);
						}
						FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(clioteName).getClioteName())).setID(-1); //Make sure that the server restart 
					}
					//FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getClioteName(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + FusionPlay.getConnection(clioteName).getID() + " " + FusionPlay.getConnection(clioteName).getStatus().toString());
					FusionPlay.dumpToRedis();
					break;
				case "alive":
					for(int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++){
						if(FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(clioteName)){
							FusionPlay.cliotesOnCheck.remove(i);
						}
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

