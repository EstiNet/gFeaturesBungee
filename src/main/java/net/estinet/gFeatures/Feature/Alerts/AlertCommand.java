package net.estinet.gFeatures.Feature.Alerts;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.serializer.ComponentSerializers;

import java.awt.*;

public class AlertCommand extends EstiCommand {
    public AlertCommand(gFeature feature) {
        super(new String[]{"alert", "alert"}, "gFeatures.admin", feature);
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        TextComponent message = ComponentSerializers.LEGACY.deserialize("&7[&bAlert&7] &r&f", '&');
        for (String arg : args) message.append(TextComponent.of(arg + " "));
        for (Player p :gFeatures.getInstance().getProxyServer().getAllPlayers()) {
            p.sendMessage(message);
        }
    }
}
