package net.estinet.gFeatures.Feature.Alerts;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.Title;

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

public class AlertClioteHook extends ClioteHook{

	public AlertClioteHook(gFeature feature) {
		super(feature, "alert");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			String message = "";
			for(String add : args){
				message += add + " ";
			}
			TextComponent tc = new TextComponent(message);
			
			Title bt = ProxyServer.getInstance().createTitle().reset();
			bt.title(new TextComponent(ChatColor.BOLD + "[" + ChatColor.RED + "Alert" + ChatColor.RESET + "" + ChatColor.BOLD + "]"));
			bt.fadeIn(2);
			bt.stay(4);
			bt.fadeOut(2);
			bt.subTitle(tc);
			for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
				p.sendTitle(bt);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
