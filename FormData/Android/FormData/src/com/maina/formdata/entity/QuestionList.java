package com.maina.formdata.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class QuestionList extends DBaseE {
		
	public QuestionList() {
	}
	
	@DatabaseField
	private int qId;
	@DatabaseField
	private String Name;
	
	public int getqId() {
		return qId;
	}
	public void setqId(int qId) {
		this.qId = qId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
}
