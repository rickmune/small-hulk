package com.maina.formdata.dto;

import java.util.List;
import java.util.UUID;

public class DformResultItem extends DBase {

	public DformResultItem() {
	}
	
	public DformResultItem(UUID id, UUID formItemId, List<String> formItemAnswer) {
		super(id);
		FormItemId = formItemId;
		FormItemAnswer = formItemAnswer;
	}
	
	private UUID FormItemId;
	private List<String> FormItemAnswer;
	
	public UUID getFormItemId() {
		return FormItemId;
	}
	public void setFormItemId(UUID formItemId) {
		FormItemId = formItemId;
	}
	public List<String> getFormItemAnswer() {
		return FormItemAnswer;
	}
	public void setFormItemAnswer(List<String> formItemAnswer) {
		FormItemAnswer = formItemAnswer;
	}
}
