package net.estinet.gFeatures.Feature.SlashServer;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

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

public class SlashServer extends gFeature {

    public SlashServer(String featurename, String d) {
        super(featurename, d);
    }

    @Override
    public void enable() {
        Enable.onEnable();
    }

    @Override
    public void disable() {
        Disable.onDisable();
    }

    public static void connectToServer(CommandSource sender, String server) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            RegisteredServer target = gFeatures.getInstance().getProxyServer().getServer(server).get();
            player.createConnectionRequest(target).fireAndForget();
        } else {
            sender.sendMessage(TextComponent.of("This command can only be run by a player!", TextColor.AQUA));
        }
    }
}
