package com.maina.formdata.dto;

import java.util.UUID;

public class DClient extends DBase{
	
	public DClient() {
	}

	public DClient(UUID id, String name) {
		super(id);
		Name = name;
	}

	private String Name;
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
}
