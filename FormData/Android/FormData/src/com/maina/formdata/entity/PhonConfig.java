package com.maina.formdata.entity;

import com.j256.ormlite.field.DatabaseField;

public class PhonConfig extends DBaseE {

	public PhonConfig() {
	}

	@DatabaseField
	private String URL;
	
	public String getURL() {
		return URL;
	}
	
	public void setURL(String uRL) {
		URL = uRL;
	}
	
}
