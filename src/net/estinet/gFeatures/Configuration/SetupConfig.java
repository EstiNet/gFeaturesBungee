package net.estinet.gFeatures.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.gFeature;

/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2016 EstiNet

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
	static List<gFeature> features = Basic.getFeatures();
	public static void setup(){
		config.createDirectory("plugins/gFeatures", "Setup the gFeatures directory for use!");
		config.createFile("plugins/gFeatures/Config.yml", "Setup the gFeatures config for use!");

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
			if(!(prop.containsKey("MySQL.Port"))){
				prop.setProperty("MySQL.Port", "3306");
			}
			if(!(prop.containsKey("MySQL.Address"))){
				prop.setProperty("MySQL.Address", "localhost");
			}
			if(!(prop.containsKey("MySQL.TableName"))){
				prop.setProperty("MySQL.TableName", "gfeatures");
			}
			if(!(prop.containsKey("MySQL.Username"))){
				prop.setProperty("MySQL.Username", "root");
			}
			if(!(prop.containsKey("MySQL.Password"))){
				prop.setProperty("MySQL.Password", "pass123");
			}
			if(!(prop.containsKey("MySQL.State"))){
				prop.setProperty("MySQL.State", "false");
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
