package net.estinet.gFeatures.Feature.FusionPlay;

public enum FusionStatus {
	NOTASSIGNED, WAITING, OFFLINE, INGAME;
	
	//WAITING : Waiting for players and ready (server is assigned to id)
	//OFFLINE : Currently offline
	//INGAME : Currently ingame on this server
	//NOTASSIGNED : Online but in queue
}
