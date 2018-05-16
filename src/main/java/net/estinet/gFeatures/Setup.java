package net.estinet.gFeatures;

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

public class Setup {
	public void onSetup() {
		//Well, unfortunate part of the API :(

		/*
		 * Initialize your plugins here.
		 * Make sure that you have your onSetup() setup
		 * to add to the Basic class. :D
		 */

		net.estinet.gFeatures.Feature.Base.Configure.onSetup();
		net.estinet.gFeatures.Feature.ServerRedirect.Configure.onSetup();
		net.estinet.gFeatures.Feature.SlashServer.Configure.onSetup();
		net.estinet.gFeatures.Feature.Alerts.Configure.onSetup();
		net.estinet.gFeatures.Feature.MinigameAssister.Configure.onSetup();
		net.estinet.gFeatures.Feature.EstiChat.Configure.onSetup();
		net.estinet.gFeatures.Feature.EstiMail.Configure.onSetup();
		net.estinet.gFeatures.Feature.Friendship.Configure.onSetup();
		net.estinet.gFeatures.Feature.ServerQuery.Configure.onSetup();
		net.estinet.gFeatures.Feature.FusionPlay.Configure.onSetup();
		net.estinet.gFeatures.Feature.EstiBans.Configure.onSetup();
		net.estinet.gFeatures.Feature.BList.Configure.onSetup();
		net.estinet.gFeatures.Feature.ServerManager.Configure.onSetup();
	}
}
