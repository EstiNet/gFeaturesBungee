package net.estinet.gFeatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.estinet.gFeatures.API.PlayerStats.Setup;
import net.estinet.gFeatures.API.PlayerStats.gPlayer;
import net.estinet.gFeatures.SQL.Player.EstiPlayer;
import net.estinet.gFeatures.SQL.Player.EstiSet;

/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2015 EstiNet

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

public class Basic<T,V> {
	static Setup setup = new Setup();
	public static List<gFeature> features = new ArrayList<>();
	private static List<Extension> extensions = new ArrayList<>();
	private static HashMap<String, String> playersections = new HashMap<>();
	private static List<gPlayer> playerstats = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private static List<EstiSet> sqlplayerfields = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private static List<EstiPlayer> sqlplayers = new ArrayList<>();
	
	public static void addFeature(gFeature feature){
		features.add(feature);
	}
	public static void addExtension(Extension extend){
		extensions.add(extend);
	}
	public static void addPlayerSection(String sectionname, String defaultvalue){
		playersections.put(sectionname, defaultvalue);
	}
	public static void addgPlayer(gPlayer player){
		playerstats.add(player);
	}
	@SuppressWarnings("rawtypes")
	public static void addSQLFields(EstiSet field){
		sqlplayerfields.add(field);
	}
	@SuppressWarnings("rawtypes")
	public static void addEstiPlayer(EstiPlayer estiplayer){
		sqlplayers.add(estiplayer);
	}
	public static void removeFeature(gFeature feature){
		features.remove(feature);
	}
	public static void removeExtension(Extension extend){
		extensions.remove(extend);
	}
	public static void removePlayerSection(String sectionname, String defaultvalue){
		playersections.remove(sectionname);
	}
	public static void removegPlayer(gPlayer player){
		playerstats.remove(player);
	}
	@SuppressWarnings("rawtypes")
	public static void removeSQLFields(EstiSet field){
		sqlplayerfields.remove(field);
	}
	@SuppressWarnings("rawtypes")
	public static void removeEstiPlayer(EstiPlayer estiplayer){
		sqlplayers.remove(estiplayer);
	}
	public static gFeature getFeature(String name){
		for(gFeature feature : features){
			if(feature.getName().equalsIgnoreCase(name)){
				return feature;
			}
		}
		return null;
	}
	public static Extension getExtension(String name){
		for(Extension extend : extensions){
			if(extend.getName().equalsIgnoreCase(name)){
				return extend;
			}
		}
		return null;
	}
	public static gPlayer getgPlayer(String uuid){
		for(gPlayer player : playerstats){
			if(player.getUUID().equalsIgnoreCase(uuid)){
				return player;
			}
		}
		return null;
	}
	public static List<gFeature> getFeatures(){
		return features;
	}
	public static List<Extension> getExtensions(){
		return extensions;
	}
	public static HashMap<String, String> getPlayerSections(){
		return playersections;
	}
	public static List<gPlayer> getgPlayers(){
		return playerstats;
	}
	@SuppressWarnings("rawtypes")
	public static List<EstiSet> getSQLFields(){
		return sqlplayerfields;
	}
	@SuppressWarnings("rawtypes")
	public static List<EstiPlayer> getEstiPlayers(){
		return sqlplayers;
	}
	public static void setgPlayers(List<gPlayer> pl){
		playerstats = pl;
	}
	public static void setFeatures(List<gFeature> pl){
		features = pl;
	}
	public static void setExtensions(List<Extension> pl){
		extensions = pl;
	}
	@SuppressWarnings("rawtypes")
	public static void setSQLFields(List<EstiSet> set){
		sqlplayerfields = set;
	}
	@SuppressWarnings("rawtypes")
	public static void setEstiPlayers(List<EstiPlayer> set){
		sqlplayers = set;
	}
	public static void setgPlayer(gPlayer originalplayer, gPlayer newplayer){
		playerstats.set(playerstats.indexOf(originalplayer), newplayer);
		setup.flushPlayer(newplayer);
	}
	public static void resetFeatures(){
		List<gFeature> feature = new ArrayList<>();
		features = feature;
	}
	public static void resetExtensions(){
		List<Extension> extend = new ArrayList<>();
		extensions = extend;
	}
}
