package net.estinet.gFeatures.Feature.Friendship.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class FriendsCommand extends EstiCommand{

	public FriendsCommand(gFeature feature) {
		super("friends", "basic", new String[0], feature);
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		ProxyServer.getInstance().getLogger().info("Test!");
	}
	
}
