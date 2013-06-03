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

	public OutGoing(UUID id, String json) {
		super(id);
		this.json = json;
	}
	@DatabaseField
	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
