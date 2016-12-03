package net.estinet.gFeatures.Feature.FusionPlay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.Configuration.Config;

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

public class ConfigHub {
	Config config = new Config();
	public void setupConfig(){
		config.createDirectory("plugins/gFeatures/FusionPlay", "[FusionPlay] plugin directory set!");
		config.createFile("plugins/gFeatures/FusionPlay/config.properties", "[FusionPlay] plugins config file created!");
		File f = new File("plugins/gFeatures/FusionPlay/config.properties");
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		try {
			input = new FileInputStream(f.getPath());
			prop.load(input);
			input.close();
			output = new FileOutputStream(f.getPath());
			if(!(prop.containsKey("Redis.IP"))){
				prop.setProperty("Redis.IP", "localhost");
			}
			if(!(prop.containsKey("Redis.Port"))){
				prop.setProperty("Redis.Port", "6379");
			}
			if(!(prop.containsKey("Redis.Password"))){
				prop.setProperty("Redis.Password", "pass123");
			}
			if(!(prop.containsKey("Redis.DatabaseNumber"))){
				prop.setProperty("Redis.DatabaseNumber", "2");
			}
			if(!(prop.containsKey("Server.maxNumOfServers"))){
				prop.setProperty("Server.maxNumOfServers", "1");
			}
			// save properties to project root folder
			prop.store(output, null);
			FusionPlay.IP = prop.getProperty("Redis.IP") + "";
			FusionPlay.port = prop.getProperty("Redis.Port") + "";
			FusionPlay.password = prop.getProperty("Redis.Password") + "";
			FusionPlay.databaseNum = prop.getProperty("Redis.DatabaseNumber") + "";
			FusionPlay.maxNumOfGames = Integer.parseInt(prop.getProperty("Server.maxNumOfServers"));
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
