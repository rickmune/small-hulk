package com.maina.formdatav2.Utils;

import com.maina.formdatav2.dto.BasicResponse;
import com.maina.formdatav2.dto.DBase;

import java.util.List;

public class SyncEntity <T extends DBase> extends BasicResponse {
	
	
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
