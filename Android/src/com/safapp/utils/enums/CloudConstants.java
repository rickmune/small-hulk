package com.safapp.utils.enums;

public enum CloudConstants {

	HTTPUTILS("CloudConstants.HTTPUTILS"),
	DATABASEMANAGER("CloudConstants.DATABASEMANAGER");
	
	public final String value;

	private  CloudConstants(String value) {
		this.value = value;
	}
}
