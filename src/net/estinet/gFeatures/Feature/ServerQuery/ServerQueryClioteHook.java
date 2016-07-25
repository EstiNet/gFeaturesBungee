package net.estinet.gFeatures.Feature.ServerQuery;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;

public class ServerQueryClioteHook extends ClioteHook{

	public ServerQueryClioteHook(gFeature feature) {
		super(feature, "info");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			CliotePing cp = new CliotePing();
			switch(args.get(0)){
			case "online":
				cp.sendMessage("info online " + ProxyServer.getInstance().getOnlineCount(), clioteName);
				break;
			case "serverget":
				cp.sendMessage("info serverget " + args.get(1) + " " + ProxyServer.getInstance().getPlayer(args.get(2)).getServer().getInfo().getName(), clioteName);
				break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
