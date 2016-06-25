package net.estinet.gFeatures;

import net.estinet.gFeatures.API.Resolver.EnableResolver;
import net.estinet.gFeatures.ClioteSky.ClioteInit;
import net.estinet.gFeatures.Configuration.LoadConfig;
import net.estinet.gFeatures.Configuration.SetupConfig;
import net.md_5.bungee.api.plugin.Plugin;

/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2016 EstiNet

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

public class Listeners extends Plugin{
	public static final String version = "3.4.1c";
	public static boolean debug = false;

	Enabler enable = new Enabler();
	Disabler disable = new Disabler();
	Library library = new Library();
	CommandLibrary commands = new CommandLibrary();
	Setup setup = new Setup();
	gLoop gl = new gLoop();
	ClioteInit ccu = new ClioteInit();

	@Override
	public void onEnable(){
		getLogger().info("_________________________________________________________________________");
		getLogger().info("Starting gFeatures.");
		getLogger().info("Current version: " + version);
		getLogger().info("Starting modules!");
		getProxy().getPluginManager().registerListener(this, new Library());
		try{
			setup.onSetup();
			SetupConfig.setup();
			LoadConfig.load();
			Thread thr = new Thread(new Runnable(){
				public void run(){
					ccu.enable();
				}
			});
			thr.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		enable.onEnable();
		gl.start();
		commands.commandEnabler();
		getProxy().getPluginManager().registerCommand(this, new SlashgFeatures());
		getLogger().info("Complete!");
		getLogger().info("_________________________________________________________________________");
	}
	@Override
	public void onDisable(){
		getLogger().info("_________________________________________________________________________");
		getLogger().info("Stopping gFeatures!");
		getLogger().info("Current version: " + version);
		getLogger().info("Turning off modules!");
		disable.onDisable();
		getLogger().info("Complete!");
		getLogger().info("_________________________________________________________________________");
	}
}
