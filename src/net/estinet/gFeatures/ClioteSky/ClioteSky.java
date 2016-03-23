package net.estinet.gFeatures.ClioteSky;

import org.bukkit.Bukkit;

public class ClioteSky {
	private static String address = "";
	private static String port = "";
	private static String category = "";
	private static String name = "";
	private static String password = "";
	private static boolean enable = false;
	public static String getCategory() {
		return category;
	}
	public static void setCategory(String category) {
		ClioteSky.category = category;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		ClioteSky.name = name;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		ClioteSky.password = password;
	}
	public static boolean isEnable() {
		return enable;
	}
	public static void setEnable(boolean enable) {
		ClioteSky.enable = enable;
	}
	public static void setAddress(String address) {
		ClioteSky.address = address;
	}
	public static void setPort(String port) {
		ClioteSky.port = port;
	}
	public static String getAddress(){
		return address;
	}
	public static String getPort(){
		return port;
	}
	public static void printError(String errorCode){
		Bukkit.getLogger().info("[ClioteSky Error] Error code " + errorCode + " recieved from server.");
	}
	public static void printLine(String line){
		Bukkit.getLogger().info("[ClioteSky] " + line);
	}
}
