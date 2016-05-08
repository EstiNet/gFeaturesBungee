package net.estinet.gFeatures.Feature.EstiMail.Commands;

import java.util.Arrays;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.MojangAPI.UUIDFetcher;
import net.estinet.gFeatures.Feature.EstiMail.EstiMail;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SlashMail extends EstiCommand{
	public SlashMail(gFeature feature){
		super("mail", "basic", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "---EstiMail Help---");
					sender.sendMessage(ChatColor.GOLD + "/mail read : Displays your inbox.");
					sender.sendMessage(ChatColor.GOLD + "/mail send [Player] [Message] : Sends a player an email.");
					sender.sendMessage(ChatColor.GOLD + "/mail clear : Clears your inbox.");
				}
				else if(args[0].equalsIgnoreCase("read")){
					EstiMail.getMail(player);
				}
				else if(args[0].equalsIgnoreCase("send")){
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] /mail send [Player] [Message].");
				}
				else if(args[0].equalsIgnoreCase("clear")){
					EstiMail.clearMail(player);
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.DARK_AQUA + "Mail cleared!");
				}
				else{
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] Do /mail help.");
				}
			}
			else if(args.length == 2){
				if(args[0].equalsIgnoreCase("send")){
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] /mail send [Player] [Message].");
				}
				else{
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] Do /mail help.");
				}
			}
			else if(args.length >= 3){
				if(args[0].equalsIgnoreCase("send")){
					BungeeCord.getInstance().getPluginManager().getPlugin("gFeatures").getProxy().getScheduler().runAsync(BungeeCord.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
						public void run(){
							UUIDFetcher uid = new UUIDFetcher(Arrays.asList(args[1]));
							String uuid = "";
							try {
								uuid = uid.call().get(args[1]).toString();
							} catch (Exception e) {
								e.printStackTrace();
							}
							if(!EstiMail.checkExists(uuid)){
								sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "]" + ChatColor.RED + " Player not found.");
							}
							else{
								String message = "";
								for(int i = 2; i != args.length; i++){
									message += args[i];
								}
								EstiMail.sendMail(player.getName(), uuid, message);
								sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "]" + ChatColor.DARK_AQUA + " Mail sent!");
							}
						}});
				}
				else{
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] Do /mail help.");
				}
			}
			else{
				sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] Do /mail help.");
			}
		}
		else{
			sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.AQUA).create());
		}
	}
}
