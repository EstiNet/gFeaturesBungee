package net.estinet.gFeatures.Feature.FusionPlay;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.estinet.gFeatures.Feature.FusionPlay.GameUtil.Logistics.SpectateProcess;
import net.estinet.gFeatures.Feature.FusionPlay.GameUtil.Logistics.Events.JoinProcess;

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

public class EventHub{
	public void onPlayerJoin(PlayerJoinEvent event){
		if(FusionPlay.currentGame.getSettings().usesPlayerCollision() == false){
			event.getPlayer().setCollidable(false);
		}
		if(!FusionPlay.assigned && !event.getPlayer().hasPermission("gFeatures.admin")){
			event.getPlayer().kickPlayer("Server hasn't been assigned!");
		}
		else{
			JoinProcess.init(event);
		}
	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		if(SpectateProcess.spectators.contains(event.getPlayer().getUniqueId())){
			event.setCancelled(true);
		}
	}

	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if(FusionPlay.currentGame.getSettings().allowPlayerLoseHunger()){
			if(event.getEntity() instanceof Player){
				Player p = (Player) event.getEntity();
				p.setFoodLevel(20);
			}
		}
	}
}
