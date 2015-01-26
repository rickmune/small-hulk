package com.maina.formdata.utils;

public enum CloudConstants {

	DATABASEMANAGER("CloudConstants.DATABASEMANAGER"),
	LOGINERROR("CloudConstants.LOGINERROR"),
    DFORMITEM("CloudConstants.DFORMITEM"),
    DFORMRESULTE("CloudConstants.DFORMRESULTE"),
    RESULTITEME("CloudConstants.RESULTITEME"),
    DFORMITEME("CloudConstants.DFORMITEME"),
    SUMMARY("CloudConstants.SUMMARY");
	
	public final String value;

	private  CloudConstants(String value) {
		this.value = value;
	}
}
