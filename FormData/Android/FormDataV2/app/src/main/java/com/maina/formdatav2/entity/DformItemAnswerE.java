package com.maina.formdatav2.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class DformItemAnswerE extends DBaseE {
	
	public DformItemAnswerE() {
	}

	public DformItemAnswerE(UUID id, String text, String value,
			DformItemE dformItemE) {
		super(id);
		Text = text;
		Value = value;
		this.dformItemE = dformItemE;
	}
	
	@DatabaseField
	private String Text;
	@DatabaseField
	private String Value;
	@DatabaseField(foreign = true)
	private DformItemE dformItemE;
	
	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public DformItemE getDformItemE() {
		return dformItemE;
	}

	public void setDformItemE(DformItemE dformItemE) {
		this.dformItemE = dformItemE;
	}
	
}
