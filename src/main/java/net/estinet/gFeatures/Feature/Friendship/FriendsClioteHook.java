package net.estinet.gFeatures.Feature.Friendship;

import java.io.File;
import java.util.List;

import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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

public class FriendsClioteHook extends ClioteHook {

    public FriendsClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String sender) {

        List<String> args = ClioteSky.parseBytesToStringList(data);

        try {
            switch (args.get(0)) {
                case "request":
                    if (!gFeatures.getInstance().getProxyServer().getPlayer(args.get(1)).isPresent()) return;
                    Player p = gFeatures.getInstance().getProxyServer().getPlayer(args.get(1)).get();
                    File f = new File("plugins/gFeatures/Friendship/" + ResolverFetcher.getUUIDfromName(args.get(2)) + "/");
                    gFeatures.getInstance().getLogger().info(f.getPath());
                    if (f.isDirectory()) {
                        Friendship.friendRequest(p, ResolverFetcher.getUUIDfromName(args.get(2)));
                    } else {
                        p.sendMessage(Friendship.prefix.append(Component.text("Player has never joined, or they don't exist!", NamedTextColor.RED)));
                    }
                    //Player request another player
                    break;
                case "confirm":
                    Friendship.friendConfirm(gFeatures.getInstance().getProxyServer().getPlayer(args.get(1)).get(), args.get(2));
                    //Player confirms friend request
                    break;
                case "list":
                    Friendship.getFriends(gFeatures.getInstance().getProxyServer().getPlayer(args.get(1)).get(), sender);
                    //Gets all friends of player
                    break;
                case "requests":
                    Friendship.getFriendRequests(gFeatures.getInstance().getProxyServer().getPlayer(args.get(1)).get(), sender);
                    //Gets all friend requests of player
                    break;
                case "obtain":
                    Friendship.getStatusDetails(args.get(1), sender);
                    //Get specific details of player
                    break;
                case "unfriend":
                    Friendship.unFriend(gFeatures.getInstance().getProxyServer().getPlayer(args.get(1)).get(), args.get(2));
                    //unfriends a player
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
