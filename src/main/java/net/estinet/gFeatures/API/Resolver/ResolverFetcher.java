package net.estinet.gFeatures.API.Resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

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

public class ResolverFetcher {
    public static HashMap<String, ArrayList<String>> uuidToNames = new HashMap<>(); // UUID to Name
    public static TreeMap<String, String> nameToUUID = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static String getUUIDfromName(String name) {
        return nameToUUID.get(name);
    }

    public static String getNamefromUUID(String uuid) {
        List<String> name = uuidToNames.get(uuid);
        if (name != null) {
            return name.get(0);
        }
        return null;
    }

    public static List<String> getAllNames(String uuid) {
        return uuidToNames.get(uuid);
    }
}
