package net.estinet.gFeatures.Feature.ServerQuery;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ProxyServer;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2017 EstiNet

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

public class ServerQueryClioteHook extends ClioteHook{

	public ServerQueryClioteHook(gFeature feature) {
		super(feature, "info");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			if(!clioteName.equals("Bungee")){
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
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
