package net.estinet.gFeatures;

import java.util.List;

import net.estinet.gFeatures.ClioteSky.Network.NetworkThread;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SlashgFeatures extends Command{
	public SlashgFeatures(){
		super("gFeaturesBungee", "gFeatures.admin", "gfb");
	}
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		try {
			if(args.length == 0){
				sender.sendMessage(ChatColor.GRAY + "Please do /gFeatures help.");
			}
			else if(args.length == 1){
				switch(args[0]){
				case "version":
					sender.sendMessage(ChatColor.GRAY + "gFeatures Version " + Listeners.version);
					break;
				case "help":
					sender.sendMessage(ChatColor.GRAY + "------Help------");
					sender.sendMessage(ChatColor.GRAY + "/gFeatures version : States the version.");
					sender.sendMessage(ChatColor.GRAY + "/gFeatures list : Lists all features with their states and versions also.");
					sender.sendMessage(ChatColor.GRAY + "/gFeatures featurestate <Feature> : Gets the state of the feature.");
					sender.sendMessage(ChatColor.GRAY + "/gFeatures send <Message> : Sends a manual message to the ClioteSky server.");
					sender.sendMessage(ChatColor.GRAY + "/gFeatures debug : Turns on debug messages.");
					break;
				case "list":
					List<gFeature> features = Basic.getFeatures();
					sender.sendMessage(ChatColor.GRAY + "Features:");
					sender.sendMessage(ChatColor.GRAY + "Enabled:");
					for(gFeature feature : features){
						if(feature.getState().equals(FeatureState.ENABLE)){
							sender.sendMessage(ChatColor.GRAY + " - " + feature.getName() + " " + feature.getVersion());
						}
					}
					sender.sendMessage(ChatColor.GRAY + "Disabled:");
					for(gFeature feature : features){
						if(feature.getState().equals(FeatureState.DISABLE)){
							sender.sendMessage(ChatColor.GRAY + " - " + feature.getName() + " " + feature.getVersion());
						}
					}
					break;
				case "featurestate":
					sender.sendMessage(ChatColor.GRAY + "Usage: /gFeatures featurestate <Plugin>");
					break;
				case "debug":
					if(Listeners.debug == true){
						Listeners.debug = false;
						sender.sendMessage(ChatColor.GRAY + "Turned off debugging.");
					}
					else{
						Listeners.debug = true;
						sender.sendMessage(ChatColor.GRAY + "Turned on debugging.");
					}
					break;
				default:
					sender.sendMessage(ChatColor.GRAY + "Please do /gFeatures help.");
					break;
				}
			}
			else if(args.length == 2){
				switch(args[0]){
				case "featurestate":
					gFeature feature = Basic.getFeature(args[1]);
					sender.sendMessage(ChatColor.GRAY + "Feature " + args[1] + " state is " + feature.getState().toString());
					break;
				case "send":
					NetworkThread nt = new NetworkThread();
					nt.sendOutput(args[1]);
					sender.sendMessage(ChatColor.GRAY + "Sent message " + args[1] + " to ClioteSky.");
					break;
				default:
					sender.sendMessage(ChatColor.GRAY + "Please do /gFeatures help.");
					break;
				}
			}
			else{
				switch(args[0]){
				case "send":
					String output = "";
					for(int i = 0; i < args.length-1; i++){
						output += args[i+1] + " ";
					}
					NetworkThread nt = new NetworkThread();
					nt.sendOutput(output);
					sender.sendMessage(ChatColor.GRAY + "Sent message " + output + "to ClioteSky.");
					break;
				default:
					sender.sendMessage(ChatColor.GRAY + "Please do /gFeatures help.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
