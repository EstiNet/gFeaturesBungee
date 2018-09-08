package net.estinet.gFeatures.Feature.EstiBans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Listeners;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Event;

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

public class EstiBans extends gFeature implements Events {

    public static HashMap<UUID, List<String>> bans = new HashMap<>();
    public static HashMap<UUID, List<String>> mutes = new HashMap<>();
    public static HashMap<UUID, List<String>> warnings = new HashMap<>();

    public static String estiBansPrefix = ChatColor.BOLD + "[" + ChatColor.DARK_AQUA + "Esti" + ChatColor.GOLD + "Bans" + ChatColor.RESET + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.AQUA;

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

    @Override
    public void eventTrigger(Event event) {
        if (event.getClass().getName().substring(26).equalsIgnoreCase("serverconnectevent")) {
            EventHub.onPlayerJoin((ServerConnectEvent) event);
        } else if (event.getClass().getName().substring(26).equalsIgnoreCase("serverswitchevent")) {
            EventHub.onServerSwitch((ServerSwitchEvent) event);
        } else if (event.getClass().getName().substring(26).equalsIgnoreCase("chatevent")) {
            EventHub.onChat((ChatEvent) event);
        }
    }

    @Override
    @Retrieval
    public void onServerConnect() {
    }

    @Override
    @Retrieval
    public void onServerSwitch() {
    }

    @Override
    @Retrieval
    public void onChat() {
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
        try {
            if (isOnServer(ProxyServer.getInstance().getPlayer(uuid), server)) {
                ProxyServer.getInstance().getPlayer(uuid).disconnect(new TextComponent(getProperBanReason(reason, "never")));
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
        try {
            if (isOnServer(ProxyServer.getInstance().getPlayer(uuid), server)) {
                ProxyServer.getInstance().getPlayer(uuid).disconnect(new TextComponent(getProperBanReason(reason, dateFormatted)));
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
        for (String str : mutes.get(uuid)) {

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
        for (String str : warnings.get(uuid)) {
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
        for (String str : warnings.get(uuid)) {
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
        for (String str : warnings.get(uuid)) {
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
        ProxyServer.getInstance().getPlayer(name).disconnect(reason);
    }

    public static boolean isServer(String server) {
        if (server.equalsIgnoreCase("all")) {
            return true;
        }
        if (ProxyServer.getInstance().getServerInfo(server) != null) {
            return true;
        }
        return false;
    }

    public static boolean isOnServer(ProxiedPlayer player, String server) {
        if (server.equalsIgnoreCase("all")) {
            return true;
        }
        if (player.getServer().getInfo().getName().equals(server)) {
            return true;
        }
        return false;
    }

    public static void dumpFile(HashMap<UUID, List<String>> data, String UUID, File f) {
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
                if (Listeners.debug) {
                    e.printStackTrace();
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getProperBanReason(String reason, String length) {
        return ChatColor.DARK_GRAY + "You are banned! Reason: " + ChatColor.DARK_AQUA + reason + ChatColor.DARK_GRAY + " Time until Unbanning: " + ChatColor.DARK_AQUA + length;
    }

    private static void blankPlayer(HashMap<UUID, List<String>> list, File f, UUID uuid, String server, String millis, String reason) {
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

    private static boolean isBlankOn(HashMap<UUID, List<String>> list, UUID uuid, String server, boolean isBans) {
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

    private static String getReason(HashMap<UUID, List<String>> list, UUID uuid, String server) {
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
