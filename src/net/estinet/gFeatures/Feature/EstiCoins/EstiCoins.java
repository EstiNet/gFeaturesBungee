package net.estinet.gFeatures.Feature.EstiCoins;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.List;
import java.util.UUID;

/*
gFeatures
https://github.com/EstiNet/gFeatures

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

public class EstiCoins extends gFeature implements Events {

    //SQL Fields
    public static String sqlAddress, sqlPort, sqlTablename, sqlUsername, sqlPassword;

    public EstiCoins(String featurename, String version) {
        super(featurename, version);
    }

    @Override
    public void enable() {
        Bukkit.getLogger().info("[EstiCoins] Enabled! Yay.");
        if (ConfigManager.check()) {
            String URL = Connection.toURL(sqlPort, sqlAddress, sqlTablename);
            Connection.checkOnline(URL, sqlUsername, sqlPassword);
            connect("CREATE TABLE IF NOT EXISTS Peoples(Name VARCHAR(200), Money FLOAT) ENGINE=InnoDB;");
            connect("UPDATE Peoples SET Money = Money + 1\nWHERE Name = 'InDev';");
        }
    }

    @Override
    public void disable() {
        Bukkit.getLogger().info("[EstiCoins] Disabled. Good bye!");
    }

    @Override
    public void eventTrigger(Event event) {
        if (event.getEventName().equalsIgnoreCase("playerjoinevent")) {
            Connection c = new Connection();
            PlayerJoinEvent e = (PlayerJoinEvent) event;
            new Thread(() -> connect("INSERT INTO Peoples(Name, Money)\n" +
                    "SELECT * FROM (SELECT '" + e.getPlayer().getUniqueId() + "', '0') AS tmp\n" +
                    "WHERE NOT EXISTS (\n" +
                    "SELECT Name FROM Peoples WHERE Name = '" + e.getPlayer().getUniqueId() + "'\n" +
                    ") LIMIT 1;\n"
            )).start();
        }
    }

    @Retrieval
    public void onPlayerJoin() {
    }

    @Override
    public void commandTrigger(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHub ch = new CommandHub();
        ch.init(sender, cmd, label, args);
    }

    private static List<String> connectReturn(String query) {
        return Connection.connectReturn(Connection.toURL(sqlPort, sqlAddress, sqlTablename), sqlUsername, sqlPassword, query);
    }

    private static void connect(String query) {
        Connection.connect(Connection.toURL(sqlPort, sqlAddress, sqlTablename), sqlUsername, sqlPassword, query);
    }

    /*
     * EstiCoins API
     */

    public static double getMoney(UUID uuid) {
        return Double.parseDouble(connectReturn("SELECT Name, Money FROM Peoples WHERE Name = '" + uuid + "';").get(1));
    }

    public static double getMoney(Player p) {
        return getMoney(p.getUniqueId());
    }

    public static void giveMoney(UUID uuid, double amount) {
        connect("UPDATE Peoples SET Money = " + (amount + getMoney(uuid)) + "\nWHERE Name = '" + uuid + "';");
    }

    public static void takeMoney(UUID uuid, double amount) {
        connect("UPDATE Peoples SET Money = " + (amount - getMoney(uuid)) + "\nWHERE Name = '" + uuid + "';");
    }

    public static void setMoney(UUID uuid, double amount) {
        connect("UPDATE Peoples SET Money = " + amount + "\nWHERE Name = '" + uuid + "';");
    }

    public static List<String> top() {
        return connectReturn("SELECT * FROM Peoples;");
    }

    public static void giveMoney(Player p, double amount) {
        giveMoney(p.getUniqueId(), amount);
    }

    public static void takeMoney(Player p, double amount) {
        takeMoney(p.getUniqueId(), amount);
    }

    public static void setMoney(Player p, double amount) {
        setMoney(p.getUniqueId(), amount);
    }

    public static boolean playerExists(Player p) {
        return connectReturn("SELECT Name, Money FROM Peoples WHERE Name = '" + p.getUniqueId().toString() + "';").get(1) != null;
    }

    public static boolean isOnline(String name) {
        final Boolean[] ret = {false};

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (name.equals(player.getName())) ret[0] = true;
        });
        return ret[0];
    }
}
