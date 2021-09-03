package net.estinet.gFeatures.Feature.EstiBans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2018 EstiNet

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

public class EstiBans extends gFeature {

    public static ConcurrentHashMap<UUID, List<String>> bans = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, List<String>> mutes = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, List<String>> warnings = new ConcurrentHashMap<>();

    public static Component estiBansPrefix = Component.text("[").decoration(TextDecoration.BOLD, true).
            append(Component.text("Esti", NamedTextColor.DARK_AQUA).decoration(TextDecoration.BOLD, true)).
            append(Component.text("Bans", NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true)).
            append(Component.text("] ").decoration(TextDecoration.BOLD, true));

    public EstiBans(String featurename, String d) {
        super(featurename, d);
    }

    @Override
    public void enable() {
        Enable.onEnable();
    }

    @Override
    public void disable() {
        Disable.onDisable();
    }

    public static boolean isBannedOn(String name, String server) {
        return isBannedOn(UUID.fromString(Objects.requireNonNull(ResolverFetcher.getUUIDfromName(name))), server);
    }

    public static boolean isBannedOn(UUID uuid, String server) {
        return isBlankOn(bans, uuid, server, true);
    }

    public static String[] getBans(String name) {
        return getBans(UUID.fromString(ResolverFetcher.getUUIDfromName(name)));
    }

    public static String[] getBans(UUID uuid) {
        checkOverdueBans(uuid);
        return Arrays.copyOf(bans.get(uuid).toArray(), bans.get(uuid).toArray().length, String[].class);
    }

    public static void checkOverdueBans(UUID uuid) {
        for (int i = 0; i < bans.get(uuid).size(); i++) {
            String str = bans.get(uuid).get(i);
            String[] strs = str.split(" ");
            try {
                if (System.currentTimeMillis() >= Double.parseDouble(strs[0])) {
                    unbanPlayer(uuid, strs[1]);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                Debug.print("[EstiBans] (Can ignore) " + e.getMessage());
            }
        }
    }

    public static String getBanReason(String name, String server) {
        return getBanReason(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server);
    }

    public static String getBanReason(UUID uuid, String server) {
        return getReason(bans, uuid, server);
    }

    public static void banPlayer(String name, String server, String reason) {
        banPlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server, reason);
    }

    public static void banPlayer(UUID uuid, String server, String reason) {
        ProxyServer proxyServer = gFeatures.getInstance().getProxyServer();
        try {
            if (proxyServer.getPlayer(uuid).isPresent() && isOnServer(proxyServer.getPlayer(uuid).get(), server)) {
                proxyServer.getPlayer(uuid).get().disconnect(getProperBanReason(reason, "never"));
            }
        } catch (Exception e) {
        }
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
        blankPlayer(bans, f, uuid, server, "never", reason);
    }

