package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.List;

import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
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

public class FusionPlayClioteHook extends ClioteHook {

    public FusionPlayClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String sender) {

        List<String> args = ClioteSky.parseBytesToStringList(data);

        try {

            if (!sender.equals("Bungee")) {

                switch (args.get(0)) {

                    case "online": //[type] parameter
                        if (FusionPlay.hasConnection(sender)) {

                            if (FusionPlay.getConnection(sender).getStatus().equals(FusionStatus.INGAME) || FusionPlay.getConnection(sender).getStatus().equals(FusionStatus.WAITING)) {

                                Debug.print("[FusionPlay] Otherup for server: " + sender);
                                FusionPlay.replaceConnection(sender);

                            } else if (FusionPlay.checkIfServerNeed()) {

                                Debug.print("[FusionPlay] A server joined? Perfect, we need more!");
                                FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setStatus(FusionStatus.WAITING);

                                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("start new"), "fusionplay", sender);

                                for (int i = 0; true; i++) {
                                    if (!FusionPlay.isValidID(i)) {
                                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setID(i);
                                        FusionPlay.addID(i);
                                        break;
                                    }
                                }

                                FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setCurrentType(args.get(1));
                                //FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getID(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + clioteName);
                                FusionPlay.dumpToRedis();
                                Debug.print("[FusionPlay] Added " + sender + " into ID server pool.");

                            } else {

                                FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setStatus(FusionStatus.NOTASSIGNED);
                                FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setID(-1);
                                FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setCurrentType(args.get(1));
                                FusionPlay.queueConnections.add(FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)));

                                Debug.print("[FusionPlay] Added " + sender + " to waiting queue.");
                            }

                        } else {
                            FusionCon fc = new FusionCon(sender);

                            if (FusionPlay.checkIfServerNeed()) {

                                Debug.print("[FusionPlay] A server joined? Perfect, we need more!");
                                fc.setStatus(FusionStatus.WAITING);
                                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("start new"), "fusionplay", sender);

                                for (int i = 0; true; i++) {
                                    if (!FusionPlay.isValidID(i)) {
                                        fc.setID(i);
                                        FusionPlay.addID(i);
                                        break;
                                    }
                                }

                                fc.setCurrentType(args.get(1));
                                FusionPlay.addConnection(fc);
                                //FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getID(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + clioteName);
                                FusionPlay.dumpToRedis();
                                Debug.print("[FusionPlay] Added " + sender + " into ID server pool.");

                            } else {
                                fc.setID(-1);
                                fc.setCurrentType(args.get(1));
                                fc.setStatus(FusionStatus.NOTASSIGNED);
                                FusionPlay.queueConnections.add(fc);
                                FusionPlay.addConnection(fc);
                                Debug.print("[FusionPlay] Added " + sender + " to waiting queue.");
                            }
                        }
                        break; //Don't make it enable every time, use other if requested

                    case "otherup": //[currentminigametype] sent parameter

                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setStatus(FusionStatus.WAITING);
                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setCurrentType(args.get(1));
                        ServerInfo cur = ProxyServer.getInstance().getServerInfo(FusionPlay.getPairedConFromID(sender).getClioteName());
                        ServerInfo si = ProxyServer.getInstance().getServerInfo(sender);
                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(sender).getClioteName())).setID(-1);

                        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("start"), "fusionplay", sender);

                        for (ProxiedPlayer pp : cur.getPlayers()) {
                            pp.connect(si); //make sure the other server restarts after everyone logs off
                        } //if the server just came back from a forced minigame switch

                        //FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(FusionPlay.getPairedConFromID(clioteName).getClioteName()).getID(), FusionPlay.getConnection(FusionPlay.getPairedConFromID(clioteName).getClioteName()).getCurrentType() + " " + FusionPlay.getPairedConFromID(clioteName).getClioteName());
                        FusionPlay.dumpToRedis();
                        break;

                    case "started": //the server has started the minigame
                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setStatus(FusionStatus.INGAME);
                        break;

                    case "done":
                        FusionPlay.replaceConnection(sender); //when a minigame has finished
                        break;

                    case "otheralive"://when checking restarted cache servers
                        FusionCon fcs = null;
                        for (int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++) {
                            if (FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(sender)) {
                                fcs = FusionPlay.cliotesOnCheck.get(i);
                                FusionPlay.cliotesOnCheck.remove(i);
                            }
                        }

                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setStatus(FusionStatus.OFFLINE);
                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(sender)).setID(FusionPlay.getConnection(args.get(1)).getID());
                        break;

                    case "cachealive"://when checking cached servers

                        if (args.size() == 1) {
                            FusionCon fc = null;

                            for (int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++) {
                                if (FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(sender)) {
                                    fc = FusionPlay.cliotesOnCheck.get(i);
                                    FusionPlay.cliotesOnCheck.remove(i);
                                }
                            }

                            fc.setStatus(FusionStatus.WAITING);
                            ServerInfo curs = ProxyServer.getInstance().getServerInfo(FusionPlay.getPairedConFromID(sender).getClioteName());
                            ServerInfo sis = ProxyServer.getInstance().getServerInfo(fc.getClioteName());

                            for (ProxiedPlayer pp : curs.getPlayers()) {
                                pp.connect(sis);
                            }
                            FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(FusionPlay.getPairedConFromID(sender).getClioteName())).setID(-1); //Make sure that the server restart
                        }
                        //FusionPlay.syncCommands.set("server-" + FusionPlay.getConnection(clioteName).getClioteName(), FusionPlay.getConnection(clioteName).getCurrentType() + " " + FusionPlay.getConnection(clioteName).getID() + " " + FusionPlay.getConnection(clioteName).getStatus().toString());
                        FusionPlay.dumpToRedis();
                        break;

                    case "alive":
                        for (int i = 0; i < FusionPlay.cliotesOnCheck.size(); i++) {
                            if (FusionPlay.cliotesOnCheck.get(i).getClioteName().equals(sender)) {
                                FusionPlay.cliotesOnCheck.remove(i);
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

