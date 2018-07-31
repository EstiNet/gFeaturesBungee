package net.estinet.gFeatures.Feature.Hubs;

import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PostLoginEvent;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.HashMap;

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
	public static void onPlayerLogin(PostLoginEvent event) {
		PendingConnection pc = event.getPlayer().getPendingConnection();
		try {
			Object handshake = pc.getClass().getMethod("getHandshake").invoke(pc);
			String host = (String) handshake.getClass().getMethod("getHost").invoke(handshake);
			if (host.equals("survival.estinet.net")) {
				ClioteSky.getInstance().sendAsync(ClioteSky.stringToBytes(event.getPlayer().getName()), "survivalmenu", "Hubs");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
