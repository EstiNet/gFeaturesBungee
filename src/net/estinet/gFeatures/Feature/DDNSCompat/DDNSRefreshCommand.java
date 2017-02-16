package net.estinet.gFeatures.Feature.DDNSCompat;

import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

public class DDNSRefreshCommand extends EstiCommand {

    public DDNSRefreshCommand(gFeature feature) {
        super("ddnsrefresh", "gFeatures.admin", new String[0], feature);
    }

    @Override
    public void execute(CommandSender arg0, String[] arg1) {

    }

}
