package com.maina.formdata.enums;

public enum DformItemType {

	Text(1),
	DropdownList(2),
	MultiChoice(3);

	public int value; 
	private DformItemType(int value){
		this.value = value;
	}
}
