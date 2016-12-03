package net.estinet.gFeaturesBungee;

import java.io.File;
import java.io.IOException;

import net.estinet.gFeaturesBungee.Commands.*;
import net.estinet.gFeaturesBungee.Commands.EstiMail.SlashMail;
import net.estinet.gFeaturesBungee.Events.Join;
import net.estinet.gFeaturesBungee.Events.Switch;
import net.estinet.gFeaturesBungee.XML.Start;
import net.md_5.bungee.api.plugin.Plugin;

public class Listeners extends Plugin{
	public static String version = "1.0.7";
	Start s = new Start();
	File fs = new File("plugins/gFeatures");
	File f = new File("plugins/gFeatures/mail.xml");
	public void onEnable(){
		getLogger().info("_______________________________________________");
		getLogger().info("Enabling gFeaturesBungee versions " + version + "...");
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
		/*getProxy().getPluginManager().registerCommand(this, new SlashHub());
		getProxy().getPluginManager().registerCommand(this, new SlashFactions());
		getProxy().getPluginManager().registerCommand(this, new SlashgWars());
		getProxy().getPluginManager().registerCommand(this, new SlashSkyblock());
		getProxy().getPluginManager().registerCommand(this, new SlashSurvival());
		getProxy().getPluginManager().registerCommand(this, new SlashCreative());*/
		//Disable /mail for now :(
		//getProxy().getPluginManager().registerCommand(this, new SlashMail());
		//getProxy().getPluginManager().registerListener(this, new Join());
		getProxy().getPluginManager().registerListener(this, new Switch());
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
