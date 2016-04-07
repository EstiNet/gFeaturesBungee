package net.estinet.gFeatures.Feature.Base.Commands;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class TestCommand extends EstiCommand{

	public TestCommand(gFeature feature) {
		super("test", "gFeatures.test", new String[0], feature);
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		ProxyServer.getInstance().getLogger().info("Test!");
	}
	
}
