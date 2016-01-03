package net.estinet.gFeaturesBungee.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SlashgWars extends Command{
	 public SlashgWars(){
	        super("gwars", "basic", new String[0]);
	 }

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
      ProxiedPlayer player = (ProxiedPlayer) sender;
      	ServerInfo target = ProxyServer.getInstance().getServerInfo("gWars");
      	player.connect(target);
		}
		else{
			sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.AQUA).create());
		}
	}
}