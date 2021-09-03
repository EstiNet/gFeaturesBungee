package net.estinet.gFeatures.Feature.EstiBans.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;

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

public class KickCommand extends EstiCommand {

    public KickCommand(gFeature feature) {
        super(new String[]{"kick"}, "gFeatures.EstiBans.kick", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("/kick [Player] [Reason]")));
        } else {
            if (!gFeatures.getInstance().getProxyServer().getPlayer(args[0]).isPresent()) {
                sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("No one is online with that username.")));
            } else {
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                EstiBans.kickPlayer(args[0], reason.toString());
                sender.sendMessage(EstiBans.estiBansPrefix.append(Component.text("Kicked Player " + args[0] + " for \"" + reason + "\"")));
            }
        }
    }
}
