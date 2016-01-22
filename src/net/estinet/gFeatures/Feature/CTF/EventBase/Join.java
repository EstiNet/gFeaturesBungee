package net.estinet.gFeatures.Feature.CTF.EventBase;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import net.estinet.gFeatures.Feature.CTF.Basic;
import net.estinet.gFeatures.Feature.CTF.PlayerMode;
import net.estinet.gFeatures.Feature.CTF.EventBase.GameFunc.Action;
import net.estinet.gFeatures.Feature.CTF.EventBase.GameFunc.StartStop;
import net.estinet.gFeatures.Feature.CTF.Holo.CTFScore;
import net.estinet.gFeatures.Feature.CTF.Holo.Lobby;

public class Join {
	StartStop ss = new StartStop();
	Spectate s = new Spectate();
	Lobby l = new Lobby();
	CTFScore ctfs = new CTFScore();
	public void init(PlayerJoinEvent event){
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
		Basic.kills.put(event.getPlayer().getUniqueId(), 0);
		Basic.deaths.put(event.getPlayer().getUniqueId(), 0);
		switch(Basic.mode){
		case WAITING:
			for(Player p : Bukkit.getOnlinePlayers()){
				p.setScoreboard(l.Initialize(p));
			}
			Basic.modes.put(event.getPlayer().getUniqueId(), PlayerMode.WAITING);
			event.getPlayer().teleport(Basic.waitspawn);
			if(Bukkit.getOnlinePlayers().size() >= 2){
				Action.sendAll(ChatColor.AQUA + "Enough players! Game will be starting in 1 minute.");
				ss.start();
			}
			break;
		case ENDED:
			for(Player p : Bukkit.getOnlinePlayers()){
				p.setScoreboard(ctfs.Initialize(p));
			}
			event.getPlayer().teleport(Basic.spectatespawn);
			Basic.modes.put(event.getPlayer().getUniqueId(), PlayerMode.SPECTATE);
			s.handler(event.getPlayer());
			break;
		case STARTED:
			for(Player p : Bukkit.getOnlinePlayers()){
				p.setScoreboard(ctfs.Initialize(p));
			}
			event.getPlayer().teleport(Basic.spectatespawn);
			Basic.modes.put(event.getPlayer().getUniqueId(), PlayerMode.SPECTATE);
			s.handler(event.getPlayer());
			break;
		default:
			break;
		}
	}
}
