package com.hwan.commuteProgram.service;

import java.io.Serializable;

public class CommuteVO extends MemberVO {
	
	//private static final long serialVersionUID = 46011L;
	
	private String date;

	private String inTime;
	private String outTime;
	private String totalTime;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public CommuteVO() {};
	
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
}