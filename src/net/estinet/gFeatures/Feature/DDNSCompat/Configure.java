package net.estinet.gFeatures.Feature.DDNSCompat;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;

/**
 * Created by Devin on 2017-01-29.
 */
public class Configure {
    @Configs
    public static void onSetup(){
        DDNSCompat base = new DDNSCompat("DDNSCompat", "1.0.0");
        Basic.addFeature(base);

        Basic.addCommand(new DDNSRefreshCommand(base));

    }
}
