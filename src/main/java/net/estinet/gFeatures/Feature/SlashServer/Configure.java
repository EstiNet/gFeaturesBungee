package net.estinet.gFeatures.Feature.SlashServer;

import net.estinet.gFeatures.Configs;
/*
gFeatures
https://github.com/EstiNet/gFeatures

   Copyright 2016 EstiNet

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
import net.estinet.gFeatures.Feature.SlashServer.Commands.*;
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

public class Configure{
	@Configs
	public static void onSetup(){
		SlashServer base = new SlashServer("SlashServer", "1.0.0");
		gFeatures.addFeature(base);
		
		gFeatures.addCommand(new SlashCreative(base));
		gFeatures.addCommand(new SlashFactions(base));
		gFeatures.addCommand(new SlashgWars(base));
		gFeatures.addCommand(new SlashHub(base));
		gFeatures.addCommand(new SlashSkyblock(base));
		//gFeatures.addCommand(new SlashSurvival(base));
	}
}
