package net.estinet.gFeatures.ClioteSky;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

public class ClioteConfigUtil {
	public void load(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("plugins/gFeatures/Config.yml");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			try{
				ClioteSky.setName(prop.getProperty("ClioteSky.Name").toString());
				ClioteSky.setCategory(prop.getProperty("ClioteSky.Category").toString());
				ClioteSky.setAddress(prop.getProperty("ClioteSky.Address").toString());
				ClioteSky.setEnable(Boolean.parseBoolean(prop.getProperty("ClioteSky.Enable").toString()));
				ClioteSky.setPassword(prop.getProperty("ClioteSky.Password").toString());
				ClioteSky.setPort(prop.getProperty("ClioteSky.Port").toString());
			}
			catch(Exception e){
				e.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		File file = new File("plugins/gFeatures/cliotecache.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void resetCache(){
		File file = new File("plugins/gFeatures/cliotecache.txt");
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addCacheEntry(String message){
		if(message.split(" ")[0].equalsIgnoreCase("hello")){
			return;
		}
		if(message.split(" ")[0].equalsIgnoreCase("create")){
			return;
		}
		File file = new File("plugins/gFeatures/cliotecache.txt");
		ClioteSky.cachedQueries.add(message);
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file, true));
			output.write(message + "\n");
			output.newLine();
			output.close();
		}
		catch(Exception e){}
	}
	public void fillCacheFromFile(){
		try (BufferedReader br = new BufferedReader(new FileReader("plugins/gFeatures/cliotecache.txt"))){
			String CurrentLine;
			while ((CurrentLine = br.readLine()) != null) {
				ClioteSky.cachedQueries.add(CurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
