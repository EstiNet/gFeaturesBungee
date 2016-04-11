package net.estinet.gFeatures.Feature.Alerts;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AlertClioteHook extends ClioteHook{

	public AlertClioteHook(gFeature feature) {
		super(feature, "alert");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args.get(0));
			ServerInfo target = ProxyServer.getInstance().getServerInfo(args.get(1));
			player.connect(target);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
