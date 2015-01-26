package com.maina.formdatav2.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class DformRespondentTypeE extends DBaseE {

	public DformRespondentTypeE() {
	}

	public DformRespondentTypeE(UUID id, String name, String code, DformE dformE) {
		super(id);
		Name = name;
		Code = code;
		DformE = dformE;
	}
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Code;
	@DatabaseField(foreign = true)
	private DformE DformE;
	
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
	
}
