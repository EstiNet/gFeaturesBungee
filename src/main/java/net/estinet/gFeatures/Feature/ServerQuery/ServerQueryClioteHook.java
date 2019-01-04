package net.estinet.gFeatures.Feature.ServerQuery;

import java.util.List;

import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.ClioteSky.ClioteHook;
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

public class ServerQueryClioteHook extends ClioteHook {

    public ServerQueryClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String clioteName) {
        List<String> args = ClioteSky.parseBytesToStringList(data);
        try {
            if (!clioteName.contains("Bungee")) {
                switch (args.get(0)) {
                    case "online":
                        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("online " + gFeatures.getInstance().getProxyServer().getAllPlayers().size()), "info", clioteName);
                        break;
                    case "serverget": // get which server the player is on
                        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("serverget " + args.get(1) + " " + gFeatures.getInstance().getProxyServer().getPlayer(args.get(2)).get().getCurrentServer().get().getServerInfo().getName()), "info", clioteName);
                        break;
                    case "uuidlookup":
                        String uuid = ResolverFetcher.getUUIDfromName(args.get(1));
                        if (uuid == null) {
                            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("uuidlookup *"), "info", clioteName);
                        } else {
                            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("uuidlookup " + uuid), "info", clioteName);
                        }
                        break;
                    case "playerlist": // returns player list separated by §
                        StringBuilder prep = new StringBuilder();
                        for (Player p : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
                            prep.append(p.getUsername()).append("§");
                        }
                        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("playerlist " + prep.substring(0, prep.length()-1)), "info", clioteName);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
