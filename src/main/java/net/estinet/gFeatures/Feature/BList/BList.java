package net.estinet.gFeatures.Feature.BList;

import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

public class BList extends gFeature {

    public static HashMap<String, ArrayList<String>> fakePlayers = new HashMap<>();

    BList(String featurename, String d) {
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

    public static HashMap<String, ArrayList<String>> getPlayersWithServer() {
        HashMap<String, ArrayList<String>> servers = new HashMap<>();
        for (Player pp : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
            try {
                if (!pp.getCurrentServer().isPresent()) continue;
                String sName = pp.getCurrentServer().get().getServerInfo().getName();
                if (servers.get(sName) == null) {
                    servers.put(sName, new ArrayList<>(Arrays.asList(pp.getUsername())));
                } else {
                    servers.get(sName).add(pp.getUsername());
                }
            } catch (NullPointerException e) { //line 58
            }
        }
        for (String server : BList.fakePlayers.keySet()) {
            if (!BList.fakePlayers.get(server).isEmpty()) servers.put(server, BList.fakePlayers.get(server));
        }
        return servers;
    }
}
