package net.estinet.gFeatures.Feature.Shop;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Feature.Shop.Enums.Trails;
import net.estinet.gFeatures.Feature.Shop.GUI.MainShop;

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

public class Shop extends gFeature implements Events{
	
	EventHub eh = new EventHub();
	
	public static RedisClient redisClient = null;
	public static StatefulRedisConnection<String, String> connection = null;
	public static RedisCommands<String, String> syncCommands = null;
	
	public static HashMap<UUID, String> playerTrail = new HashMap<>();
	
	public static String IP = "", port = "", password = "", databaseNum = "";
	
	public Shop(String featurename, String d) {
		super(featurename, d);
	}
	@Override
	public void enable(){
		Enable.onEnable();
	}
	@Override
	public void disable(){
		Disable.onDisable();
	}
	@Override
	public void eventTrigger(Event event) {
		if(event.getEventName().equalsIgnoreCase("playerjoinevent")){
			eh.onPlayerJoin((PlayerJoinEvent)event);
		}
		else if(event.getEventName().equalsIgnoreCase("playerquitevent")){
			eh.onPlayerLeave((PlayerQuitEvent) event);
		}
	}
	
	public static int getNumOfTrails(String uuid){
		int num = 0;
		for(Trails trail : Trails.values()){
			if(syncCommands.get("trails-" + uuid + "-" + trail.toString()).equals("true")){
				num++;
			}
		}
		return num;
	}
	public static String getTrail(String uuid, String trailName){
		String value = syncCommands.get("trails-" + uuid + "-" + trailName);
		return value;
	}
	public static int getTotalNumOfTrails(){
		return Trails.values().length-1;
	}
	
	@Override
	public void onPlayerJoin(){}
	
	@Override
	public void onPlayerLeave(){}
	
	@Override
	public void commandTrigger(CommandSender sender, Command cmd, String label, String[] args) { 
			MainShop ms = new MainShop();
			ms.init((Player) sender);
	}
}
