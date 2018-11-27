package net.estinet.gFeatures.Feature.MinigameAssister;

import com.velocitypowered.api.proxy.server.RegisteredServer;
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

public class SendAll {
    public void sendAllInfo() {
        ClioteSky.getInstance().sendAsync(new byte[0], "mgstart", "MinigameHubs");

        for (String mgs : MinigameAssister.servers.keySet()) {
            RegisteredServer target = gFeatures.getInstance().getProxyServer().getServer(mgs).get();
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(mgs + " " + MinigameAssister.servers.get(mgs) + " " + target.getPlayersConnected().size() + " " + MinigameAssister.maps.get(mgs)), "mgrecieve", "MinigameHubs");
        }
        ClioteSky.getInstance().sendAsync(new byte[0], "mgdone", "MinigameHubs");
    }
}
