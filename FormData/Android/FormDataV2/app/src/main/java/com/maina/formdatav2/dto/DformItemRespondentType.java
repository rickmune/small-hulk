package com.maina.formdatav2.dto;

import java.util.UUID;

public class DformItemRespondentType extends DBase {

	public DformItemRespondentType() {
	}
	
	public DformItemRespondentType(UUID id, UUID respondentTypeId,
			UUID formItemId) {
		super(id);
		RespondentTypeId = respondentTypeId;
		FormItemId = formItemId;
	}

	private UUID RespondentTypeId;
	private UUID FormItemId;
	public UUID getRespondentTypeId() {
		return RespondentTypeId;
	}

	public void setRespondentTypeId(UUID respondentTypeId) {
		RespondentTypeId = respondentTypeId;
	}

	public UUID getFormItemId() {
		return FormItemId;
	}

	public void setFormItemId(UUID formItemId) {
		FormItemId = formItemId;
	}

}
