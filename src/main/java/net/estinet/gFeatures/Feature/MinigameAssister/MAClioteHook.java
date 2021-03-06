package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.ClioteHook;

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

public class MAClioteHook extends ClioteHook {

    public MAClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String clioteName) {
        try {
            Debug.print("Comparing " + clioteName);
            if (!MinigameAssister.servers.containsKey(clioteName)) {
                MinigameAssister.servers.put(clioteName, MGState.OFFLINE);
            } else {
                Debug.print("Was found.");
                MinigameAssister.servers.replace(clioteName, MGState.OFFLINE);
            }
            SendAll sa = new SendAll();
            sa.sendAllInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
