package net.estinet.gFeatures;

import org.bukkit.Bukkit;

import net.estinet.gFeatures.API.Logger.Debug;

public class gLoop {
	public void start(){
		/*
		 * Initialize gFeatures Core loops here.
		 * 
		 */
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getServer().getPluginManager().getPlugin("gFeatures"), new Runnable() {
        	public void run(){
        		Thread th = new Thread(new Runnable(){
        			public void run(){
        				try{
        					
        				}
        				catch(Exception e){
        					Debug.print(e.getMessage());
        				}
        			}
        		});
        		th.start();
        	}
        }, 1000L, 1000L);
	}
}
