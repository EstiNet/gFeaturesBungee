package net.genesishub.gFeatures.Feature.gWarsSuite.Multiplayer;

import net.genesishub.gFeatures.Feature.gWarsSuite.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;

public class Damage {
	Statistics stats = new Statistics();
	public void onEntityDamage(EntityDamageByEntityEvent event){
		if((OrangeTeam.hasPlayer((Player) event.getDamager()) &&  OrangeTeam.hasPlayer((Player) event.getEntity())) || (BlueTeam.hasPlayer((Player) event.getDamager()) &&  BlueTeam.hasPlayer((Player) event.getEntity()))){
			event.setCancelled(true);
			Bukkit.getLogger().info("Damage averted.");
		}
		else{
			if(event.getEntity().isDead()){
				//TODO Check if player is dead and add kill + death
				event.setCancelled(true);
				
			}
		}
	}
	public void onWeaponDamage(WeaponDamageEntityEvent event){
		if((OrangeTeam.hasPlayer((Player) event.getDamager()) &&  OrangeTeam.hasPlayer((Player) event.getVictim())) || (BlueTeam.hasPlayer((Player) event.getDamager()) &&  BlueTeam.hasPlayer((Player) event.getVictim()))){
			event.setCancelled(true);
			Bukkit.getLogger().info("Damage averted.");
		}
		else{
			//TODO Check if player is dead and add kill + death
		}
	}
}