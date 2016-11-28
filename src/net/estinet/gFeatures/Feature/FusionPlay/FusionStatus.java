package net.estinet.gFeatures.Feature.FusionPlay;

public enum FusionStatus {
	NOTASSIGNED, WAITING, OFFLINE, INGAME, OTHERINGAME;
	
	//WAITING : Waiting for players and ready (both servers in pair)
	//OFFLINE : Currently offline (not assigned to pair)
	//INGAME : Currently ingame on this server (other server is waiting)
	//NOTASSIGNED : Online but not in pair
	//OTHERINGAME : Currently on standby for while other is ingame
}
