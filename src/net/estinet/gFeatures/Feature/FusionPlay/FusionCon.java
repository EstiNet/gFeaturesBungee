package net.estinet.gFeatures.Feature.FusionPlay;

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

public class FusionCon {
	private String curType = "";
	private String clioteName = "";
	private int id = 0;
	private FusionStatus status = FusionStatus.OFFLINE;
	public FusionCon(String clioteName){
		this.clioteName = clioteName;
	}
	public FusionCon(String clioteName, int id){
		this.clioteName = clioteName;
		this.id = id;
	}
	public FusionStatus getStatus(){
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
	public void setStatus(FusionStatus status){
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
