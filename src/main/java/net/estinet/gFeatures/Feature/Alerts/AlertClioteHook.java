package net.estinet.gFeatures.Feature.Alerts;

import java.util.List;

import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.Title;

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
            TextComponent tc = new TextComponent(message.toString());

            Title bt = ProxyServer.getInstance().createTitle().reset();
            bt.title(new TextComponent(ChatColor.BOLD + "[" + ChatColor.RED + "Alert" + ChatColor.RESET + "" + ChatColor.BOLD + "]"));
            bt.fadeIn(20);
            bt.stay(40);
            bt.fadeOut(20);
            bt.subTitle(tc);
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                p.sendTitle(bt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
