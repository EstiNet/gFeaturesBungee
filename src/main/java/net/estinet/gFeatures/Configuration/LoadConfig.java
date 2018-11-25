package net.estinet.gFeatures.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;

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

public class LoadConfig {
    static List<gFeature> features = gFeatures.getFeatures();

    public static void load() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("plugins/gFeatures/Config.yml");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            try {
                List<gFeature> featur = new ArrayList<>();
                for (gFeature feature : features) {
                    if ((prop.getProperty("Plugins." + feature.getName()).equals("true"))) {
                        feature.setEnabled(true);
                    } else {
                        feature.setEnabled(false);
                    }
                    featur.add(feature);
                }
                gFeatures.setFeatures(featur);
            } catch (Exception e) {
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

    }
}
