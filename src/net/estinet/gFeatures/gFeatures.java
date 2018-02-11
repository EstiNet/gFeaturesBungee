package net.estinet.gFeatures;

import net.md_5.bungee.api.plugin.Command;

import java.io.File;
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

public class gFeatures {
    public static File f = new File("plugins/gFeatures/Config.yml");

    static Setup setup = new Setup();

    public static List<gFeature> features = new ArrayList<>();
    private static List<EstiCommand> commands = new ArrayList<>();
    public static void addFeature(gFeature feature){
        features.add(feature);
    }
    public static void addCommand(EstiCommand command){
        commands.add(command);
    }
    public static void removeFeature(gFeature feature){
        features.remove(feature);
    }
    public static void removeCommand(EstiCommand command){
        commands.remove(command);
    }
    public static gFeature getFeature(String name){
        for(gFeature feature : features){
            if(feature.getName().equalsIgnoreCase(name)){
                return feature;
            }
        }
        return null;
    }
    public static Command getCommand(String name){
        for(EstiCommand command : commands){
            if(command.getName().equalsIgnoreCase(name)){
                return command;
            }
        }
        return null;
    }
    public static List<gFeature> getFeatures(){
        return features;
    }
    public static List<EstiCommand> getCommands(){
        return commands;
    }
    public static void setFeatures(List<gFeature> pl){
        features = pl;
    }
    public static void setCommands(List<EstiCommand> ec){
        commands = ec;
    }
    public static void resetFeatures(){
        List<gFeature> feature = new ArrayList<>();
        features = feature;
    }
}
