package com.maina.formdatav2.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DClientId extends DBaseE {

	public DClientId() {
	}

	public DClientId(UUID id, String name) {
		super(id);
		Name = name;
	}
	
	@DatabaseField
	private String Name;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
}
