package net.estinet.gFeatures.API.Resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
