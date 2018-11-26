package net.estinet.gFeatures.Feature.EstiMail;

import java.io.File;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
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

public class EventHub {
    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> {
            File f = new File("plugins/gFeatures/EstiMail/" + event.getPlayer().getUniqueId().toString());
            if (!f.isDirectory()) {
                f.mkdir();
            }
            EstiMail.getMail(event.getPlayer());
        }).schedule();
    }
}
