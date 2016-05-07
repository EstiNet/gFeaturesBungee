package net.estinet.gFeatures.Feature.EstiChat;

import java.util.List;

import net.estinet.gFeatures.gFeature;
import net.estinet.gFeatures.ClioteSky.API.ClioteHook;

public class ChatClioteHook extends ClioteHook{

	public ChatClioteHook(gFeature feature) {
		super(feature, "chat");
	}
	@Override
	public void run(List<String> args, String categoryName, String clioteName){
		try{
			String mgs = "";
			for(String arg : args){
				mgs += arg + " ";
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
