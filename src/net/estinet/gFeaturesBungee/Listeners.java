package net.estinet.gFeaturesBungee;

import net.estinet.gFeaturesBungee.Commands.*;
import net.md_5.bungee.api.plugin.Plugin;

public class Listeners extends Plugin{
	public static String version = "1.0.5";
	public void onEnable(){
		getLogger().info("_______________________________________________");
		getLogger().info("Enabling gFeaturesBungee version " + version + "...");
		getProxy().getPluginManager().registerCommand(this, new SlashHub());
		getProxy().getPluginManager().registerCommand(this, new SlashFactions());
		getProxy().getPluginManager().registerCommand(this, new SlashgWars());
		getProxy().getPluginManager().registerCommand(this, new SlashSkyblock());
		getProxy().getPluginManager().registerCommand(this, new SlashSurvival());
		getProxy().getPluginManager().registerCommand(this, new SlashCreative());
		getLogger().info("Enabled!");
		getLogger().info("_______________________________________________");
	}
	public void onDisable(){
		getLogger().info("_______________________________________________");
		getLogger().info("Disabling gFeaturesBungee version " + version + "...");
		getLogger().info("Disabled!");
		getLogger().info("_______________________________________________");
	}
}
