package net.estinet.gFeatures.Feature.Alerts;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;
import net.md_5.bungee.BungeeTitle;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
			BungeeTitle bt = new BungeeTitle();
			bt.title(new TextComponent(ChatColor.BOLD + "[" + ChatColor.RED + "Alert" + ChatColor.RESET + "" + ChatColor.BOLD + "]"));
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
