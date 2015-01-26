package com.maina.formdatav2.dto;

import java.util.UUID;

public class UserDto extends DBase{
	

	public UserDto() {
	}

	public UserDto(UUID id, boolean isActive, String username, String password,
			String fullname, int userType, String email, String phoneNumber,
			UUID clientId, UUID locationId, String clientName, boolean isPasswordChanged,
			boolean isSecuritySet, int securityQuestionId, String securityAnswer) {
		super(id);
		Username = username;
		Password = password;
		Fullname = fullname;
		UserTypeId = userType;
		Email = email;
		PhoneNumber = phoneNumber;
		ClientId = clientId;
		LocationId = locationId;
		ClientName = clientName;
		IsPasswordChanged = isPasswordChanged;
		IsSecuritySet = isSecuritySet;
		SecurityQuestionId = securityQuestionId;
		SecurityAnswer = securityAnswer;
	}

	private String Username;
	private String Password;
	private String Fullname;
	private int UserTypeId;
	private String Email;
	private String PhoneNumber;
	private UUID ClientId;
	private UUID LocationId;
	private String ClientName;
	private boolean IsPasswordChanged;
	private boolean IsSecuritySet;
	private int SecurityQuestionId;
	private String SecurityAnswer;

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setFullname(String fullname) {
		Fullname = fullname;
	}

	public int getUserType() {
		return UserTypeId;
	}

	public void setUserType(int userType) {
		UserTypeId = userType;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public UUID getClientId() {
		return ClientId;
	}

	public void setClientId(UUID clientId) {
		ClientId = clientId;
	}

	public UUID getLocationId() {
		return LocationId;
	}

	public void setLocationId(UUID locationId) {
		LocationId = locationId;
	}

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public boolean isIsPasswordChanged() {
		return IsPasswordChanged;
	}

	public void setIsPasswordChanged(boolean isPasswordChanged) {
		IsPasswordChanged = isPasswordChanged;
	}

	public boolean isIsSecuritySet() {
		return IsSecuritySet;
	}

	public void setIsSecuritySet(boolean isSecuritySet) {
		IsSecuritySet = isSecuritySet;
	}

	public int getSecurityQuestionId() {
		return SecurityQuestionId;
	}

	public void setSecurityQuestionId(int securityQuestionId) {
		SecurityQuestionId = securityQuestionId;
	}

	public String getSecurityAnswer() {
		return SecurityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		SecurityAnswer = securityAnswer;
	}

	@Override
	public String toString() {
		return "UserDto [Username=" + Username + ", Password=" + Password
				+ ", Fullname=" + Fullname + ", UserTypeId=" + UserTypeId
				+ ", Email=" + Email + ", PhoneNumber=" + PhoneNumber
				+ ", ClientId=" + ClientId + ", LocationId=" + LocationId
				+ ", ClientName=" + ClientName + ", IsPasswordChanged="
				+ IsPasswordChanged + ", IsSecuritySet=" + IsSecuritySet
				+ ", SecurityQuestionId=" + SecurityQuestionId
				+ ", SecurityAnswer=" + SecurityAnswer + "]";
	}	
	
}