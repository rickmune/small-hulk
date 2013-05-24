package com.safapp.dto;

import java.util.UUID;

public class BaseDTO {
	
	public BaseDTO(UUID id, boolean isActive) {
		super();
		Id = id;
		IsActive = isActive;
	}
	private UUID Id;
	private boolean IsActive;
	
	public UUID getId() {
		return Id;
	}
	public void setId(UUID id) {
		Id = id;
	}
	public boolean isIsActive() {
		return IsActive;
	}
	public void setIsActive(boolean isActive) {
		IsActive = isActive;
	}
	
}
