package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.concurrent.TimeUnit;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;

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

public class Enable {
    static ConfigHub ch = new ConfigHub();

    public static void onEnable() {
        gFeatures.getInstance().getLogger().info("[FusionPlay] Enabled!");
        ch.setupConfig();
        gFeatures.getInstance().getLogger().info("[FusionPlay] Connecting to Redis...");

        RedisURI ruri = new RedisURI();
        ruri.setDatabase(Integer.parseInt(FusionPlay.databaseNum));
        ruri.setPort(Integer.parseInt(FusionPlay.port));
        ruri.setPassword(FusionPlay.password);
        ruri.setHost(FusionPlay.IP);

        FusionPlay.redisClient = RedisClient.create(ruri);
        FusionPlay.connection = FusionPlay.redisClient.connect();
        FusionPlay.syncCommands = FusionPlay.connection.sync();

        gFeatures.getInstance().getLogger().info("[FusionPlay] Connected!");

        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("obtain"), "fusionplay", "all");
        FusionPlay.syncCommands.flushdb();

        gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> {

            for (FusionCon fc : FusionPlay.getCurrentOnlineGames()) {
                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("alive"), "fusionplay", fc.getClioteName());
                FusionPlay.cliotesOnCheck.add(fc);

                gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> {

                    if (FusionPlay.cliotesOnCheck.contains(fc)) {
                        FusionPlay.cliotesOnCheck.remove(fc);
                        FusionPlay.usedID.remove((Object) fc.getID());
                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(fc.getClioteName())).setID(-1);
                        FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(fc.getClioteName())).setStatus(FusionStatus.OFFLINE);
                        //FusionPlay.syncCommands.del("server-" + fc.getID());
                        FusionPlay.dumpToRedis();
                        FusionPlay.replaceConnection(fc.getClioteName());
                    }

                }).delay(5, TimeUnit.SECONDS).schedule();

            }
        }).repeat(10, TimeUnit.SECONDS).schedule(); // run 10 times

    }
}
