package net.genesishub.gFeatures.Feature.gWarsSuite;

import java.util.HashMap;

import net.genesishub.gFeatures.Feature.gWarsSuite.Multiplayer.Team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Constants {
	public final static Location spawnonjoin = new Location(Bukkit.getServer().getWorld("gWars"), 350.3, 103.0, -59.3);
	public final static Location multiplayergunselection = new Location(Bukkit.getServer().getWorld("gWars"), 250.0, 103.0, -59.0); //TODO
	public final static Location multiplayerorangeteamlook = new Location(Bukkit.getServer().getWorld("gWars"), 636.0, 91.0, -96.0); //TODO
	public static HashMap<Team, String> multiplayerpossession = new HashMap<>();
	public static HashMap<Player, gWarsMode> mode = new HashMap<>();
}
