package com.maina.formdata.utils;

import java.util.List;

import com.maina.formdata.dto.BasicResponse;
import com.maina.formdata.dto.DBase;

public class SyncEntity <T extends DBase> extends BasicResponse{
	
	
	public SyncEntity() {
	}
	
	public SyncEntity(boolean status, String info, List<T> t, int recordCount) {
		super(info, status);
		Data = t;
		RecordCount = recordCount;
	}
	
	private List<T> Data;
	private int RecordCount;
	
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	
	public boolean getStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	
	public List<T> getData() {
		return Data;
	}
	public void setData(List<T> t) {
		this.Data = t;
	}
	public int getRecordCount() {
		return RecordCount;
	}
	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
	
}
