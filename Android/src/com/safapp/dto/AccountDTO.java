package com.safapp.dto;

import java.util.UUID;

public class AccountDTO extends BaseDTO {

	public AccountDTO(UUID id, boolean isActive, String name, UUID countryId) {
		super(id, isActive);
		Name = name;
		CountryId = countryId;
	}

	private String Name;
	private UUID CountryId;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public UUID getCountryId() {
		return CountryId;
	}

	public void setCountryId(UUID countryId) {
		CountryId = countryId;
	}
	
}
