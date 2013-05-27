package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Country extends BaseEntity{

	public Country() {
	}
	
	public Country(UUID id) {
		super(id);
	}
	
	public Country(UUID id, Date createdOn, Date updatedOn, boolean isActive) {
		super(id, createdOn, updatedOn, isActive);
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
