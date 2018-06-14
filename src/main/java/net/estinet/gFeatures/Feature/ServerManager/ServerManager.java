package net.estinet.gFeatures.Feature.ServerManager;

import net.estinet.gFeatures.API.Logger.Debug;
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
import java.util.concurrent.TimeUnit;

public class ServerManager extends gFeature implements Events {

    public ServerManager(String featurename, String d) {
        super(featurename, d);
    }

    public static HashMap<String, UnresolvedAddress> domains = new HashMap<>();

    private final File file = new File("config.yml");
    private Map<String, Object> config;

    @Override
    public void enable() {
        ProxyServer.getInstance().getLogger().info("[ServerManager] Enabled!");

        // Read BungeeCord config and add all of the servers to the domains map.

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

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

        // Start scheduler to check for offline servers

        ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> {
            for (ServerInfo si : ProxyServer.getInstance().getServers().values()) {
                ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> resolveServer(si));
            }
        }, 5, TimeUnit.MINUTES);
    }

    @Override
    public void disable() {
        ProxyServer.getInstance().getLogger().info("[ServerManager] Disabled!");
    }

    @Override
    public void eventTrigger(Event event) {
        if (event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("serverconnectevent")) {
            ServerConnectEvent e = (ServerConnectEvent) event;
            resolveServer(e.getTarget());
        }
    }

    @Retrieval
    @Override
    public void onServerConnect() {
    }

    /*
     * Since some of our hosts use DDNS (dynamic ip), we need to re-resolve the domain
     * in BungeeCord when the ip changes, since Bungee doesn't do that itself.
     */

    private void resolveServer(ServerInfo serverInfo) {
        serverInfo.ping((result, error) -> {
            if (error != null) {
                //Means that server is not responding : OFFLINE

                Debug.print("[ServerManager] Attempting to re-resolve offline server " + serverInfo.getName() + "...");

                Debug.print("[ServerManager] Resolving old host " + serverInfo.getAddress().getAddress() + ":" + serverInfo.getAddress().getPort());

                String oldAddress = serverInfo.getAddress().getAddress().toString();
                InetSocketAddress newAddress = new InetSocketAddress(domains.get(serverInfo.getName()).address, Integer.parseInt(domains.get(serverInfo.getName()).port));
                ServerInfo newsi = ProxyServer.getInstance().constructServerInfo(serverInfo.getName(), newAddress, serverInfo.getMotd(), false);

                Debug.print("[ServerManager] New host is " + newAddress.getAddress() + ":" + newAddress.getPort());

                ProxyServer.getInstance().getServers().remove(serverInfo.getName());
                ProxyServer.getInstance().getServers().put(newsi.getName(), newsi);

                if(!oldAddress.equals(newAddress.toString())) ProxyServer.getInstance().getLogger().info("[ServerManager] Re-resolved server " + serverInfo.getName() + ".");
            }
        });
    }

    /*
     * Helper method (totally not from BungeeCord code)
     * for getting values in the config.yml file.
     */

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
