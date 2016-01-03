package net.estinet.gFeaturesBungee.Commands.EstiMail;

import net.estinet.gFeaturesBungee.EstiMail.Gloze;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SlashMail extends Command{
	public SlashMail(){
        super("mail", "basic", new String[0]);
 }

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("read")){
					//Add soon
				}
				else if(args[0].equalsIgnoreCase("send")){
					sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] /mail send [Player] [Message].");
				}
				else if(args[0].equalsIgnoreCase("clear")){
					
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
					if(!Gloze.check(args[1])){
						sender.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "]" + ChatColor.RED + " Player not found.");
					}
					else{
						String message = "";
						for(int i = 2; i == args.length; i++){
							message += args[i];
						}
						Gloze.send(sender, args[1], message);
					}
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
