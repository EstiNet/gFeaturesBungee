package net.estinet.gFeatures.Feature.SlashServer.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SlashHub extends EstiCommand{
	public SlashHub(gFeature feature){
		super("hub", "basic", new String[0], feature);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(player.getServer().getInfo().getName().equals("Factions") || player.getServer().getInfo().getName().equals("Survival") || player.getServer().getInfo().getName().equals("Skyblock") || player.getServer().getInfo().getName().equals("Creative") || player.getServer().getInfo().getName().equals("gWars")  || player.getServer().getInfo().getName().equals("Hub") || player.getServer().getInfo().getName().equals("Development")  ){
				ServerInfo target = ProxyServer.getInstance().getServerInfo("Hub");
				player.connect(target);
			}
			else{
				ServerInfo target = ProxyServer.getInstance().getServerInfo("MinigameHub");
				player.connect(target);
			}
		}
		else{
			sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.AQUA).create());
		}
	}
}