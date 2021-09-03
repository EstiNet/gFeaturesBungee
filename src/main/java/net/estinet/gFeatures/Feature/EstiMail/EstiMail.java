package net.estinet.gFeatures.Feature.EstiMail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

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

public class EstiMail extends gFeature {

    public static Component prefix = Component.text("[").decoration(TextDecoration.BOLD, true).append(
            Component.text("EstiMail", NamedTextColor.AQUA).decoration(TextDecoration.BOLD, true).append(
                    Component.text("] ", NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true)
            )
    );

    public EstiMail(String featurename, String d) {
        super(featurename, d);
    }

    @Override
    public void enable() {
        Enable.onEnable();
    }

    @Override
    public void disable() {
        Disable.onDisable();
    }

    @SuppressWarnings("deprecation")
    public static void sendMail(String senderName, String recieverUUID, String mail) {
        File f = new File("plugins/gFeatures/EstiMail/" + recieverUUID);
        if (!f.isDirectory()) {
            f.mkdir();
        }
        File fs = new File("plugins/gFeatures/EstiMail/" + recieverUUID + "/" + (int) Math.floor(Math.random() * 10000));
        if (fs.exists()) {
            sendMail(senderName, recieverUUID, mail);
        } else {
            try {
                fs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PrintWriter pw = new PrintWriter(fs);
                pw.write(senderName + "\r\n");
                pw.write(mail);
                pw.close();
                if (gFeatures.getInstance().getProxyServer().getPlayer(recieverUUID).isPresent()) {
                    gFeatures.getInstance().getProxyServer().getPlayer(recieverUUID).get().sendMessage(prefix.append(Component.text("You have new mail! Do /mail read to check!")));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void getMail(Player receiver) {
        File f = new File("plugins/gFeatures/EstiMail/" + receiver.getUniqueId().toString());
        File[] array = f.listFiles();
        String line;
        boolean hasmail = false;
        String name = "";
        if (array.length > 0)
            receiver.sendMessage(prefix.append(Component.text( "You have new mail!")));
        for (File fs : array) {
            try {
                FileReader fr = new FileReader(fs);
                BufferedReader br = new BufferedReader(fr);
                int linenum = 1;
                while ((line = br.readLine()) != null) {
                    if (linenum == 1) {
                        name = line;
                    } else if (linenum == 2) {
                        receiver.sendMessage(prefix.append(Component.text("" + name + ": " + line)));
                        hasmail = true;
                    }
                    linenum++;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!hasmail) {
            receiver.sendMessage(prefix.append(Component.text("You have no new mail.")));
        }
    }

    public static void clearMail(Player reciever) {
        File f = new File("plugins/gFeatures/EstiMail/" + reciever.getUniqueId().toString());
        for (File fs : f.listFiles()) {
            fs.delete();
        }
    }

    public static boolean checkExists(String uuid) {
        File f = new File("plugins/gFeatures/EstiMail/" + uuid);
        return f.isDirectory();
    }
}
