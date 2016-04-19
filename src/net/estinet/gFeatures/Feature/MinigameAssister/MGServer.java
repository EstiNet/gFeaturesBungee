package net.estinet.gFeatures.Feature.MinigameAssister;

public class MGServer {
	private String name, category;
	public MGServer(String name, String category){
		this.name = name;
		this.category = category;
	}
	public String getName(){
		return name;
	}
	public String getCategory(){
		return category;
	}
}
