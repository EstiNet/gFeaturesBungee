package net.estinet.gFeatures.Feature.Alerts;

import java.time.Duration;
import java.util.List;

import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;

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

public class AlertClioteHook extends ClioteHook {

    public AlertClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String identifier) {

        List<String> args = ClioteSky.parseBytesToStringList(data);

        try {
            StringBuilder message = new StringBuilder();
            for (String add : args) {
                message.append(add).append(" ");
            }
            Component title = LegacyComponentSerializer.legacyAmpersand().deserialize("&f&l[&cAlert&f&l]");

            final Title.Times times = Title.Times.of(Duration.ofMillis(1000), Duration.ofMillis(2000), Duration.ofMillis(1000));
            final Title bt = Title.title(title, Component.text(message.toString()), times);

            for (Player p : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
                p.showTitle(bt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
