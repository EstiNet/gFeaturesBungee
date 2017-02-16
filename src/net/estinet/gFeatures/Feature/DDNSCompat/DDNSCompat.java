package net.estinet.gFeatures.Feature.DDNSCompat;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ProxyConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Event;

public class DDNSCompat extends gFeature implements Events {

    public DDNSCompat(String featurename, String d) {
        super(featurename, d);
    }
    @Override
    public void enable(){

    }
    @Override
    public void disable(){

    }
    @Override
    public void eventTrigger(Event event) {}

    public static void refresh(){
        for(ListenerInfo li : ProxyServer.getInstance().getConfig().getListeners()){
            li.getHost();
        }
    }
}
