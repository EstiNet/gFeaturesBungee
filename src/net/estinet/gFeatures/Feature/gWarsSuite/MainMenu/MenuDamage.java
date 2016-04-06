package net.estinet.gFeatures.Feature.gWarsSuite.MainMenu;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;

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

public class MenuDamage {
	public void onEntityDamage(EntityDamageByEntityEvent event){
		event.setCancelled(true);
	}
	public void onWeaponDamage(WeaponDamageEntityEvent event){
		event.setCancelled(true);
	}
}
