package com.maina.formdatav2.dto;

import java.util.UUID;

public class DformRespondentType extends DBase {

	public DformRespondentType(UUID id, String name, String code) {
		super(id);
		Name = name;
		Code = code;
	}

	private String Name;
	private String Code;

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
