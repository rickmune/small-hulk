package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Country extends BaseEntity{

	public Country(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name, String code, String zipCode) {
		super(id, createdOn, updatedOn, isActive);
		Name = name;
		Code = code;
		ZipCode = zipCode;
	}

	public Country() {
	}
	
	public Country(UUID id) {
		super(id);
	}
	
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Code;
	@DatabaseField
	private String ZipCode;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
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
}
