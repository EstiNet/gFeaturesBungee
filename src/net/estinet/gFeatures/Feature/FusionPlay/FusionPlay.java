package net.estinet.gFeatures.Feature.FusionPlay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.estinet.gFeatures.Events;
import net.estinet.gFeatures.Retrieval;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.CliotePing;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Event;

public class FusionPlay extends gFeature implements Events{

	private static List<FusionCon> connections = new ArrayList<>();
	public static List<Integer> usedID = new ArrayList<>();
	public static Queue<FusionCon> queueConnections = new LinkedList<FusionCon>();

	EventHub eh = new EventHub();

	public FusionPlay(String featurename, String d) {
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
		if(event.getClass().getName().substring(26, event.getClass().getName().length()).equalsIgnoreCase("playerhandshakeevent")){
			eh.onPlayerJoin((PlayerHandshakeEvent)event);
		}
	}
	@Override
	@Retrieval
	public void onPlayerHandshake(){}

	public static List<FusionCon> getConnections(){
		return connections;
	}
	public static void addConnection(FusionCon fc){
		connections.add(fc);
	}
	public static void removeConnection(FusionCon fc){
		connections.remove(fc);
	}
	public static void removeConnection(String clioteName){
		connections.remove(getConnectionArrayID(clioteName));
	}
	public static void addID(int id){
		if(!usedID.contains(id)){
			usedID.add(id);
		}
	}
	public static List<FusionCon> getConnectionsFromID(int id){
		List<FusionCon> list = new ArrayList<>();
		for(FusionCon fc : connections){
			if(fc.getID() == id){
				list.add(fc);
			}
		}
		return list;
	}
	public static boolean hasConnection(String clioteName){
		for(FusionCon fc : connections){
			if(fc.getClioteName().equals(clioteName)){
				return true;
			}
		}
		return false;
	}
	public static int getConnectionArrayID(String clioteName){
		for(int i = 0; i < connections.size(); i++){
			FusionCon fc = connections.get(i);
			if(fc.getClioteName().equals(clioteName)){
				return i;
			}
		}
		return -1;
	}
	public static void replaceConnection(String clioteName){
		FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setStatus(FusionStatus.OFFLINE);
		int id = FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).getID();
		FusionCon fc = queueConnections.poll();
		if(!connections.get(getConnectionArrayID(clioteName)).getCurrentType().equals(fc.getCurrentType())){
			CliotePing cp = new CliotePing();
			cp.sendMessage("fusionplay start", fc.getClioteName()); //PLZ IMPLEMENT
			fc.setStatus(FusionStatus.WAITING);
			fc.setID(id);
			ServerInfo cur = BungeeCord.getInstance().getServerInfo(clioteName);
			ServerInfo si = BungeeCord.getInstance().getServerInfo(fc.getClioteName());
			for(ProxiedPlayer pp : cur.getPlayers()){
				pp.connect(si);
			}
			FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(clioteName)).setID(-1);
		}
		else{
			FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(fc.getClioteName())).setStatus(FusionStatus.OFFLINE);
			FusionPlay.getConnections().get(FusionPlay.getConnectionArrayID(fc.getClioteName())).setID(id);
			CliotePing cp = new CliotePing();
			cp.sendMessage("fusionplay other", fc.getClioteName());
		}
	}
	public static FusionCon getPairedConFromID(FusionCon fc){
		for(FusionCon fcs : getConnectionsFromID(fc.getID())){
			if(!fcs.getClioteName().equals(fc.getClioteName())){
				return fcs;
			}
		}
		return fc;
	}
	public static FusionCon getConnection(String clioteName){
		for(FusionCon fc : connections){
			if(fc.getClioteName().equals(clioteName)){
				return fc;
			}
		}
		return null;
	}
	public static FusionCon getPairedConFromID(String clioteName){
		FusionCon fc = getConnection(clioteName);
		for(FusionCon fcs : getConnectionsFromID(fc.getID())){
			if(!fcs.getClioteName().equals(fc.getClioteName())){
				return fcs;
			}
		}
		return fc;
	}
	public static List<FusionCon> getCurrentGames(){
		List<FusionCon> cons = new ArrayList<>();
		for(int i = 0; i < connections.size(); i++){
			if(isPaired)
		}
	}
	public static boolean isValidID(int num){
		for(FusionCon fc : connections){
			
		}
	}
	public static boolean isPairedID(int id){
		int nums = 0;
		for(FusionCon fc : connections){
			if(fc.getID() == id){
				nums++;
			}
		}
		if(nums > 1){
			return true;
		}
		return false;
	}

}
