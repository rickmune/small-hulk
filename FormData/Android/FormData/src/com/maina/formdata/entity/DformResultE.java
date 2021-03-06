package com.maina.formdata.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class DformResultE extends DBaseE {
	
	public DformResultE() {
	}
	
	public DformResultE(UUID id, UUID respondentTypeId, UUID formId,
			List<DformResultItemE> formResultItem, boolean done, boolean sent,
			String username, UUID locationId, Date resultDateTime, String appVersion) {
		super(id);
		RespondentTypeId = respondentTypeId;
		FormId = formId;
		FormResultItem = formResultItem;
		Done = done;
		Sent = sent;
		Username = username;
		LocationId = locationId;
		ResultDateTime = resultDateTime;
		AppVersion = appVersion;
	}
	@DatabaseField
	private UUID RespondentTypeId;
	@DatabaseField
	private UUID FormId;
	private List<DformResultItemE> FormResultItem;
	@DatabaseField
	private boolean Done = false;
	@DatabaseField
	private boolean Sent = false;
	@DatabaseField
	private double Longitude;
	@DatabaseField
    private double Latitude;
	@DatabaseField
	private String Username;
	@DatabaseField
	private UUID LocationId;
	@DatabaseField
	private Date ResultDateTime;
	@DatabaseField
	private String AppVersion;
	
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

	public void addFormResultItem(DformResultItemE formResultItem) {
		if(FormResultItem == null)FormResultItem = new ArrayList<DformResultItemE>();
		FormResultItem.add(formResultItem);
	}

	public boolean isDone() {
		return Done;
	}

	public void setDone(boolean done) {
		Done = done;
	}

	public boolean isSent() {
		return Sent;
	}

	public void setSent(boolean sent) {
		Sent = sent;
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

	public UUID getLocationId() {
		return LocationId;
	}

	public void setLocationId(UUID locationId) {
		this.LocationId = locationId;
	}

	public Date getResultDateTime() {
		return ResultDateTime;
	}

	public void setResultDateTime(Date resultDateTime) {
		ResultDateTime = resultDateTime;
	}

	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}
}
