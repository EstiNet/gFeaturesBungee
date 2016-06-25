package net.estinet.gFeatures.API.Resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ResolverFetcher {
	public static String getUUIDfromName(String name){
		File f = new File("plugins/gFeatures/Resolver");
		for(File fs : f.listFiles()){
				try {
					FileReader fr = new FileReader(new File(fs.getPath() + "/current.txt"));
					BufferedReader br = new BufferedReader(fr);
					String status = br.readLine();
					br.close();
					if(status.equalsIgnoreCase(name)){
						return fs.getName();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	public static String getNamefromUUID(String uuid){
		File f = new File("plugins/gFeatures/Resolver");
		for(File fs : f.listFiles()){
			if(fs.getName().equals(uuid)){
				try {
					FileReader fr = new FileReader(new File(fs.getPath() + "/current.txt"));
					BufferedReader br = new BufferedReader(fr);
					String status = br.readLine();
					br.close();
					return status;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	public static List<String> getPreviousNames(String name){
		List<String> names = new ArrayList<>();
		File f = new File("plugins/gFeatures/Resolver");
		for(File fs : f.listFiles()){
				try {
					FileReader fr = new FileReader(new File(fs.getPath() + "/current.txt"));
					BufferedReader br = new BufferedReader(fr);
					String status = br.readLine();
					br.close();
					if(status.equalsIgnoreCase(name)){
						FileReader frs = new FileReader(new File(fs.getPath() + "/previous.txt"));
						BufferedReader brs = new BufferedReader(frs);
						while(true){
							String readline = brs.readLine();
							if(readline.equals(null)){
								break;
							}
							else{
								names.add(readline);
							}
						}
						brs.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return names;
	}
}
