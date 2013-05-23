package com.safapp.utils;

import com.safapp.entities.BaseEntity;

public class SyncEntity <T extends BaseEntity>{
	
	public SyncEntity(boolean status, String info, T t) {
		super();
		Status = status;
		Info = info;
		this.t = t;
	}
	private boolean Status;
	private String Info;
	private T t;
	
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
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
}
