package net.estinet.gFeatures.Feature.ServerRedirect;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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

public class SRClioteHook extends ClioteHook{

	public SRClioteHook(gFeature feature) {
		super(feature, "redirect");
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
