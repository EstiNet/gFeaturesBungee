package net.estinet.gFeatures.Feature.FusionPlay;

public class FusionCon {
	private String curType = "";
	private String clioteName = "";
	private int id = 0;
	private boolean status = false;
	public FusionCon(String clioteName){
		this.clioteName = clioteName;
	}
	public FusionCon(String clioteName, int id){
		this.clioteName = clioteName;
		this.id = id;
	}
	public boolean isOn(){
		return status;
	}
	public String getCurrentType(){
		return curType;
	}
	public String getClioteName(){
		return clioteName;
	}
	public int getID(){
		return id;
	}
	public void setStatus(boolean status){
		this.status = status;
	}
	public void setID(int id){
		this.id = id;
	}
	public void setClioteName(String clioteName){
		this.clioteName = clioteName;
	}
	public void setCurrentType(String curType){
		this.curType = curType;
	}
}
