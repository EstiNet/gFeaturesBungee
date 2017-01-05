package net.estinet.gFeatures.Feature.EstiBans;

import net.estinet.gFeatures.Configuration.Config;

/*
gFeatures
https://github.com/EstiNet/gFeaturesBungee

   Copyright 2017 EstiNet

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

public class ConfigHub {
	
	/*
	 * Layout of player data.
	 * Line 1: bansiqs [if banned (true/false)] [milliseconds to unban] [server] [reason]
	 * Line 2: mutesiqs [if muted (true/false)] [milliseconds to unmute] [server] [reason]
	 * Line 3+: warniqs[num] [milliseconds to unwarn] [reason]
	 */
	
	Config config = new Config();
	public void setupConfig(){
		config.createDirectory("plugins/gFeatures/EstiBans", "[EstiBans] Plugin directory set!");
		config.createDirectory("plugins/gFeatures/EstiBans/playerdata", "[EstiBans] Playerdata directory set!");
	}
}