    public static void banPlayer(String name, String server, double millis, String reason) {
        banPlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server, millis, reason);
    }

    public static void banPlayer(UUID uuid, String server, double millis, String reason) {
        Date date = new Date((long) (millis - System.currentTimeMillis()));
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        String dateFormatted = formatter.format(date);
        ProxyServer proxyServer = gFeatures.getInstance().getProxyServer();
        try {
            if (proxyServer.getPlayer(uuid).isPresent() && isOnServer(proxyServer.getPlayer(uuid).get(), server)) {
                proxyServer.getPlayer(uuid).get().disconnect(getProperBanReason(reason, dateFormatted));
            }
        } catch (Exception e) {
        }
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
        blankPlayer(bans, f, uuid, server, millis + "", reason);
    }

    public static void unbanPlayer(String name, String server) {
        unbanPlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server);
    }

    public static void unbanPlayer(UUID uuid, String server) {
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
        for (int i = 0; i < bans.get(uuid).size(); i++) {
            try {
                if (bans.get(uuid).get(i).split(" ")[1].equals(server)) {
                    bans.get(uuid).remove(i);
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        dumpFile(bans, uuid.toString(), f);
    }

    public static String[] getMutes(String name) {
        return getMutes(UUID.fromString(ResolverFetcher.getUUIDfromName(name)));
    }

    public static String[] getMutes(UUID uuid) {
        checkOverdueMutes(uuid);
        return Arrays.copyOf(mutes.get(uuid).toArray(), mutes.get(uuid).toArray().length, String[].class);
    }

    public static boolean isMutedOn(String name, String server) {
        return isMutedOn(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server);
    }

    public static boolean isMutedOn(UUID uuid, String server) {
        return isBlankOn(mutes, uuid, server, false);
    }

    public static void checkOverdueMutes(UUID uuid) {
        for (int i = 0; i < mutes.get(uuid).size(); i++) {
            String str = mutes.get(uuid).get(i);
            String[] strs = str.split(" ");
            try {
                if (System.currentTimeMillis() >= Double.parseDouble(strs[0])) {
                    unmutePlayer(uuid, strs[1]);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            }
        }
    }

    public static String getMuteReason(String name, String server) {
        return getMuteReason(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server);
    }

    public static String getMuteReason(UUID uuid, String server) {
        return getReason(mutes, uuid, server);
    }

    public static void mutePlayer(String name, String server, String reason) {
        mutePlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server, reason);
    }

    public static void mutePlayer(UUID uuid, String server, String reason) {
        mutePlayer(uuid, server, "never", reason);
    }

    public static void mutePlayer(String name, String server, String millis, String reason) {
        mutePlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server, millis, reason);
    }

    public static void mutePlayer(UUID uuid, String server, String millis, String reason) {
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-mutes");
        blankPlayer(mutes, f, uuid, server, millis, reason);
    }

    public static void unmutePlayer(String name, String server) {
        unmutePlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), server);
    }

    public static void unmutePlayer(UUID uuid, String server) {
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-mutes");
        for (int i = 0; i < mutes.get(uuid).size(); i++) {
            try {
                if (mutes.get(uuid).get(i).split(" ")[1].equals(server)) {
                    mutes.get(uuid).remove(i);
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        dumpFile(mutes, uuid.toString(), f);
    }

    public static String[] getWarnings(String name) {
        return getWarnings(UUID.fromString(ResolverFetcher.getUUIDfromName(name)));
    }

    public static String[] getWarnings(UUID uuid) {
        checkOverdueWarnings(uuid);
        return Arrays.copyOf(warnings.get(uuid).toArray(), warnings.get(uuid).toArray().length, String[].class);
    }

    public static void checkOverdueWarnings(UUID uuid) {
        for (int i = 0; i < warnings.get(uuid).size(); i++) {
            String str = warnings.get(uuid).get(i);
            String[] strs = str.split(" ");
            try {
                if (System.currentTimeMillis() >= Double.parseDouble(strs[0])) {
                    unwarnPlayer(uuid, strs[1]);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isValidWarnID(UUID uuid, String id) {
        for (int i = 0; i < warnings.get(uuid).size(); i++) {
            String str = warnings.get(uuid).get(i);
            String[] strs = str.split(" ");
            if (System.currentTimeMillis() >= Double.parseDouble(strs[0])) {
                unwarnPlayer(uuid, strs[1]);
            }
            if (strs[1].equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static String getNextWarnID(UUID uuid) {
        long[] longs = new long[warnings.size()];
        int i = 0;
        for (int j = 0; j < warnings.get(uuid).size(); j++) {
            String str = warnings.get(uuid).get(j);
            try {
                String[] strs = str.split(" ");
                if (System.currentTimeMillis() >= Double.parseDouble(strs[0])) {
                    unwarnPlayer(uuid, strs[1]);
                } else {
                    longs[i] = Long.parseLong(strs[1]);
                    i++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        Arrays.sort(longs);
        return "" + (longs[longs.length - 1] + 1);
    }

    public static void warnPlayer(String name, String id, double millis, String reason) {
        warnPlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), id, millis, reason);
    }

    public static void warnPlayer(UUID uuid, String id, double millis, String reason) {
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-warnings");
        warnings.get(uuid).add(millis + " " + id + " " + reason);
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream(f, true));
            pw.append("\n" + millis + " " + id + " " + reason);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void unwarnPlayer(String name, String id) {
        unwarnPlayer(UUID.fromString(ResolverFetcher.getUUIDfromName(name)), id);
    }

    public static void unwarnPlayer(UUID uuid, String id) {
        File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-warnings");
        for (int i = 0; i < warnings.get(uuid).size(); i++) {
            try {
                if (warnings.get(uuid).get(i).split(" ")[1].equals(id)) {
                    warnings.get(uuid).remove(i);
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        dumpFile(warnings, uuid.toString(), f);
    }

    @SuppressWarnings("deprecation")
    public static void kickPlayer(String name, String reason) {
        if (!gFeatures.getInstance().getProxyServer().getPlayer(name).isPresent()) return;
        gFeatures.getInstance().getProxyServer().getPlayer(name).get().disconnect(Component.text(reason));
    }

    public static boolean isServer(String server) {
        if (server.equalsIgnoreCase("all")) {
            return true;
        }
        return gFeatures.getInstance().getProxyServer().getServer(server).isPresent();
    }

    public static boolean isOnServer(Player player, String server) {
        if (server.equalsIgnoreCase("all")) {
            return true;
        }
        if (!player.getCurrentServer().isPresent()) return false;
        return player.getCurrentServer().get().getServerInfo().getName().equals(server);
    }

    public static void dumpFile(ConcurrentHashMap<UUID, List<String>> data, String UUID, File f) {
        f.delete();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream(f, true));
            try {
                for (String value : data.get(UUID)) {
                    pw.write(value);
                }
            } catch (NullPointerException e) {
                if (gFeatures.debug) {
                    e.printStackTrace();
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Component getProperBanReason(String reason, String length) {
        return Component.text("You are banned! Reason: ", NamedTextColor.DARK_GRAY).
                append(Component.text(reason, NamedTextColor.DARK_AQUA)).
                append(Component.text(" Time until Unbanning: ", NamedTextColor.DARK_GRAY)).
                append(Component.text(length, NamedTextColor.DARK_AQUA));
    }

    private static void blankPlayer(ConcurrentHashMap<UUID, List<String>> list, File f, UUID uuid, String server, String millis, String reason) {
        list.get(uuid).add(millis + " " + server + " " + reason);
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream(f, true));
            pw.append("\n").append(millis).append(" ").append(server).append(" ").append(reason);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isBlankOn(ConcurrentHashMap<UUID, List<String>> list, UUID uuid, String server, boolean isBans) {
        try {
            for (String line : list.get(uuid)) {
                String[] str = line.split(" ");
                try {
                    if (str[1].equalsIgnoreCase(server) || str[1].equalsIgnoreCase("all")) {
                        try {
                            if (System.currentTimeMillis() >= Double.parseDouble(str[0])) {
                                if (isBans) {
                                    unbanPlayer(uuid, server);
                                } else {
                                    unmutePlayer(uuid, server);
                                }
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            Debug.print("[EstiBans] (Can ignore) " + e.getMessage());
                        }
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    Debug.print("[EstiBans] (Can ignore) " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getReason(ConcurrentHashMap<UUID, List<String>> list, UUID uuid, String server) {
        try {
            for (String line : list.get(uuid)) {
                String[] str = line.split(" ");
                if (str.length > 2) {
                    if (str[1].equalsIgnoreCase(server) || str[1].equalsIgnoreCase("all")) {
                        StringBuilder reason = new StringBuilder();
                        for (int i = 2; i < str.length; i++) {
                            reason.append(str[i]).append(" ");
                        }
                        return reason.toString();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
