package net.estinet.gFeatures.Feature.EstiBans.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.EstiBans.EstiBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class EstiBansCommand extends EstiCommand{

	public EstiBansCommand(gFeature feature) {
		super("estibans", "gFeatures.admin", new String[0], feature);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("help")){
				sender.sendMessage(ChatColor.AQUA + "----- EstiBans -----");
				sender.sendMessage(ChatColor.AQUA + "/estibans info [Player] - Obtains player information.");
				sender.sendMessage(ChatColor.AQUA + "/estibans reload - Reloads from the config.");
			}
			else if(args[0].equalsIgnoreCase("reload")){
				EstiBans.bans = new HashMap<>();
				EstiBans.mutes = new HashMap<>();
				EstiBans.warnings = new HashMap<>();
				
				try(Stream<Path> paths = Files.walk(Paths.get("plugins/gFeatures/EstiBans/playerdata"))) {
				    paths.forEach(filePath -> {
				        if (Files.isRegularFile(filePath)) {
				        	File f = new File(filePath.toString());
				        	List<String> list = new ArrayList<>();
				    		try {
				    			FileReader fr = new FileReader(f);
				    			BufferedReader br = new BufferedReader(fr);
				    			String str = "";
				    			while((str = br.readLine()) != null) {
				    				list.add(str);
				    			}   
				    			br.close(); 
				    		} catch (IOException e){
				    			e.printStackTrace();
				    		}
				    		if(f.getName().contains("-bans")){
				    			EstiBans.bans.put(UUID.fromString(f.getName().replace("-bans", "")), list);
				    		}
				    		else if(f.getName().contains("-mutes")){
				    			EstiBans.mutes.put(UUID.fromString(f.getName().replace("-mutes", "")), list);
				    		}
				    		else if(f.getName().contains("-warnings")){
				    			EstiBans.warnings.put(UUID.fromString(f.getName().replace("-warnings", "")), list);
				    		}
				        }
				    });
				} catch (IOException e) {
					e.printStackTrace();
				} 
				sender.sendMessage(EstiBans.estiBansPrefix + "Completed reload.");
			}
			else{
				sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
			}
		}
		else if(args.length == 1){
			if(args[0].equalsIgnoreCase("info")){
				sender.sendMessage(new TextComponent(EstiBans.estiBansPrefix + "Player info for " + args[1]));
				sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Bans:"));
				for(String str : EstiBans.getBans(args[0])){
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
				}
				sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Mutes"));
				for(String str : EstiBans.getMutes(args[0])){
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
				}
				sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Warnings"));
				for(String str : EstiBans.getWarnings(args[0])){
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "- " + str));
				}
			}
			else{
				sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
			}
		}
		else{
			sender.sendMessage(EstiBans.estiBansPrefix + "/estibans help");
		}
	}	
}
