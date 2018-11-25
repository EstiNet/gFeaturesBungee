package net.estinet.gFeatures;

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

public class Enabler {
    public static void onEnable() {
        for (gFeature feature : gFeatures.getFeatures()) {
            if (feature.isEnabled()) {
                try {
                    for (Object listener : feature.getEventListeners()) gFeatures.getInstance().getProxyServer().getEventManager().register(gFeatures.getInstance(), listener);
                    feature.enable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
