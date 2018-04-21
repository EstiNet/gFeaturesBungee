package net.estinet.gFeatures.Feature.MinigameAssister;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.API.Logger.Debug;
import net.estinet.gFeatures.ClioteSkyOld.API.ClioteHook;

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

public class MGMapClioteHook extends ClioteHook{

	public MGMapClioteHook(gFeature feature) {
		super(feature, "mgmap");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			Debug.print("Comparing mgmap " + clioteName + " " + categoryName);
			if(!MinigameAssister.maps.containsKey(clioteName)){
				MinigameAssister.maps.put(clioteName, args.get(0));
			}
			else{
				Debug.print("Was found.");
				MinigameAssister.maps.replace(clioteName, args.get(0));
			}
			SendAll sa = new SendAll();
			sa.sendAllInfo();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
