package net.estinet.gFeatures.ClioteSky.Network.Protocol.Output;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.ClioteSky.Network.NetworkThread;
import net.estinet.gFeatures.ClioteSky.Network.Protocol.Packet;
import net.md_5.bungee.api.ProxyServer;

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

public class OutputAlive extends Packet{
	public void run(List<String> args){
		if(NetworkThread.clientSocket == null){
			ClioteSky.setServerOnline(false);
		}
		else{
			ClioteSky.setAliveCache(true);
			NetworkThread nt = new NetworkThread();
			nt.sendOutput("alive");
			ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), () -> {
                if(ClioteSky.isAliveCache()){
                    if(!ClioteSky.isServerOnline()){
                        ClioteSky.printLine("Uh oh! Server went offline.");
                        try {
                            NetworkThread.clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ClioteSky.setServerOffline();
                    }
                }
            }, 5, TimeUnit.SECONDS);
		}
	}
}