package net.estinet.gFeatures;

import net.estinet.gFeatures.API.Resolver.ResolverInit;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.Configuration.LoadConfig;
import net.estinet.gFeatures.Configuration.SetupConfig;
import net.md_5.bungee.api.plugin.Plugin;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2018 EstiNet

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

public class Listeners extends Plugin {
    public static final String version = "3.7.1c";
    public static boolean debug = false;

    Enabler enable = new Enabler();
    Disabler disable = new Disabler();
    CommandLibrary commands = new CommandLibrary();
    Setup setup = new Setup();
    gLoop gl = new gLoop();

    @Override
    public void onEnable() {
        getLogger().info("_________________________________________________________________________");
        getLogger().info("Starting gFeatures.");
        getLogger().info("Current version: " + version);
        getLogger().info("Starting modules!");
        getProxy().getPluginManager().registerListener(this, new Library());
        try {
            setup.onSetup();
            SetupConfig.setup();
            LoadConfig.load();
            ClioteSky.initClioteSky();
            new Thread(ResolverInit::loadCache).start();
        } catch (Exception e) {
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
    public void onDisable() {
        getLogger().info("_________________________________________________________________________");
        getLogger().info("Stopping gFeatures!");
        getLogger().info("Current version: " + version);
        getLogger().info("Turning off modules!");
        disable.onDisable();
        getLogger().info("Complete!");
        getLogger().info("_________________________________________________________________________");
    }
}
