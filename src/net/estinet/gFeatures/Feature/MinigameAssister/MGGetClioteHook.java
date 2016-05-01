package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class MGGetClioteHook extends ClioteHook{

	public MGGetClioteHook(gFeature feature) {
		super(feature, "mgget");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			CliotePing cp = new CliotePing();
			cp.sendMessage("mgstart", "MinigameHubs");
			for(String mgs : MinigameAssister.servers.keySet()){
				ServerInfo target = ProxyServer.getInstance().getServerInfo(mgs);
				cp.sendMessage("mgrecieve " + mgs + " " + MinigameAssister.servers.get(mgs) + " " + target.getPlayers().size() + " " + MinigameAssister.maps.get(mgs), clioteName);
			} //MGS.getName() should be the name of the Cliote as well as server...
			cp.sendMessage("mgdone", clioteName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
