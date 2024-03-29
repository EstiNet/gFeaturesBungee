package net.estinet.gFeatures.Feature.SlashServer.Commands;

import com.velocitypowered.api.command.CommandSource;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Feature.SlashServer.SlashServer;
import net.estinet.gFeatures.gFeature;

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

public class SlashCreative extends EstiCommand{
	public SlashCreative(gFeature feature){
		super(new String[]{"creative"}, "basic", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		SlashServer.connectToServer(sender, "Creative");
	}
}
