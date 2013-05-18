package com.safapp.utils.enums;


public enum UserType {

	Phone(1)
	,Email(2);
	
	public final int value;

	private UserType(int value) {
		this.value = value;
	}
}
