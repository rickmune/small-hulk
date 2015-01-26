package com.maina.formdatav2.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;

public class DBaseE {

	
	public DBaseE() {
	}

	public DBaseE(UUID id) {
		Id = id;
	}
	
	@DatabaseField(id = true, index = true)
	private UUID Id;

	public UUID getId() {
		return Id;
	}

	public void setId(UUID id) {
		Id = id;
	}
	
}
