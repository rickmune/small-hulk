package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.safapp.dto.AccountDTO;

public class Account extends BaseEntity {
	
	public Account() {
	}

	public Account(UUID id, Date createdOn, Date updatedOn, boolean isActive) {
		super(id, createdOn, updatedOn, isActive);
		// TODO Auto-generated constructor stub
	}

	public Account(UUID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

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
	
	public AccountDTO toDTO(){
		return new AccountDTO(getId(), isIsActive(), Name);
	}
}
