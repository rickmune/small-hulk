package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

public class BaseEntity {

	public BaseEntity(UUID id, Date createdOn, Date updatedOn, boolean isActive) {
		Id = id;
		CreatedOn = createdOn;
		UpdatedOn = updatedOn;
		IsActive = isActive;
	}
	
	private UUID Id;
	private Date CreatedOn;
	private Date UpdatedOn;
	private boolean IsActive;
	public UUID getId() {
		return Id;
	}
	public void setId(UUID id) {
		Id = id;
	}
	public Date getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(Date createdOn) {
		CreatedOn = createdOn;
	}
	public Date getUpdatedOn() {
		return UpdatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		UpdatedOn = updatedOn;
	}
	public boolean isIsActive() {
		return IsActive;
	}
	public void setIsActive(boolean isActive) {
		IsActive = isActive;
	}	
}
