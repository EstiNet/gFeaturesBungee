package net.estinet.gFeatures;

import java.util.ConcurrentModificationException;
import java.util.List;

import net.estinet.gFeatures.API.Resolver.JoinHandler;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.plugin.Event;

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

public class Library implements Listener {
    @EventHandler
    public void onPlayerHandshake(PlayerHandshakeEvent event) {
        check("onPlayerHandshake", event);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        check("onPlayerDisconnect", event);
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        check("onServerSwitch", event);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        JoinHandler jh = new JoinHandler();
        jh.init(event.getPlayer());
        check("onPostLogin", event);
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        check("onServerConnect", event);
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        check("onPreLogin", event);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        check("onChat", event);
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        check("onLogin", event);
    }

    public void check(String methodname, Event event) {
        List<gFeature> features = gFeatures.getFeatures();
        for (gFeature feature : features) {
            try {
                if (!(feature.getClass().getDeclaredMethod(methodname).equals(null))) {
                    if (feature.getState().equals(FeatureState.ENABLE)) {
                        feature.eventTrigger(event);
                    }
                }
            } catch (NoSuchMethodException e) {
            } catch (SecurityException e) {
            } catch (ConcurrentModificationException e) {
            }
        }
    }
}
