package net.estinet.gFeatures.Feature.ServerManager;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Event;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServerManager extends gFeature implements Events {

    public ServerManager(String featurename, String d) {
        super(featurename, d);
    }

    public static HashMap<String, UnresolvedAddress> domains = new HashMap<>();
    private final File file = new File("config.yml");
    private Map<String, Object> config;
    private Yaml yaml;

    @Override
    public void enable() {
        ProxyServer.getInstance().getLogger().info("[ServerManager] Enabled!");

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);

        try {
            config = yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, Map<String, Object>> base = get("servers", config);

        for (Map.Entry<String, Map<String, Object>> entry : base.entrySet()) {
            Map<String, Object> val = entry.getValue();
            String name = entry.getKey();
            String addr = get("address", val);
            domains.put(name, new UnresolvedAddress(addr.split(":")[0], addr.split(":")[1]));
            ProxyServer.getInstance().getLogger().info("[ServerManager] Found server " + name + ".");
        }
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
                    ServerInfo si = e.getTarget();
                    ProxyServer.getInstance().getLogger().info("[ServerManager] Attempting to re-resolve offline server " + si.getName() + "...");
                    ServerInfo newsi = ProxyServer.getInstance().constructServerInfo(si.getName(), new InetSocketAddress(domains.get(si.getName()).address, Integer.parseInt(domains.get(si.getName()).port)), si.getMotd(), false);
                    ProxyServer.getInstance().getServers().remove(si.getName());
                    ProxyServer.getInstance().getServers().put(newsi.getName(), newsi);
                    ProxyServer.getInstance().getLogger().info("[ServerManager] Re-resolved.");
                }
            });
        }
    }

    @Retrieval
    @Override
    public void onServerConnect() {
    }

    @SuppressWarnings("unchecked")
    private <T> T get(String path, Map submap) {
        int index = path.indexOf('.');
        if (index == -1) {
            Object val = submap.get(path);
            return (T) val;
        } else {
            String first = path.substring(0, index);
            String second = path.substring(index + 1, path.length());
            Map sub = (Map) submap.get(first);
            if (sub == null) {
                sub = new LinkedHashMap();
                submap.put(first, sub);
            }
            return get(second, sub);
        }
    }

}
