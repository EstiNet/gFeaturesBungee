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
				pw.write(senderName + "\n");
				pw.write(mail);
				pw.close();
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
		int linenum = 1;
		boolean hasmail = false;
		String name = "";
		for(File fs : array){
			try {
				FileReader fr = new FileReader(fs);
				BufferedReader br = new BufferedReader(fr);
				while((line = br.readLine()) != null) {
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
