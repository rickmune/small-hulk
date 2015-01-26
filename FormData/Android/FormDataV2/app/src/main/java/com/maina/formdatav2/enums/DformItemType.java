package com.maina.formdatav2.enums;

public enum DformItemType {

	Text(1),
	DropdownList(2),
	MultiChoice(3),
    Date(4),
    Image(5);

	public int value;

	private DformItemType(int value){
		this.value = value;
	}
}
