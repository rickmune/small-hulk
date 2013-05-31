package com.safapp.dto;

import java.util.UUID;

public class AccountDTO extends BaseDTO {

	public AccountDTO(UUID id, boolean isActive, String name) {
		super(id, isActive);
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
