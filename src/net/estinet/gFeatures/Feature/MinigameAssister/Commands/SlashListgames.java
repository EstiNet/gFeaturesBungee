package net.estinet.gFeatures.Feature.MinigameAssister.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.MinigameAssister.MGServer;
import net.estinet.gFeatures.Feature.MinigameAssister.MinigameAssister;
import net.md_5.bungee.api.CommandSender;

public class SlashListgames extends EstiCommand{
	public SlashListgames(gFeature feature){
		super("listgames", "basic", new String[0], feature);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("Servers:");
		for(String mgs : MinigameAssister.servers.keySet()){
			sender.sendMessage(mgs + " " + MinigameAssister.servers.get(mgs));
		}
	}
}
