package net.estinet.gFeatures.Feature.gFactions.Menus;

import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Feature.gFactions.Blaze;

public class GetMenu {
	net.estinet.gFeatures.Feature.gFactions.Menus.Autorifle.Beginner autorifle = new net.estinet.gFeatures.Feature.gFactions.Menus.Autorifle.Beginner();
	public void start(PlayerInteractEntityEvent event){
		if(event.getRightClicked().getLocation().equals(Blaze.autorifle)){
			if(!Basic.getgPlayer(event.getPlayer().getUniqueId().toString()).getValue("gFactions.Tier").equals("autorifle")){
				autorifle.makeInventory(event.getPlayer(), Basic.getgPlayer(event.getPlayer().getUniqueId().toString()));
			}
		}
		else if(event.getRightClicked().getLocation().equals(Blaze.shotgun)){
			
		}
		else if(event.getRightClicked().getLocation().equals(Blaze.sniper)){
			
		}
	}
}
