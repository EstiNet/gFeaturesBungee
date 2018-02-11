package net.estinet.gFeatures;

import net.md_5.bungee.api.plugin.Event;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2018 EstiNet

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

public class gFeature{
	private String name;
	private String version;
	private FeatureState state;
	private String commandlabel;
	
	public gFeature(String featurename, String d){
		name = featurename;
		version = d;
	}
	public void setName(String featurename){
		name = featurename;
		return;
	}
	public void setVersion(String featureversion){
		version = featureversion;
		return;
	}
	public void setState(FeatureState states){
		state = states;
		return;
	}
	public void setCommandLabel(String label){
		commandlabel = label;
		return;
	}
	public String getName(){
		return name;
	}
	public String getVersion(){
		return version;
	}
	public FeatureState getState(){
		return state;
	}
	public String getCommandLabel(){
		return commandlabel;
	}
	public void enable(){}
	public void disable(){}
	public void eventTrigger(Event event){}
	public void onPlayerHandshake(){}
	public void onPlayerDisconnect(){}
	public void onServerSwitch(){}
	public void onPostLogin(){}
	public void onServerConnect(){}
	public void onPreLogin(){}
	public void onLogin(){}
	public void onChat(){}
}
