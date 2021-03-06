package net.estinet.gFeatures.Feature.MinigameAssister;

import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.Feature.MinigameAssister.Commands.SlashListgames;
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

public class Configure {
    @Configs
    public static void onSetup() {
        MinigameAssister base = new MinigameAssister("MinigameAssister", "1.0.1");
        gFeatures.addFeature(base);

        ClioteSky.addHook(new MAClioteHook("mgcomplete", base.getName()));
        ClioteSky.addHook(new ConfirmClioteHook("mghello", base.getName()));
        ClioteSky.addHook(new StartClioteHook("mgstart", base.getName()));
        ClioteSky.addHook(new MGGetClioteHook("mgget", base.getName()));
        ClioteSky.addHook(new MGMapClioteHook("mgmap", base.getName()));
        gFeatures.addCommand(new SlashListgames(base));
    }
}
