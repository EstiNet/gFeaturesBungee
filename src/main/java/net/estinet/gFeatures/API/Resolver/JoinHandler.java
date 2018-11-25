package net.estinet.gFeatures.API.Resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.velocitypowered.api.proxy.Player;

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

public class JoinHandler {
    public static void init(Player p) {
        File f = new File("plugins/gFeatures/Resolver/" + p.getUniqueId() + "/");
        File cur = new File("plugins/gFeatures/Resolver/" + p.getUniqueId() + "/current.txt");
        File pre = new File("plugins/gFeatures/Resolver/" + p.getUniqueId() + "/previous.txt");

        // Check if files exist
        if (!f.isDirectory()) {
            f.mkdir();
        }
        if (!cur.exists()) {
            ResolverFetcher.nameToUUID.put(p.getUsername(), p.getUniqueId().toString());
            if (ResolverFetcher.uuidToNames.get(p.getUniqueId().toString()) == null) {
                ResolverFetcher.uuidToNames.put(p.getUniqueId().toString(), new ArrayList<>(Arrays.asList(p.getUsername())));
            } else {
                ResolverFetcher.uuidToNames.get(p.getUniqueId().toString()).add(0, p.getUsername());
            }
            try {
                cur.createNewFile();
                PrintWriter pw = new PrintWriter(cur);
                pw.write(p.getUsername());
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!pre.exists()) {
            try {
                pre.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Check if name has changed
        try {
            FileReader fr = new FileReader(cur);
            BufferedReader br = new BufferedReader(fr);
            String status = br.readLine();
            if (status != null) {
                if (!status.equals(p.getUsername())) {
                    ResolverFetcher.nameToUUID.put(p.getUsername(), p.getUniqueId().toString());
                    if (ResolverFetcher.uuidToNames.get(p.getUniqueId().toString()) == null) {
                        ResolverFetcher.uuidToNames.put(p.getUniqueId().toString(), new ArrayList<>(Arrays.asList(p.getUsername())));
                    } else {
                        ResolverFetcher.uuidToNames.get(p.getUniqueId().toString()).add(0, p.getUsername());
                    }
                    new Thread(() -> {
                        try {
                            cur.delete();
                            cur.createNewFile();
                            PrintWriter pw = new PrintWriter(cur);
                            pw.write(p.getUsername());
                            pw.close();
                            PrintWriter pws = new PrintWriter(pre);
                            pws.write(status + "\n");
                            pws.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } else {
                cur.delete();
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
