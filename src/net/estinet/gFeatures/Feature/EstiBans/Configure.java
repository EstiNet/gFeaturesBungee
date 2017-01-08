package net.estinet.gFeatures.Feature.EstiBans;

import net.estinet.gFeatures.Basic;
import net.estinet.gFeatures.Configs;
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
import net.estinet.gFeatures.Feature.EstiBans.Commands.BanCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.EstiBansCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.KickCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.MuteCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.TempBanCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.TempMuteCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.UnbanCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.UnmuteCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.UnwarnCommand;
import net.estinet.gFeatures.Feature.EstiBans.Commands.WarnCommand;

public class Configure{
	@Configs
	public static void onSetup(){
		EstiBans base = new EstiBans("EstiBans", "1.0.0");
		Basic.addFeature(base);
		
		Basic.addCommand(new BanCommand(base));
		Basic.addCommand(new KickCommand(base));
		Basic.addCommand(new MuteCommand(base));
		Basic.addCommand(new UnbanCommand(base));
		Basic.addCommand(new UnmuteCommand(base));
		Basic.addCommand(new EstiBansCommand(base));
		Basic.addCommand(new TempBanCommand(base));
		Basic.addCommand(new TempMuteCommand(base));
		Basic.addCommand(new UnwarnCommand(base));
		Basic.addCommand(new WarnCommand(base));
	}
}
