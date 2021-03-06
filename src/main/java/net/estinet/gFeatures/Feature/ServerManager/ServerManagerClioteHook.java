package net.estinet.gFeatures.Feature.ServerManager;

import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import java.util.List;

public class ServerManagerClioteHook extends ClioteHook {

    public ServerManagerClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String identifier) {
        List<String> args = ClioteSky.parseBytesToStringList(data);

        // ServerManager ClioteSky functions

        if (args.size() > 0) {
            switch (args.get(0)) {/* TODO REDO FOR VELOCITY
                case "add":
                    //servermanager add [name] [address] [port] [motd]
                    ServerInfo info = ProxyServer.getInstance().constructServerInfo(args.get(1), new InetSocketAddress(args.get(2), Integer.parseInt(args.get(3))), args.get(4), false);
                    ProxyServer.getInstance().getServers().put(info.getName(), info);
                    ProxyServer.getInstance().getLogger().info("Adding server " + args.get(1) + ".");
                    ServerManager.domains.put(args.get(1), new UnresolvedAddress(args.get(2), args.get(3)));
                    break;
                case "remove":
                    //servermanager remove [name]
                    for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo(args.get(1)).getPlayers()) {
                        p.disconnect("The server is being removed.");
                    }
                    ProxyServer.getInstance().getServers().remove(args.get(1));
                    ProxyServer.getInstance().getLogger().info("Removing server " + args.get(1) + ".");
                    ServerManager.domains.remove(args.get(1));
                    break;*/
            }
        }
    }
}
