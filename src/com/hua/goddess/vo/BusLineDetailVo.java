package com.hua.goddess.vo;

import java.util.ArrayList;

public class BusLineDetailVo {
	private String LGUID;
	private String LName;
	private String LDirection;
	private ArrayList<StandInfoVo> StandInfo;
	public String getLGUID() {
		return LGUID;
	}
	public void setLGUID(String lGUID) {
		LGUID = lGUID;
	}
	public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}
	public String getLDirection() {
		return LDirection;
	}
	public void setLDirection(String lDirection) {
		LDirection = lDirection;
	}
	public ArrayList<StandInfoVo> getStandInfo() {
		return StandInfo;
	}
	public void setStandInfo(ArrayList<StandInfoVo> standInfo) {
		StandInfo = standInfo;
	}
	
}
