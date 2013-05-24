package com.safapp.utils;

import java.util.List;

import com.safapp.dto.BaseDTO;

public class SyncEntity <T extends BaseDTO>{
	
	public SyncEntity(boolean status, String info, List<T> t) {
		super();
		Status = status;
		Info = info;
		this.t = t;
	}
	private boolean Status;
	private String Info;
	private List<T> t;
	
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public List<T> getT() {
		return t;
	}
	public void setT(List<T> t) {
		this.t = t;
	}
	
}
