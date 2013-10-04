package com.maina.formdata.dto;

import java.util.List;
import java.util.UUID;

import com.maina.formdata.entity.DformResultItemE;

public class DformResult extends DBase {

	public DformResult() {
	}
	
	public DformResult(UUID id, UUID respondentTypeId, UUID formId,
			List<DformResultItemE> formResultItem, double longitude,
    		double latitude) {
		super(id);
		RespondentTypeId = respondentTypeId;
		FormId = formId;
		FormResultItem = formResultItem;
	}
	
	private UUID RespondentTypeId;
	private UUID FormId;
	private double Longitude;
    private double Latitude;
    private String Username;
	
	private List<DformResultItemE> FormResultItem;
	
	public UUID getRespondentTypeId() {
		return RespondentTypeId;
	}

	public void setRespondentTypeId(UUID respondentTypeId) {
		RespondentTypeId = respondentTypeId;
	}

	public UUID getFormId() {
		return FormId;
	}

	public void setFormId(UUID formId) {
		FormId = formId;
	}

	public List<DformResultItemE> getFormResultItem() {
		return FormResultItem;
	}

	public void setFormResultItem(List<DformResultItemE> formResultItem) {
		FormResultItem = formResultItem;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}
}
