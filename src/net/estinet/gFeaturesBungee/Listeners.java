package net.estinet.gFeaturesBungee;

import java.io.File;
import java.io.IOException;

import net.estinet.gFeaturesBungee.Commands.*;
import net.estinet.gFeaturesBungee.Commands.EstiMail.SlashMail;
import net.estinet.gFeaturesBungee.XML.Start;
import net.md_5.bungee.api.plugin.Plugin;

public class Listeners extends Plugin{
	public static String version = "1.0.5";
	Start s = new Start();
	File fs = new File("plugins/gFeatures");
	File f = new File("plugins/gFeatures/mail.xml");
	public void onEnable(){
		getLogger().info("_______________________________________________");
		getLogger().info("Enabling gFeaturesBungee version " + version + "...");
		if(!fs.isDirectory()){
			fs.mkdir();
		}
		if(!f.isFile()){
			try {
				f.createNewFile();
				s.start(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		getProxy().getPluginManager().registerCommand(this, new SlashHub());
		getProxy().getPluginManager().registerCommand(this, new SlashFactions());
		getProxy().getPluginManager().registerCommand(this, new SlashgWars());
		getProxy().getPluginManager().registerCommand(this, new SlashSkyblock());
		getProxy().getPluginManager().registerCommand(this, new SlashSurvival());
		getProxy().getPluginManager().registerCommand(this, new SlashCreative());
		getProxy().getPluginManager().registerCommand(this, new SlashMail());
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
