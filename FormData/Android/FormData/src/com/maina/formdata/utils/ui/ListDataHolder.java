package com.maina.formdata.utils.ui;

import java.util.UUID;

public class ListDataHolder {

	public ListDataHolder(UUID _id, String value) {
		Value = value;
		this._id = _id;
	}
	
	public String Value;
	public UUID _id;
}
