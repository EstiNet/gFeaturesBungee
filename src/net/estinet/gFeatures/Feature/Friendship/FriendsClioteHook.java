package net.estinet.gFeatures.Feature.Friendship;

import java.io.File;
import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class FriendsClioteHook extends ClioteHook{

	public FriendsClioteHook(gFeature feature) {
		super(feature, "friends");
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			switch(args.get(0)){
			case "request":
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args.get(1));
				File f = new File("plugins/gFeatures/Friendship/" + args.get(2));
				if(f.isDirectory()){
					Friendship.friendRequest(p, args.get(2));
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
