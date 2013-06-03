package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.safapp.dto.AccountDTO;
@DatabaseTable
public class Account extends BaseEntity {
	
	public Account() {
	}

	public Account(UUID id) {
		super(id);
	}

	public Account(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name, Country country) {
		super(id, createdOn, updatedOn, isActive);
		Name = name;
		Country = country;
	}
	@DatabaseField
	private String Name;
	@DatabaseField(foreign = true)
	private Country Country;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	public Country getCountry() {
		return Country;
	}

	public void setCountry(Country country) {
		Country = country;
	}

	public AccountDTO toDTO(){
		return new AccountDTO(getId(), isIsActive(), Name, Country.getId());
	}
}
