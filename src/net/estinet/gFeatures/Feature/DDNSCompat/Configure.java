package net.estinet.gFeatures.Feature.DDNSCompat;

import net.estinet.gFeatures.Configs;
import net.estinet.gFeatures.gFeatures;

public class Configure {
    @Configs
    public static void onSetup(){
        DDNSCompat base = new DDNSCompat("DDNSCompat", "1.0.0");
        gFeatures.addFeature(base);

        gFeatures.addCommand(new DDNSRefreshCommand(base));

    }
}
