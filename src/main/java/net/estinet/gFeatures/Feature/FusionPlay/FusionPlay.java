package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.velocitypowered.api.proxy.Player;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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

public class FusionPlay extends gFeature {

    private static List<FusionCon> connections = new ArrayList<>();
    public static List<Integer> usedID = new ArrayList<>();
    public static Queue<FusionCon> queueConnections = new LinkedList<FusionCon>();
    public static List<FusionCon> cliotesOnCheck = new ArrayList<>();

    public static RedisClient redisClient = null;
    public static StatefulRedisConnection<String, String> connection = null;
    public static RedisCommands<String, String> syncCommands = null;

    public static String IP = "", port = "", password = "", databaseNum = "";
    public static int maxNumOfGames = 1;

    public FusionPlay(String featurename, String d) {
        super(featurename, d);
    }

    @Override
    public void enable() {
        Enable.onEnable();
    }

    @Override
    public void disable() {
        Disable.onDisable();
    }

    public static List<FusionCon> getConnections() {
        return connections;
    }

    public static void addConnection(FusionCon fc) {
        connections.add(fc);
    }

    public static void removeConnection(FusionCon fc) {
        connections.remove(fc);
    }

    public static void removeConnection(String clioteName) {
        connections.remove(getConnectionArrayID(clioteName));
    }

    public static void addID(int id) {
        if (!usedID.contains(id)) {
            usedID.add(id);
        }
    }

    public static List<FusionCon> getConnectionsFromID(int id) {
        List<FusionCon> list = new ArrayList<>();
        for (FusionCon fc : connections) {
            if (fc.getID() == id) {
                list.add(fc);
            }
        }
        return list;
    }

    public static boolean hasConnection(String clioteName) {
        for (FusionCon fc : connections) {
            if (fc.getClioteName().equals(clioteName)) {
                return true;
            }
        }
        return false;
    }

    public static int getConnectionArrayID(String clioteName) {
        for (int i = 0; i < connections.size(); i++) {
            FusionCon fc = connections.get(i);
            if (fc.getClioteName().equals(clioteName)) {
                return i;
            }
        }
        return -1;
    }

    public static void replaceConnection(String clioteName) {
        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.OFFLINE);
        int id = FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).getID();
        FusionCon fc = queueConnections.poll();

        Runnable run = () -> gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> {
            if (cliotesOnCheck.contains(fc)) {
                cliotesOnCheck.remove(fc);
                connections.get(FusionPlay.getConnectionArrayID(fc.getClioteName())).setStatus(FusionStatus.OFFLINE);
                for (Player pp : gFeatures.getInstance().getProxyServer().getServer(clioteName).get().getPlayersConnected()) {
                    pp.sendMessage(Component.text("Please wait a bit longer, shuffling servers...", NamedTextColor.DARK_GRAY));
                }
                replaceConnection(clioteName);
            }
        }).delay(5, TimeUnit.SECONDS).schedule();

        if (fc == null) {//temporary, until there are servers to take care and dynamic allocation

            for (Player pp : gFeatures.getInstance().getProxyServer().getServer(clioteName).get().getPlayersConnected()) {
                pp.createConnectionRequest(gFeatures.getInstance().getProxyServer().getServer("MinigameHub").get()).fireAndForget();
                pp.sendMessage(Component.text("Sorry! One of our servers went offline, and we can't restore the session!", NamedTextColor.DARK_GRAY));
            }

            FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.OFFLINE);
            //FusionPlay.syncCommands.del("server-" + FusionPlay.getConnection(clioteName).getID());
            FusionPlay.dumpToRedis();
            usedID.remove((Object) FusionPlay.getConnection(clioteName).getID());
            FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(-1);

        } else if (!connections.get(getConnectionArrayID(clioteName)).getCurrentType().equals(fc.getCurrentType())) {

            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("start"), "fusionplay", fc.getClioteName());
            fc.setID(id);
            cliotesOnCheck.add(fc);
            run.run();

        } else {

            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("other " + clioteName), "fusionplay", fc.getClioteName()); //ADD ALIVE CHECK
            cliotesOnCheck.add(fc);
            run.run();

        }
    }

    public static FusionCon getPairedConFromID(FusionCon fc) {
        for (FusionCon fcs : getConnectionsFromID(fc.getID())) {
            if (!fcs.getClioteName().equals(fc.getClioteName())) {
                return fcs;
            }
        }
        return fc;
    }

    public static FusionCon getConnection(String clioteName) {
        for (FusionCon fc : connections) {
            if (fc.getClioteName().equals(clioteName)) {
                return fc;
            }
        }
        return null;
    }

    public static FusionCon getPairedConFromID(String clioteName) {
        FusionCon fc = getConnection(clioteName);
        for (FusionCon fcs : getConnectionsFromID(fc.getID())) {
            if (!fcs.getClioteName().equals(fc.getClioteName())) {
                return fcs;
            }
        }
        return fc;
    }

    public static List<FusionCon> getCurrentAvailableGames() {
        List<FusionCon> cons = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            if (isValidID(i)) {
                for (FusionCon fc : getConnectionsFromID(i)) {
                    if (fc.getStatus().equals(FusionStatus.WAITING)) {
                        cons.add(fc);
                    }
                }
            }
        }
        return cons;
    }

    public static List<FusionCon> getCurrentCachedGames() {
        List<FusionCon> cons = new ArrayList<>();
        for (FusionCon fc : connections) {
            if (fc.getStatus().equals(FusionStatus.NOTASSIGNED)) {
                cons.add(fc);
            }
        }
        return cons;
    }

    public static List<FusionCon> getCurrentOnlineGames() {
        List<FusionCon> cons = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            if (isValidID(i)) {
                for (FusionCon fc : getConnectionsFromID(i)) {
                    if (fc.getStatus().equals(FusionStatus.WAITING) || fc.getStatus().equals(FusionStatus.INGAME)) {
                        cons.add(fc);
                    }
                }
            }
        }
        return cons;
    }

    public static boolean isValidID(int num) {
        for (FusionCon fc : connections) {
            if (fc.getID() == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPairedID(int id) {
        int nums = 0;
        for (FusionCon fc : connections) {
            if (fc.getID() == id) {
                nums++;
            }
        }
        if (nums > 1) {
            return true;
        }
        return false;
    }

    public static boolean checkIfServerNeed() {
        if ((getCurrentOnlineGames().size() <= getCurrentCachedGames().size()) && getCurrentOnlineGames().size() < maxNumOfGames) {
            return true;
        }
        return false;
    }

    public static void dumpToRedis() {
        syncCommands.flushdb();
        for (FusionCon fc : connections) {
            syncCommands.set("server-" + fc.getClioteName(), fc.getCurrentType() + " " + fc.getID() + " " + fc.getStatus().toString());
        }
    }
}
