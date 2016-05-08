package net.estinet.gFeatures.Feature.EstiChat;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
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
			String name = args.get(0);
			args.remove(0);
			String servername = ProxyServer.getInstance().getPlayer(name).getServer().getInfo().getName();
			String mgs = "";
			for(String arg : args){
				mgs += arg + " ";
			}
			
			ProxyServer.getInstance().getLogger().info(mgs);
			for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
				if(!player.getServer().getInfo().getName().equalsIgnoreCase(servername)){
					player.sendMessage("[" + servername + "] " + mgs);
					Debug.print("[EstiChat] Sent player " + player.getName() + " " + "[" + servername + "] " + mgs);
				}
			}
			CliotePing cp = new CliotePing();
			cp.sendMessage("consolechat " + servername + " " + mgs, "all");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
