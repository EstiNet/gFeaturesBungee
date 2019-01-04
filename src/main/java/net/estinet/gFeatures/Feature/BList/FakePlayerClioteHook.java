package net.estinet.gFeatures.Feature.BList;

import net.estinet.gFeatures.ClioteSky.ClioteHook;
import net.estinet.gFeatures.ClioteSky.ClioteSky;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakePlayerClioteHook extends ClioteHook {
    public FakePlayerClioteHook(String identifier, String gFeatureName) {
        this.identifier = identifier;
        this.gFeatureName = gFeatureName;
    }

    @Override
    public void run(byte[] data, String clioteName) {
        List<String> args = ClioteSky.parseBytesToStringList(data);
        try {
            if (!clioteName.contains("Bungee")) {
                switch (args.get(0)) {
                    case "update": // fake player separated by §, server represented from clioteName
                        if (BList.fakePlayers.get(clioteName) != null) {
                            BList.numFake -= BList.fakePlayers.get(clioteName).size();
                        }
                        if (args.size() == 1) {
                            BList.fakePlayers.put(clioteName, new ArrayList<>());
                            break;
                        }
                        ArrayList<String> players = new ArrayList<>(Arrays.asList(args.get(1).split("§")));
                        BList.fakePlayers.put(clioteName, players);
                        BList.numFake += players.size();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
