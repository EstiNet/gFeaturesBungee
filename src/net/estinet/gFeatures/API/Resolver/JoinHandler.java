package net.estinet.gFeatures.API.Resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JoinHandler {
	public void init(ProxiedPlayer p){
		File f = new File("plugins/gFeatures/Resolver/" + p.getUniqueId() + "/");
		File cur = new File("plugins/gFeatures/Resolver/" + p.getUniqueId() + "/current.txt");
		File pre = new File("plugins/gFeatures/Resolver/" + p.getUniqueId() + "/previous.txt");
		if(!f.isDirectory()){
			f.mkdir();
		}
		if(!cur.exists()){
			try {
				cur.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!pre.exists()){
			try {
				pre.createNewFile();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		try {
			FileReader fr = new FileReader(cur);
			BufferedReader br = new BufferedReader(fr);
			String status = br.readLine();
			if(!(status == null)){
				if(!status.equals(p.getName())){
					cur.delete();
					cur.createNewFile();
					PrintWriter pw = new PrintWriter(cur);
					pw.write(p.getName());
					pw.close();
					PrintWriter pws = new PrintWriter(pre);
					pws.write(status + "\n");
					pws.close();
				}
			}
			else{
				cur.delete();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
