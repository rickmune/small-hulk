package com.maina.formdata.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class DformItemRespondentTypeE extends DBaseE {

	public DformItemRespondentTypeE() {
	}
	
	public DformItemRespondentTypeE(UUID id, UUID respondentTypeId, UUID formItemId) {
		super(id);
		RespondentTypeId = respondentTypeId;
		FormItemId = formItemId;
	}
	
	@DatabaseField
	private UUID RespondentTypeId;
	@DatabaseField
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
