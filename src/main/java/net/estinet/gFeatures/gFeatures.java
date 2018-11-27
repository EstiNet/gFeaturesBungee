package net.estinet.gFeatures;

import com.google.common.eventbus.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.estinet.gFeatures.API.Resolver.JoinHandler;
import net.estinet.gFeatures.API.Resolver.ResolverInit;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.Configuration.LoadConfig;
import net.estinet.gFeatures.Configuration.SetupConfig;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

@Plugin(id = "gFeatures", name = "gFeatures", version = "4.0.0p", authors = {"EspiDev"})
public class gFeatures {
    public static final String version = "4.0.0p";
    public static boolean debug = false;
    private static gFeatures gfeatures;

    private final ProxyServer server;
    private final Logger logger;

    public static File f = new File("plugins/gFeatures/Config.yml");

    public static List<gFeature> features = new ArrayList<>();
    private static List<EstiCommand> commands = new ArrayList<>();

    @Inject
    public gFeatures(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        logger.info("_________________________________________________________________________");
        logger.info("Starting gFeatures.");
        logger.info("Current version: " + version);
        logger.info("Starting modules!");
        gfeatures = this;
        this.server.getEventManager().register(this, this);
        try {
            Setup.onSetup();
            SetupConfig.setup();
            LoadConfig.load();
            ClioteSky.initClioteSky();
            new Thread(ResolverInit::loadCache).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Enable module listeners

        for (gFeature feature : gFeatures.getFeatures()) {
            if (feature.isEnabled()) {
                try {
                    for (Object listener : feature.getEventListeners())
                        gFeatures.getInstance().getProxyServer().getEventManager().register(gFeatures.getInstance(), listener);
                    feature.enable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Enable module commands

        for (EstiCommand command : gFeatures.getCommands()) {
            if (gFeatures.getFeature(command.feature.getName()).isEnabled()) {
                this.server.getCommandManager().register((source, args) -> {
                    if (command.permission.equals("basic") || source.hasPermission(command.permission)) command.execute(source, args);
                    }, command.names);
            }
        }

        this.server.getCommandManager().register(new SlashgFeatures(), "gfb");
        logger.info("Complete!");
        logger.info("_________________________________________________________________________");
    }

    @Subscribe
    void onServerShutdown(ProxyShutdownEvent event) {
        getLogger().info("_________________________________________________________________________");
        getLogger().info("Stopping gFeatures!");
        getLogger().info("Current version: " + version);
        getLogger().info("Turning off modules!");
        Disabler.onDisable();
        getLogger().info("Complete!");
        getLogger().info("_________________________________________________________________________");
    }

    @Subscribe
    void onPostLogin(PostLoginEvent event) {
        JoinHandler.init(event.getPlayer());
    }

    public ProxyServer getProxyServer() { return server; }

    public Logger getLogger() { return logger; }

    public static gFeatures getInstance() { return gfeatures; }

    public static void addFeature(gFeature feature) {
        features.add(feature);
    }

    public static void addCommand(EstiCommand command) {
        commands.add(command);
    }

    public static gFeature getFeature(String name) {
        for (gFeature feature : features) {
            if (feature.getName().equalsIgnoreCase(name)) {
                return feature;
            }
        }
        return null;
    }

    public static List<gFeature> getFeatures() {
        return features;
    }

    public static List<EstiCommand> getCommands() {
        return commands;
    }

    public static void setFeatures(List<gFeature> pl) {
        features = pl;
    }

    public static void setCommands(List<EstiCommand> ec) {
        commands = ec;
    }

    public static void resetFeatures() {
        features = new ArrayList<>();
    }
}
