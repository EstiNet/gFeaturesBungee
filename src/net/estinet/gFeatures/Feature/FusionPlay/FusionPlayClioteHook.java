package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;

public class FusionPlayClioteHook extends ClioteHook{

	public FusionPlayClioteHook(gFeature feature) {
		super(feature, "fusionplay");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			if(!clioteName.equals("Bungee")){
				CliotePing cp = new CliotePing();
				switch(args.get(0)){
				case "online":
					if(FusionPlay.hasConnection(clioteName)){
						FusionPlay.connections.get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(true);;
					}
					break;
				case "serverget":
					cp.sendMessage("info serverget " + args.get(1) + " " + ProxyServer.getInstance().getPlayer(args.get(2)).getServer().getInfo().getName(), clioteName);
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

