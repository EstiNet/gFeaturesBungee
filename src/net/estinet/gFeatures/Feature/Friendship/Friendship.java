package net.estinet.gFeatures.Feature.Friendship;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.MojangAPI.UUIDFetcher;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Event;

public class Friendship extends gFeature implements Events{

	EventHub eh = new EventHub();

	public Friendship(String featurename, String d) {
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
	public static void friendRequest(ProxiedPlayer requester, String friend){
		File f = new File("plugins/gFeatures/Friendship/" + requester.getUniqueId() + "/" + friend);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String con = br.readLine();
				if(!(con == null)){
					if(con.equals("requested")){
						requester.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + ChatColor.RED + "Friend request already sent!");
						br.close(); 
						return;
					}
					else if(con.equals("pending")){
						friendConfirm(requester, friend);
						br.close(); 
						return;
					}
					else if(con.equals("confirmed")){
						requester.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + ChatColor.AQUA + "You're already friends with this player!");
						br.close(); 
						return;
					}
				}
				else{
					f.delete();
				}
				br.close(); 
			}
			catch(IOException e) {e.printStackTrace();}
			f.delete();
			f.createNewFile();
			PrintWriter pw = new PrintWriter(f);
			pw.write("requested");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File fs = new File("plugins/gFeatures/Friendship/" + friend + "/" + requester.getUniqueId());
		if(!fs.exists()){
			try {
				fs.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fs.delete();
			fs.createNewFile();
			PrintWriter pw = new PrintWriter(fs);
			pw.write("pending");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		requester.sendMessage("["+ ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] Friend request sent!");
	}
	@SuppressWarnings("deprecation")
	public static void friendConfirm(ProxiedPlayer confirmer, String friend){
		File f = new File("plugins/gFeatures/Friendship/" + confirmer.getUniqueId() + "/" + friend);
		f.delete();
		try {
			f.createNewFile();
			PrintWriter pw = new PrintWriter(f);
			pw.write("confirmed");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File fs = new File("plugins/gFeatures/Friendship/" + friend + "/" + confirmer.getUniqueId());
		fs.delete();
		try {
			fs.createNewFile();
			PrintWriter pw = new PrintWriter(fs);
			pw.write("confirmed");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		UUIDFetcher uf = new UUIDFetcher(Arrays.asList(friend));
		String name = "";
		try {
			name = uf.call().get(friend).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		confirmer.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] You and " + name + " are now friends!");
		if(ProxyServer.getInstance().getPlayer(name) != null){
			ProxyServer.getInstance().getPlayer(name).sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] You and " + confirmer.getName() + " are now friends!");
		}
	}
	@SuppressWarnings("deprecation")
	public static void unFriend(ProxiedPlayer unfriender, String hate){
		File f = new File("plugins/gFeatures/Friendship/" + unfriender.getUniqueId() + "/" + hate);
		f.delete();
		File fs = new File("plugins/gFeatures/Friendship/" + hate + "/" + unfriender.getUniqueId());
		fs.delete();
		UUIDFetcher uf = new UUIDFetcher(Arrays.asList(hate));
		try {
			unfriender.sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] Unfriended " + uf.call().get(hate) + ".");
			if(ProxyServer.getInstance().getPlayer(hate) != null){
				ProxyServer.getInstance().getPlayer(hate).sendMessage("[" + ChatColor.GOLD + "Friends" + ChatColor.WHITE + "] " + unfriender.getName() + " unfriended you!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void getFriendRequests(ProxiedPlayer p, String cliotename){
		File f = new File("plugins/gFeatures/Friendship/" + p.getUniqueId() + "/");
		for(File fs : f.listFiles()){
			try {
				FileReader fr = new FileReader(fs);
				BufferedReader br = new BufferedReader(fr);
				String status = br.readLine();
				if(!(status == null)){
					if(status.equals("pending")){
						CliotePing cp = new CliotePing();
						UUIDFetcher uuid = new UUIDFetcher(Arrays.asList(fs.getName()));
						cp.sendMessage("friendreq " + uuid.call().get(fs.getName()).toString() + " " + p.getName() , cliotename);
					}
				}
				else{
					f.delete();
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void getFriends(ProxiedPlayer p){

	}
	public static void getStatusDetails(ProxiedPlayer p){

	}
}
