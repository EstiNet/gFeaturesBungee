package net.estinet.gFeatures.Feature.EstiBans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Event;

public class EstiBans extends gFeature implements Events{

	EventHub eh = new EventHub();

	public static String estiBansPrefix = ChatColor.BOLD + "[" + ChatColor.DARK_AQUA + "Esti" + ChatColor.GOLD + "Bans" + ChatColor.RESET + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.AQUA;

	public EstiBans(String featurename, String d) {
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
		if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("serverconnectevent")){
			eh.onPlayerJoin((ServerConnectEvent)event);
		}
	}
	@Override
	@Retrieval
	public void onServerConnect(){}

	public static boolean isBannedOn(String name, String server){
		return isBannedOn(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static boolean isBannedOn(UUID uuid, String server){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String str = "";
			while((str = br.readLine()) != null) {
				String[] strs = str.split(" ");
				if(strs[1].equals(server)){
					br.close(); 
					return true;
				}
			}   
			br.close(); 
		} catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}
	public static String getBanReason(String name, String server){
		return getBanReason(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static String getBanReason(UUID uuid, String server){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String str = "";
			while((str = br.readLine()) != null) {
				String[] strs = str.split(" ");
				if(strs[1].equals(server)){
					String reason = "";
					for(int i = 2; i < strs.length; i++){
						reason += strs[i];
					}
					br.close();
					return reason;
				}
			}   
			br.close(); 
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public static void banPlayer(String name, String server, String reason){
		banPlayer(ResolverFetcher.getUUIDfromName(name), server, reason);
	}
	public static void banPlayer(UUID uuid, String server, String reason){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
		PrintWriter pw;
		try {
			pw = new PrintWriter(f);
			pw.write("forever " + server + " " + reason);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void banPlayer(String name, String server, double millis, String reason){
		banPlayer(ResolverFetcher.getUUIDfromName(name), server, millis, reason);
	}
	public static void banPlayer(UUID uuid, String server, double millis, String reason){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
		PrintWriter pw;
		try {
			pw = new PrintWriter(f);
			pw.write(millis + " " + server + " " + reason);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void unbanPlayer(String name, String server){
		unbanPlayer(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static void unbanPlayer(UUID uuid, String server){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
		String line = "";
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String str = "";
			while((str = br.readLine()) != null) {
				String[] strs = str.split(" ");
				if(strs[1].equals(server)){
					br.close();
					line = str;
					break;
				}
			}   
			br.close(); 
		} catch (IOException e){
			e.printStackTrace();
		}
		deleteLine(f, new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-banstmp.txt"), line);
	}
	public static boolean isMutedOn(String name, String server){
		return isMutedOn(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static boolean isMutedOn(UUID uuid, String server){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-mutes");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String str = "";
			while((str = br.readLine()) != null) {
				String[] strs = str.split(" ");
				if(strs[1].equals(server)){
					br.close(); 
					return true;
				}
			}   
			br.close(); 
		} catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}
	public static String getMuteReason(String name, String server){
		return getMuteReason(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static String getMuteReason(UUID uuid, String server){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString() + "-bans");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String str = "";
			while((str = br.readLine()) != null) {
				String[] strs = str.split(" ");
				if(strs[1].equals(server)){
					String reason = "";
					for(int i = 2; i < strs.length; i++){
						reason += strs[i];
					}
					br.close();
					return reason;
				}
			}   
			br.close(); 
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public static void mutePlayer(String name, String server, String reason){
		mutePlayer(ResolverFetcher.getUUIDfromName(name), server, reason);
	}
	public static void mutePlayer(UUID uuid, String server, String reason){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString());
		replaceSelected("bansiqs", "muteiqs true forever " + server + " " + reason, f);
	}
	public static void mutePlayer(String name, String server, double millis, String reason){
		mutePlayer(ResolverFetcher.getUUIDfromName(name), server, millis, reason);
	}
	public static void mutePlayer(UUID uuid, String server, double millis, String reason){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString());
		replaceSelected("bansiqs", "muteiqs true " + millis + " " + server + " " + reason, f);
	}
	public static void unmutePlayer(String name, String server){
		unmutePlayer(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static void unmutePlayer(UUID uuid, String server){
		File f = new File("plugins/gFeatures/EstiBans/playerdata/" + uuid.toString());
		replaceSelected("bansiqs", "muteiqs false", f);
	}
	public static void warnPlayer(String name, String server, double millis, String reason){
		warnPlayer(ResolverFetcher.getUUIDfromName(name), server, millis, reason);
	}
	public static void warnPlayer(UUID uuid, String server, double millis, String reason){

	}
	public static void unwarnPlayer(String name, String server){
		unwarnPlayer(ResolverFetcher.getUUIDfromName(name), server);
	}
	public static void unwarnPlayer(UUID uuid, String server){

	}
	@SuppressWarnings("deprecation")
	public static void kickPlayer(String name, String reason){
		ProxyServer.getInstance().getPlayer(name).disconnect(reason);
	}
	public static boolean isServer(String server){
		if(server.equalsIgnoreCase("all")){
			return true;
		}
		if(ProxyServer.getInstance().getServerInfo(server) != null){
			return true;
		}
		return false;
	}
	public static boolean deleteLine(File inputFile, File tempFile, String lineToRemove){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.equals(lineToRemove)) continue;
				writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close(); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return tempFile.renameTo(inputFile);
	}
	public static void replaceSelected(String replaceWith, String type, File f) {
		try {
			// input the file content to the String "input"
			BufferedReader file = new BufferedReader(new FileReader(f));
			String line;String input = "";

			while ((line = file.readLine()) != null) input += line + '\n';

			file.close();

			// this if structure determines whether or not to replace "0" or "1"
			if (Integer.parseInt(type) == 0) {
				input = input.replace(replaceWith + "1", replaceWith + "0"); 
			}
			else if (Integer.parseInt(type) == 1) {
				input = input.replace(replaceWith + "0", replaceWith + "1");
			} 

			// write the new String with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream("notes.txt");
			fileOut.write(input.getBytes());
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
