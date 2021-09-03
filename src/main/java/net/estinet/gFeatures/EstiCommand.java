package net.estinet.gFeatures;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;

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

public abstract class EstiCommand implements SimpleCommand {
	
	public gFeature feature;
	public String permission;
	public String[] names;

	public EstiCommand(String[] names, String permission, gFeature feature) {
		this.names = names;
		this.permission = permission;
		this.feature = feature;
	}

	@Override
	public void execute(Invocation invocation) {
		if (this.permission.equals("basic") || invocation.source().hasPermission(this.permission))
			execute(invocation.source(), invocation.arguments());
	}
	public abstract void execute(CommandSource source, String[] args);

}
