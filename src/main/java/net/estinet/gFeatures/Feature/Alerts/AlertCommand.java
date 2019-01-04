package net.estinet.gFeatures.Feature.Alerts;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.ComponentSerializers;

public class AlertCommand extends EstiCommand {
    public AlertCommand(gFeature feature) {
        super(new String[]{"alert", "alert"}, "gFeatures.admin", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        StringBuilder l = new StringBuilder();
        for (String arg : args) l.append(arg).append(" ");
        TextComponent message = ComponentSerializers.LEGACY.deserialize("&7[&bAlert&7] &r&f" + l.toString(), '&');
        for (Player p :gFeatures.getInstance().getProxyServer().getAllPlayers()) {
            p.sendMessage(message);
        }
        gFeatures.getInstance().getLogger().info("[Alert] " + l);
        ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("[Alert] " + l), "displaymessage", "all");
    }
}
