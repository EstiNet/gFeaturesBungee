package net.estinet.gFeatures.ClioteSky.Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSky.ClioteConfigUtil;
import net.estinet.gFeatures.ClioteSky.ClioteSky;
import net.estinet.gFeatures.ClioteSky.Network.Protocol.Output.OutputHello;
import net.md_5.bungee.api.ProxyServer;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2017 EstiNet

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

public class NetworkThread {
	public static Socket clientSocket = null;
	public static DataOutputStream outToServer = null;
	public static BufferedReader inFromServer = null;
	public static void start(boolean whee){
		try{
			String input;
			try{
				clientSocket = new Socket(ClioteSky.getAddress(), Integer.parseInt(ClioteSky.getPort()));
				outToServer = new DataOutputStream(clientSocket.getOutputStream());
				inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			}
			catch(ConnectException e){
				Debug.print("[ClioteSky] Connection unsuccessful.");
				if(whee){
					ClioteSky.printError("Unable to connect to ClioteSky at " + ClioteSky.getAddress() + ":" + ClioteSky.getPort());
				}
				ClioteSky.setServerOffline();
				clientSocket = null;
				return;
			}
			ClioteSky.printLine("Connected to ClioteSky at " + ClioteSky.getAddress() + ":" + ClioteSky.getPort());
			OutputHello os = new OutputHello();
			os.run(null);		
			while(true){
				try{
					input = inFromServer.readLine();
					if(input == null){
						ClioteSky.printError("Couldn't establish connection with server. We'll try a bit later!");
						ClioteSky.setServerOffline();
						clientSocket.close();
						clientSocket = null;
						break;
					}
					else{
						Decosion de = new Decosion();
						de.decode(input);
					}
				}
				catch(SocketException se){
					ClioteSky.printError("Uh oh! Server went offline.");
					ClioteSky.setServerOffline();
					clientSocket.close();
					clientSocket = null;
					break;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void sendOutput(String message){
		try {
			if(ClioteSky.isSyncedOutput()){
				ClioteSky.secondCachedQueries.add(message);
			}
			else{
				if(ClioteSky.isServerOnline()){
					try{
						DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
						outToServer.writeBytes(message + "\n");
					outToServer.flush();
					ClioteSky.setSyncedOutput(true);
					ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
			            public void run() {
			            	ClioteSky.setSyncedOutput(false);
			            }
			         }, 500, TimeUnit.MILLISECONDS);
					}
					catch(NullPointerException e){
						ClioteConfigUtil ccu = new ClioteConfigUtil();
						ccu.addCacheEntry(message);
					}
					
				}
				else{
					ClioteConfigUtil ccu = new ClioteConfigUtil();
					ccu.addCacheEntry(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
