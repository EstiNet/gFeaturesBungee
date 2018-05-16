package net.estinet.gFeatures.Feature.ServerManager;

import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.util.List;

public class ServerManagerClioteHook extends ClioteHook {

    public ServerManagerClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String identifier) {
        List<String> args = ClioteSky.parseBytesToStringList(data);

        if (args.size() > 0) {
            switch (args.get(0)) {
                case "add":
                    //servermanager add [name] [address] [port] [motd]
                    ServerInfo info = ProxyServer.getInstance().constructServerInfo(args.get(1), new InetSocketAddress(args.get(2), Integer.parseInt(args.get(3))), args.get(4), false);
                    ProxyServer.getInstance().getServers().put(info.getName(), info);
                    ProxyServer.getInstance().getLogger().info("Adding server " + args.get(1) + ".");
                    break;
                case "remove":
                    //servermanager remove [name]
                    for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo(args.get(1)).getPlayers()) {
                        p.disconnect("The server is being removed.");
                    }
                    ProxyServer.getInstance().getServers().remove(args.get(1));
                    ProxyServer.getInstance().getLogger().info("Removing server " + args.get(1) + ".");
                    break;
            }
        }
    }
}
