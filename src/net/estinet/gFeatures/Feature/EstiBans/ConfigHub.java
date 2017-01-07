package net.estinet.gFeatures.Feature.EstiBans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import net.estinet.gFeatures.Configuration.Config;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2017 EstiNet

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

public class ConfigHub {
	
	/*
	 * Layout of player data.
	 * Bans: [milliseconds to unban] [server] [reason]
	 * Mutes: [milliseconds to unmute] [server] [reason]
	 * Warnings: [milliseconds to unwarn] [id] [reason]
	 */
	
	Config config = new Config();
	public void setupConfig(){
		config.createDirectory("plugins/gFeatures/EstiBans", "[EstiBans] Plugin directory set!");
		config.createDirectory("plugins/gFeatures/EstiBans/playerdata", "[EstiBans] Playerdata directory set!");
		try(Stream<Path> paths = Files.walk(Paths.get("plugins/gFeatures/EstiBans/playerdata"))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		        	File f = new File(filePath.toString());
		        	List<String> list = new ArrayList<>();
		    		try {
		    			FileReader fr = new FileReader(f);
		    			BufferedReader br = new BufferedReader(fr);
		    			String str = "";
		    			while((str = br.readLine()) != null) {
		    				list.add(str);
		    			}   
		    			br.close(); 
		    		} catch (IOException e){
		    			e.printStackTrace();
		    		}
		    		if(f.getName().contains("-bans")){
		    			EstiBans.bans.put(UUID.fromString(f.getName().replace("-bans", "")), list);
		    		}
		    		else if(f.getName().contains("-mutes")){
		    			EstiBans.mutes.put(UUID.fromString(f.getName().replace("-mutes", "")), list);
		    		}
		    		else if(f.getName().contains("-warnings")){
		    			EstiBans.warnings.put(UUID.fromString(f.getName().replace("-warnings", "")), list);
		    		}
		        }
		    });
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
