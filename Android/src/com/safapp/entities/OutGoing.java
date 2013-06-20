package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class OutGoing extends BaseEntity{

	
	public OutGoing() {
	}

	public OutGoing(UUID id, Date createdOn, Date updatedOn, boolean isActive) {
		super(id, createdOn, updatedOn, isActive);
	}

	public OutGoing(UUID id) {
		super(id);
	}

	public OutGoing(UUID id, String json, String controlIp) {
		super(id);
		this.json = json;
		this.controlIp = controlIp;
	}
	@DatabaseField
	private String json;
	@DatabaseField
	private String controlIp;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getControlIp() {
		return controlIp;
	}

	public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	}
	
}
