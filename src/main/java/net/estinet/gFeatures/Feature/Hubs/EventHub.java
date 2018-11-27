package net.estinet.gFeatures.Feature.Hubs;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

public class EventHub {
    private static HashMap<UUID, Boolean> hash = new HashMap<>();

    @Subscribe
    public static void onPlayerLogin(PostLoginEvent event) {
        String host = event.getPlayer().getVirtualHost().get().getHostString();
        if (host.contains("survival.estinet.net")) {
            hash.put(event.getPlayer().getUniqueId(), true);
            gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> {
                hash.remove(event.getPlayer().getUniqueId());
            }).delay(1, TimeUnit.MINUTES).schedule();
        }
    }

    @Subscribe
    public static void onServerConnect(ServerConnectedEvent event) {
        if (hash.get(event.getPlayer().getUniqueId()) != null && hash.get(event.getPlayer().getUniqueId())) {
            hash.remove(event.getPlayer().getUniqueId());
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(event.getPlayer().getUsername()), "survivalmenu", "Hubs");
        }
    }
}
