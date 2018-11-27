package net.estinet.gFeatures.Feature.Friendship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;

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

public class EventHub {
    @Subscribe
    public void onPlayerJoin(PostLoginEvent event) {
        File f = new File("plugins/gFeatures/Friendship/" + event.getPlayer().getUniqueId() + "/");
        if (!f.isDirectory()) {
            f.mkdir();
        }
    }

    @Subscribe
    public void onPlayerDisconnect(DisconnectEvent event) {
        if (!event.getPlayer().getCurrentServer().isPresent()) return;
        File f = new File("plugins/gFeatures/Friendship/" + event.getPlayer().getUniqueId() + "/seen");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter pw = new PrintWriter(f);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            pw.write(dateFormat.format(cal.getTime()) + "\n");
            pw.write(event.getPlayer().getCurrentServer().get().getServerInfo().getName());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
