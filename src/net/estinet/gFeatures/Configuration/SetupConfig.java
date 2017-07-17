package net.estinet.gFeatures.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;

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

public class SetupConfig {

	static Config config = new Config();
	static File f = new File("plugins/gFeatures/Config.yml");
	static List<gFeature> features = gFeatures.getFeatures();
	public static void setup(){
		config.createDirectory("plugins/gFeatures", "Setup the gFeatures directory for use!");
		config.createFile("plugins/gFeatures/Config.yml", "Setup the gFeatures config for use!");
		config.createDirectory("plugins/gFeatures/Resolver", "Setup the Resolver utility directory!");

		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		try {
			input = new FileInputStream(f.getPath());
			prop.load(input);
			input.close();
			output = new FileOutputStream(f.getPath());
			for(gFeature feature : features){
				if(!(prop.containsKey("Plugins." + feature.getName()))){
					prop.setProperty("Plugins." + feature.getName() , "false");
				}
			}
			if(!(prop.containsKey("ClioteSky.Address"))){
				prop.setProperty("ClioteSky.Address", "localhost");
			}
			if(!(prop.containsKey("ClioteSky.Port"))){
				prop.setProperty("ClioteSky.Port", "36000");
			}
			if(!(prop.containsKey("ClioteSky.Category"))){
				prop.setProperty("ClioteSky.Category", "Default");
			}
			if(!(prop.containsKey("ClioteSky.Name"))){
				prop.setProperty("ClioteSky.Name", "Server");
			}
			if(!(prop.containsKey("ClioteSky.Password"))){
				prop.setProperty("ClioteSky.Password", "password");
			}
			if(!(prop.containsKey("ClioteSky.Enable"))){
				prop.setProperty("ClioteSky.Enable", "false");
			}
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
