package net.estinet.gFeatures.API.Resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ResolverInit {
    public static void loadCache(){
        File f = new File("plugins/gFeatures/Resolver");
        for(File fs : f.listFiles()){
            try {
                FileReader fr = new FileReader(new File(fs.getPath() + "/current.txt"));
                BufferedReader br = new BufferedReader(fr);
                String curname = br.readLine(), uuid = fs.getName();
                List<String> names = new ArrayList<>();
                names.add(curname);
                br.close();
                FileReader frs = new FileReader(new File(fs.getPath() + "/previous.txt"));
                BufferedReader brs = new BufferedReader(frs);
                while(true){
                    String readline = brs.readLine();
                    if(readline == null){
                        break;
                    }
                    else{
                        names.add(readline);
                    }
                }
                brs.close();
                ResolverFetcher.nameData.put(uuid, names);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
