package net.estinet.gFeatures.Feature.Alerts;

import java.util.List;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.title.TextTitle;
import com.velocitypowered.api.util.title.Title;
import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;
import net.kyori.text.serializer.ComponentSerializers;

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
            TextComponent title = TextComponent.of("[").decoration(TextDecoration.BOLD, true).
                    append(TextComponent.of("Alert").color(TextColor.RED).resetStyle().
                            append(TextComponent.of("]").decoration(TextDecoration.BOLD, true)));

            TextTitle bt = TextTitle.builder().
                    title(title).
                    subtitle(TextComponent.of(message.toString())).
                    fadeIn(20).
                    stay(40).
                    fadeOut(20).
                    build();

            for (Player p : gFeatures.getInstance().getProxyServer().getAllPlayers()) {
                p.sendTitle(bt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
