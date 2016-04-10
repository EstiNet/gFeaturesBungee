package net.estinet.gFeatures;

import net.md_5.bungee.api.plugin.Command;

public abstract class EstiCommand extends Command{
	
	public gFeature feature;
	
	public EstiCommand(String name, String permission, String[] aliases, gFeature feature) {
		super(name, permission, aliases);
		this.feature = feature;
	}
}
