package net.estinet.gFeatures.Feature.Hubs;

import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

public class EventHub{
	public static HashMap<UUID, Boolean> hash = new HashMap<>();
	public static void onPlayerLogin(PostLoginEvent event) {
		PendingConnection pc = event.getPlayer().getPendingConnection();
		try {
			Object handshake = pc.getClass().getMethod("getHandshake").invoke(pc);
			String host = (String) handshake.getClass().getMethod("getHost").invoke(handshake);
			if (host.equals("survival.estinet.net")) {
				hash.put(event.getPlayer().getUniqueId(), true);
				ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> {
					hash.remove(event.getPlayer().getUniqueId());
				}, 1, TimeUnit.MINUTES);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public static void onServerConnect(ServerConnectEvent event) {
		if (hash.get(event.getPlayer().getUniqueId()) != null && hash.get(event.getPlayer().getUniqueId())) {
			hash.remove(event.getPlayer().getUniqueId());
			if (event.getTarget().getName().equals("Hub")) {
				ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(event.getPlayer().getName()), "survivalmenu", "Hubs");
			} else {
				event.setTarget(ProxyServer.getInstance().getServerInfo("Hub"));
				ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(event.getPlayer().getName()), "survivalmenu", "Hubs");
			}
		}
	}
}
