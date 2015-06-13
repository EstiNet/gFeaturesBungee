package net.genesishub.gFeatures.Feature.gWarsSuite.MainMenu;

import net.genesishub.gFeatures.Basic;
import net.genesishub.gFeatures.API.Inventory.ClearInventory;
import net.genesishub.gFeatures.API.Messaging.ActionAPI;
import net.genesishub.gFeatures.Feature.gWarsSuite.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Join {
	ActionAPI aapi = new ActionAPI();
	Statistics stats = new Statistics();
	public void start(PlayerJoinEvent event){
		aapi.sendActionbar(event.getPlayer(), ChatColor.AQUA + "Welcome to the " + ChatColor.GOLD + "gWars " + ChatColor.AQUA + "Beta! Code version: " + Basic.getFeature("gWarsSuite").getVersion());
		//TODO Player cinematic
		Player p = event.getPlayer();
		ClearInventory ci = new ClearInventory();
		ci.clearInv(event.getPlayer());
		Location spawnonjoin = new Location(Bukkit.getServer().getWorld("gWars"), 350.3, 103.0, -59.3);
		p.teleport(spawnonjoin);
		p.setWalkSpeed((float) 0.5);
		/*for(Player sp : Bukkit.getOnlinePlayers()){
			if(stats.getMode(sp).equals(gWarsMode.MAINMENU) ||  stats.getMode(sp).equals(gWarsMode.GUNMENU) || stats.getMode(sp).equals(gWarsMode.SPAWNMENU) || stats.getMode(sp).equals(gWarsMode.TEAMMENU)){
				p.hidePlayer(sp);
			}
		}
		for (Player players : Bukkit.getOnlinePlayers()){
            players.hidePlayer(p);
        }*///TODO NEED IT LATER MAYBE
		p.setGameMode(GameMode.ADVENTURE);
		ItemStack item1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.CREEPER.ordinal());
		SkullMeta item1meta = (SkullMeta) item1.getItemMeta();
		item1meta.setDisplayName(ChatColor.GOLD + "Singleplayer");
		item1.setItemMeta(item1meta);
		
		ItemStack item2 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta item2meta = (SkullMeta) item2.getItemMeta();
		item2meta.setDisplayName(ChatColor.GOLD + "Multiplayer");
		item2.setItemMeta(item2meta);
		
		p.getInventory().setItem(0, item1);
		p.getInventory().setItem(1, item2);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getServer().getPluginManager().getPlugin("gFeatures"), new Runnable() {
        	public void run(){
        		p.playSound(p.getLocation(), "Kime&Vexento-Climb(Fixed)", 500, 1);
        	}
        }, 200L);
		
	}
	public void end(Player p){
		for (Player players : Bukkit.getOnlinePlayers()){
            players.showPlayer(p);
        }
	}
}
