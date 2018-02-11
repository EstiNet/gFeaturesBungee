package net.estinet.gFeatures.Feature.EstiMail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
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

public class EstiMail extends gFeature implements Events{

	EventHub eh = new EventHub();

	public EstiMail(String featurename, String d) {
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
		if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("postloginevent")){
			eh.onPlayerJoin((PostLoginEvent)event);
		}
	}
	@Override
	@Retrieval
	public void onPostLogin(){}

	@SuppressWarnings("deprecation")
	public static void sendMail(String senderName, String recieverUUID, String mail){
		File f = new File("plugins/gFeatures/EstiMail/" + recieverUUID);
		if(!f.isDirectory()){
			f.mkdir();
		}
		File fs = new File("plugins/gFeatures/EstiMail/" + recieverUUID + "/" + (int)Math.floor(Math.random()*10000));
		if(fs.exists()){
			sendMail(senderName, recieverUUID, mail);
			return;
		}
		else{
			try {
				fs.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				PrintWriter pw = new PrintWriter(fs);
				pw.write(senderName + "\r\n");
				pw.write(mail);
				pw.close();
				try{
				if(!(ProxyServer.getInstance().getPlayer(recieverUUID) == null)){
					ProxyServer.getInstance().getPlayer(recieverUUID).sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "You have new mail! Do /mail read to check!");
				}
				}
				catch(NullPointerException e){}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("deprecation")
	public static void getMail(ProxiedPlayer reciever){
		File f = new File("plugins/gFeatures/EstiMail/" + reciever.getUniqueId().toString());
		File[] array = f.listFiles();
		String line = null;
		boolean hasmail = false;
		String name = "";
		for(File fs : array){
			ProxyServer.getInstance().getLogger().info(fs.getAbsolutePath());
			try {
				FileReader fr = new FileReader(fs);
				BufferedReader br = new BufferedReader(fr);
				int linenum = 1;
				while((line = br.readLine()) != null) {
					ProxyServer.getInstance().getLogger().info(line);
					if(linenum == 1){
						name = line;
					}
					else if(linenum == 2){
						reciever.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + name + ": " + line);
						hasmail = true;
					}
					linenum++;
				}   
				br.close(); 
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		if(!hasmail){
			reciever.sendMessage(ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.WHITE + "You have no new mail.");
		}
	}
	public static void clearMail(ProxiedPlayer reciever){
		File f = new File("plugins/gFeatures/EstiMail/" + reciever.getUniqueId().toString());
		for(File fs : f.listFiles()){
			fs.delete();
		}
	}
	public static boolean checkExists(String uuid) {
		File f = new File("plugins/gFeatures/EstiMail/" + uuid);
		if(!f.isDirectory()){
			return false;
		}
		else{
			return true;
		}
	}
}
