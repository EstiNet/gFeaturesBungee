package net.estinet.gFeatures;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldLoadEvent;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2016 EstiNet

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

public class Library implements Listener{
	@EventHandler
	public void onPlayerJoin(PlayerHandshakeEvent event){
		check("onPlayerHandshake", event);
	}
	@EventHandler
	public void onPlayerLeave(PlayerDisconnectEvent event){
		check("onPlayerDisconnect", event);
	}
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent event){
		check("onServerSwitch", event);
	}
    public void check(String methodname, Event event){
    	List<gFeature> features = Basic.getFeatures();
		for(gFeature feature : features){
			try {
				if(!(feature.getClass().getDeclaredMethod(methodname).equals(null))){
					if(feature.getState().equals(FeatureState.ENABLE)){
					feature.eventTrigger(event);
					}
				}
			} catch (NoSuchMethodException e) {} 
			catch (SecurityException e) {}
			catch (ConcurrentModificationException e) {}
		}
		List<Extension> extensions = Basic.getExtensions();
		List<gUtility> utilities = new ArrayList<>();
		for(Extension ext : extensions){
			if(ext.getType().equals(ExtensionsType.Utility)){
				utilities.add((gUtility) ext);
			}
		}
		for(gUtility utility : utilities){
			try {
				if(!(utility.getClass().getDeclaredMethod(methodname).equals(null))){
					if(utility.getState().equals(FeatureState.ENABLE)){
					utility.eventTrigger(event);
					}
				}
			} catch (NoSuchMethodException e) {} 
			catch (SecurityException e) {}
		}
    }

}
