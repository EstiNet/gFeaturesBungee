package net.estinet.gFeatures.Feature.Friendship;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
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

public class Friendship extends gFeature implements Events {

    EventHub eh = new EventHub();

    public Friendship(String featurename, String d) {
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
        if (event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("postloginevent")) {
            eh.onPlayerJoin((PostLoginEvent) event);
        } else if (event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("playerdisconnectevent")) {
            eh.onPlayerDisconnect((PlayerDisconnectEvent) event);
        }
    }

    @Override
    @Retrieval
    public void onPostLogin() {
    }

    @Override
    @Retrieval
    public void onPlayerDisconnect() {
    }

    @SuppressWarnings("deprecation")
    public static void friendRequest(ProxiedPlayer requester, String friend) {
        File f = new File("plugins/gFeatures/Friendship/" + requester.getUniqueId() + "/" + friend);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String con = br.readLine();
                if (!(con == null)) {
                    if (con.equals("requested")) {
                        requester.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + ChatColor.RED + "Friend request already sent!");
                        br.close();
                        return;
                    } else if (con.equals("pending")) {
                        friendConfirm(requester, friend);
                        br.close();
                        return;
                    } else if (con.equals("confirmed")) {
                        requester.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + ChatColor.AQUA + "You're already friends with this player!");
                        br.close();
                        return;
                    }
                } else {
                    f.delete();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            f.delete();
            f.createNewFile();
            PrintWriter pw = new PrintWriter(f);
            pw.write("requested");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File fs = new File("plugins/gFeatures/Friendship/" + friend + "/" + requester.getUniqueId());
        if (!fs.exists()) {
            try {
                fs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fs.delete();
            fs.createNewFile();
            PrintWriter pw = new PrintWriter(fs);
            pw.write("pending");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        requester.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] Friend request sent!");
    }

    @SuppressWarnings("deprecation")
    public static void friendConfirm(ProxiedPlayer confirmer, String friend) {
        //UUIDFetcher uf = new UUIDFetcher(Arrays.asList(friend));
        String name = "";
        try {
            name = ResolverFetcher.getUUIDfromName(friend);
            //name = uf.call().get(friend).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File f = new File("plugins/gFeatures/Friendship/" + confirmer.getUniqueId() + "/" + name);
        f.delete();
        try {
            f.createNewFile();
            PrintWriter pw = new PrintWriter(f);
            pw.write("confirmed");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File fs = new File("plugins/gFeatures/Friendship/" + name + "/" + confirmer.getUniqueId());
        fs.delete();
        System.out.println(fs.getPath());
        try {
            fs.createNewFile();
            PrintWriter pw = new PrintWriter(fs);
            pw.write("confirmed");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        confirmer.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] You and " + friend + " are now friends!");
        if (ProxyServer.getInstance().getPlayer(name) != null) {
            ProxyServer.getInstance().getPlayer(name).sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] You and " + confirmer.getName() + " are now friends!");
        }
    }

    @SuppressWarnings("deprecation")
    public static void unFriend(ProxiedPlayer unfriender, String hates) {
        try {
            //UUIDFetcher uf = new UUIDFetcher(Arrays.asList(hates));
            //String hate = uf.call().get(hates).toString();
            String hate = ResolverFetcher.getUUIDfromName(hates);
            File f = new File("plugins/gFeatures/Friendship/" + unfriender.getUniqueId() + "/" + hate);
            File fs = new File("plugins/gFeatures/Friendship/" + hate + "/" + unfriender.getUniqueId());
            if ((!fs.exists() && !f.exists()) || (!fs.exists() | !f.exists())) {
                try {
                    unfriender.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + hates + " is not added as a friend.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                f.delete();
                fs.delete();
                try {
                    unfriender.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] Unfriended " + hates + ".");
                    if (ProxyServer.getInstance().getPlayer(hate) != null) {
                        ProxyServer.getInstance().getPlayer(hate).sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + unfriender.getName() + " unfriended you!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getFriendRequests(ProxiedPlayer p, String cliotename) {
        File f = new File("plugins/gFeatures/Friendship/" + p.getUniqueId() + "/");
        for (File fs : f.listFiles()) {
            if (!fs.getName().equals("seen")) {
                try {
                    FileReader fr = new FileReader(fs);
                    BufferedReader br = new BufferedReader(fr);
                    String status = br.readLine();
                    if (!(status == null)) {
                        if (status.equals("pending")) {
                            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(ResolverFetcher.getNamefromUUID(fs.getName()) + " " + p.getName()), "friendreq", cliotename);
                        }
                    } else {
                        f.delete();
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> {
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("done " + p.getName()), "friendreq", cliotename);
        });
    }

    public static void getFriends(ProxiedPlayer p, String cliotename) {
        File f = new File("plugins/gFeatures/Friendship/" + p.getUniqueId() + "/");
        for (File fs : f.listFiles()) {
            if (!fs.getName().equals("seen")) {
                ProxyServer.getInstance().getLogger().info(fs.getName());
                try {
                    FileReader fr = new FileReader(fs);
                    BufferedReader br = new BufferedReader(fr);
                    String status = br.readLine();
                    if (!(status == null)) {
                        if (status.equals("confirmed")) {
                            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(ResolverFetcher.getNamefromUUID(fs.getName()) + " " + p.getName()), "friendget", cliotename);
                            Friendship.getStatusDetails(ResolverFetcher.getNamefromUUID(fs.getName()), cliotename);
                        }
                    } else {
                        f.delete();
                    }
                    br.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ProxyServer.getInstance().getScheduler().runAsync(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> {
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("done " + p.getName()), "friendget", cliotename);
        });
    }

    public static void getStatusDetails(String uuid, String cliotename) {
        //UUIDFetcher uuids = new UUIDFetcher(Arrays.asList(uuid));
        String name = "";
        try {
            //name = uuids.call().get(uuid).toString();
            name = ResolverFetcher.getUUIDfromName(uuid);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (ProxyServer.getInstance().getPlayer(uuid) != null) {
            ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("online " + ProxyServer.getInstance().getPlayer(uuid).getServer().getInfo().getName() + " " + uuid), "frienddetails", cliotename);
        } else {
            File f = new File("plugins/gFeatures/Friendship/" + name + "/seen");
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String date = br.readLine();
                String server = br.readLine();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                long diff = Calendar.getInstance().getTimeInMillis() - dateFormat.parse(date).getTime();
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes("offline " + uuid + " " + server + " " + diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes & " + diffSeconds + " seconds ago."), "frienddetails", cliotename);
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
