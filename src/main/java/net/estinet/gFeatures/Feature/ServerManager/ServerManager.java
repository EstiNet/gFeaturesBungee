package net.estinet.gFeatures.Feature.ServerManager;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Event;

public class ServerManager extends gFeature implements Events {

    public ServerManager(String featurename, String d) {
        super(featurename, d);
    }

    @Override
    public void enable() {
        ProxyServer.getInstance().getLogger().info("[ServerManager] Enabled!");
    }

    @Override
    public void disable() {
        ProxyServer.getInstance().getLogger().info("[ServerManager] Disabled!");
    }

    @Override
    public void eventTrigger(Event event) {
        if (event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("serverconnectevent")) {
            ServerConnectEvent e = (ServerConnectEvent) event;
            e.getTarget().ping((result, error) -> {
                if (error != null) {
                    //Means that server is not responding : OFFLINE

                }

            });
        }
    }

    @Retrieval
    @Override
    public void onServerConnect() {
    }
}
