package com.maina.formdata.utils;

public enum CloudConstants {

	DATABASEMANAGER("CloudConstants.DATABASEMANAGER"),
	LOGINERROR("CloudConstants.LOGINERROR");
	
	public final String value;

	private  CloudConstants(String value) {
		this.value = value;
	}
}
