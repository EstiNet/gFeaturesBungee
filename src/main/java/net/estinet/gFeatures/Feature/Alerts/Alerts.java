package net.estinet.gFeatures.Feature.Alerts;

import net.estinet.gFeatures.gFeature;
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

public class Alerts extends gFeature {

    public Alerts(String featurename, String d) {
        super(featurename, d);
    }

    @Override
    public void enable() {
        gFeatures.getInstance().getLogger().info("[Alerts] enabled!");
    }

    @Override
    public void disable() {
        gFeatures.getInstance().getLogger().info("[Alerts] Disabled!");
    }
}
