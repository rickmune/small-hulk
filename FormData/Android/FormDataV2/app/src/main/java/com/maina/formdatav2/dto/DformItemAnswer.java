package com.maina.formdatav2.dto;

import java.util.UUID;

public class DformItemAnswer extends DBase {

	public DformItemAnswer() {
	}
	
	public DformItemAnswer(UUID id, String text, String value) {
		super(id);
		Text = text;
		Value = value;
	}

	private String Text;
	private String Value;
	
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
}
