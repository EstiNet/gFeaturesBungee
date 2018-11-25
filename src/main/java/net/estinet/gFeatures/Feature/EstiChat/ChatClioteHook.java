package net.estinet.gFeatures.Feature.EstiChat;

import java.util.List;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;

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

public class ChatClioteHook extends ClioteHook {

    public ChatClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String sender) {
        List<String> args = ClioteSky.parseBytesToStringList(data);
        try {
            String name = args.get(0);
            args.remove(0);
            ProxyServer proxyServer = gFeatures.getInstance().getProxyServer();
            String servername;
            if (!proxyServer.getPlayer(name).isPresent() || !proxyServer.getPlayer(name).get().getCurrentServer().isPresent()) {
                servername = "";
            } else {
                servername = EstiChat.getServerName(proxyServer.getPlayer(name).get().getCurrentServer().get().getServerInfo().getName());
            }
            StringBuilder mgs = new StringBuilder();
            for (String arg : args) {
                mgs.append(arg).append(" ");
            }

            gFeatures.getInstance().getLogger().info(mgs.toString());
            for (Player player : proxyServer.getAllPlayers()) {
                if (player.getCurrentServer().isPresent() && !EstiChat.getServerName(player.getCurrentServer().get().getServerInfo().getName()).equalsIgnoreCase(servername)) {
                    player.sendMessage(TextComponent.of("[" + servername + "] " + mgs));
                    Debug.print("[EstiChat] Sent player " + player.getUsername() + " " + "[" + servername + "] " + mgs);
                }
            }
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(servername + " " + mgs), "consolechat", "all");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
