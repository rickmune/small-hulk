package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

public class Account extends BaseEntity {
	
	public Account(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name) {
		super(id, createdOn, updatedOn, isActive);
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
