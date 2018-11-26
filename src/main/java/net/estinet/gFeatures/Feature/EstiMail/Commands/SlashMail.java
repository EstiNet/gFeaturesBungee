package net.estinet.gFeatures.Feature.EstiMail.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.estinet.gFeatures.API.Resolver.ResolverFetcher;
import net.estinet.gFeatures.EstiCommand;
import net.estinet.gFeatures.Feature.EstiMail.EstiMail;
import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.gFeatures;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;

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

public class SlashMail extends EstiCommand{
	public SlashMail(gFeature feature){
		super(new String[]{"mail"}, "basic", feature);
	}

	@Override
	public void execute(CommandSource sender, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ComponentSerializers.LEGACY.deserialize("&3&l---EstiMail Help---", '&'));
					sender.sendMessage(ComponentSerializers.LEGACY.deserialize("&6/mail read : Displays your inbox.", '&'));
					sender.sendMessage(ComponentSerializers.LEGACY.deserialize("&6/mail send [Player] [Message] : Sends a player an email.", '&'));
					sender.sendMessage(ComponentSerializers.LEGACY.deserialize("&6/mail clear : Clears your inbox.", '&'));
				}
				else if(args[0].equalsIgnoreCase("read")){
					EstiMail.getMail(player);
				}
				else if(args[0].equalsIgnoreCase("send")){
					sender.sendMessage(EstiMail.prefix.append(TextComponent.of("/mail send [Player] [Message].")));
				}
				else if(args[0].equalsIgnoreCase("clear")){
					EstiMail.clearMail(player);
					sender.sendMessage(EstiMail.prefix.append(TextComponent.of("Mail cleared!", TextColor.DARK_AQUA)));
				}
				else{
					sender.sendMessage(EstiMail.prefix.append(TextComponent.of( "Do /mail help.")));
				}
			}
			else if(args.length == 2){
				if(args[0].equalsIgnoreCase("send")){
					sender.sendMessage(EstiMail.prefix.append(TextComponent.of("/mail send [Player] [Message].")));
				}
				else{
					sender.sendMessage(EstiMail.prefix.append(TextComponent.of("Do /mail help.")));
				}
			}
			else if(args.length >= 3){
				if(args[0].equalsIgnoreCase("send")){
					gFeatures.getInstance().getProxyServer().getScheduler().buildTask(gFeatures.getInstance(), () -> {
						String uuid = "";
						try {
							uuid = ResolverFetcher.getUUIDfromName(args[1]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(!EstiMail.checkExists(uuid)){
							sender.sendMessage(EstiMail.prefix.append(TextComponent.of("Player not found.", TextColor.RED)));
						}
						else{
							StringBuilder message = new StringBuilder();
							for(int i = 2; i != args.length; i++){
								message.append(args[i]).append(" ");
							}
							EstiMail.sendMail(player.getUsername(), uuid, message.toString());
							sender.sendMessage(EstiMail.prefix.append(TextComponent.of("Mail sent!", TextColor.DARK_AQUA)));
						}
					}).schedule();
				}
				else{
					sender.sendMessage(EstiMail.prefix.append(TextComponent.of("Do /mail help.")));
				}
			}
			else{
				sender.sendMessage(EstiMail.prefix.append(TextComponent.of("Do /mail help.")));
			}
		}
		else{
			sender.sendMessage(TextComponent.of("This command can only be run by a player!", TextColor.AQUA));
		}
	}
}
