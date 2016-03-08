package net.estinet.gFeatures.Command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import net.estinet.gFeatures.Extension;
import net.estinet.gFeatures.gFeature;

public class EstiCommand extends BukkitCommand{
	private gFeature feature;
	private Extension extension;
	private CommandExecutable execution;
	private boolean multiThread, methodTrigger = false;
	public EstiCommand(String name, String description, String usageMessage, String permission, gFeature feature, CommandExecutable executedTask){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.feature = feature;
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, Extension extension, CommandExecutable executedTask){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.extension = extension;
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, gFeature feature, CommandExecutable executedTask, List<String> aliases){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.feature = feature;
		super.setAliases(aliases);
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, Extension extension, CommandExecutable executedTask, List<String> aliases){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.extension = extension;
		super.setAliases(aliases);
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, gFeature feature, CommandExecutable executedTask, boolean methodTrigger){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.feature = feature;
		this.methodTrigger = methodTrigger;
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, Extension extension, CommandExecutable executedTask, boolean methodTrigger){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.extension = extension;
		this.methodTrigger = methodTrigger;
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, gFeature feature, CommandExecutable executedTask, List<String> aliases, boolean methodTrigger){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.feature = feature;
		super.setAliases(aliases);
		this.methodTrigger = methodTrigger;
		execution = executedTask;
	}
	public EstiCommand(String name, String description, String usageMessage, String permission, Extension extension, CommandExecutable executedTask, List<String> aliases, boolean methodTrigger){
		super(name);
		super.setDescription(description);
		super.setPermission(permission);
		super.setUsage(usageMessage);
		this.extension = extension;
		super.setAliases(aliases);
		this.methodTrigger = methodTrigger;
		execution = executedTask;
	}
	public gFeature getFeature(){
		return feature;
	}
	public Extension getExtension(){
		return extension;
	}
	public void setFeature(gFeature feature){
		this.feature = feature;
	}
	public void setExtension(Extension extension){
		this.extension = extension;
	}
	@Override
	public boolean execute(CommandSender sender, String arg1, String[] args) {
		if(methodTrigger){
			feature.commandTrigger(sender, this, this.getLabel(), args);
		}
		else{
		execution.args = args;
		execution.cmd = this;
		execution.sender = sender;
		if(!multiThread){
			execution.run();
		}
		else{
			Thread thr = new Thread(new Runnable(){
				public void run(){
					execution.run();
				}
			});
			thr.start();
		}
		}
		return false;
	}
}
