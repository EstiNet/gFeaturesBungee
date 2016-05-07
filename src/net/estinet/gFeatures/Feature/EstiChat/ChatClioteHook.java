package net.estinet.gFeatures.Feature.EstiChat;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChatClioteHook extends ClioteHook{

	public ChatClioteHook(gFeature feature) {
		super(feature, "chat");
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			String mgs = "";
			for(String arg : args){
				mgs += arg + " ";
			}
			for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
				if(!player.getServer().getInfo().getName().equalsIgnoreCase(clioteName)){
					player.sendMessage("[" + clioteName + "] " + mgs);
				}
			}
			CliotePing cp = new CliotePing();
			cp.sendMessage("consolechat " + clioteName + " " + mgs, "all");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
