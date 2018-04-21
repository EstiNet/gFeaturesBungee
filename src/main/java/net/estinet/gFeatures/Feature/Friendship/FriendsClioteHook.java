package net.estinet.gFeatures.Feature.Friendship;

import java.io.File;
import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.ClioteSkyOld.API.ClioteHook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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

public class FriendsClioteHook extends ClioteHook{

	public FriendsClioteHook(gFeature feature) {
		super(feature, "friends");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			switch(args.get(0)){
			case "request":
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args.get(1));
				File f = new File("plugins/gFeatures/Friendship/" + ResolverFetcher.getUUIDfromName(args.get(2)) + "/");
				ProxyServer.getInstance().getLogger().info(f.getPath());
				if(f.isDirectory()){
					Friendship.friendRequest(p, ResolverFetcher.getUUIDfromName(args.get(2)));
				}
				else{
					p.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + ChatColor.RED + "Player has never joined, or they don't exist!");
				}
				//Player request another player
				break;
			case "confirm":
				Friendship.friendConfirm(ProxyServer.getInstance().getPlayer(args.get(1)), args.get(2));
				//Player confirms friend request
				break;
			case "list":
				Friendship.getFriends(ProxyServer.getInstance().getPlayer(args.get(1)), clioteName);
				//Gets all friends of player
				break;
			case "requests":
				Friendship.getFriendRequests(ProxyServer.getInstance().getPlayer(args.get(1)), clioteName);
				//Gets all friend requests of player
				break;
			case "obtain":
				Friendship.getStatusDetails(args.get(1), clioteName);
				//Get specific details of player
				break;
			case "unfriend":
				Friendship.unFriend(ProxyServer.getInstance().getPlayer(args.get(1)), args.get(2));
				//unfriends a player
				break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
