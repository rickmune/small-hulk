package com.safapp.dto;

import java.util.Date;
import java.util.UUID;

import com.safapp.entities.Country;

public class CountryDTO extends BaseDTO {

	public CountryDTO(UUID id, boolean isActive, String code, String zipCode,
			String name) {
		super(id, isActive);
		Code = code;
		ZipCode = zipCode;
		Name = name;
	}

	private String Code;
	private String ZipCode;
	private String Name;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	public Country Map(){
		Date date = new Date();
		return new Country(getId(), date, date, isIsActive(), Name, Code, ZipCode);
	}
}
