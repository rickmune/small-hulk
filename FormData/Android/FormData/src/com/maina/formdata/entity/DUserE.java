package com.maina.formdata.entity;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.maina.formdata.enums.UserType;

@DatabaseTable
public class DUserE extends DBaseE {
	
	public DUserE() {
	}
	
	public DUserE(UUID id, String username, String password, String fullname,
			UserType userTypeId, String email, String phoneNumber, UUID clientId,
			UUID locationId, String clientName, boolean isPasswordChanged,
			boolean isSecuritySet, int securityQuestionId, String securityAnswer) {
		super(id);
		Username = username;
		Password = password;
		Fullname = fullname;
		UserTypeId = userTypeId;
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
	@DatabaseField
	private String Username;
	@DatabaseField
	private String Password;
	@DatabaseField
	private String Fullname;
	@DatabaseField
	private UserType UserTypeId;
	@DatabaseField
	private String Email;
	@DatabaseField
	private String PhoneNumber;
	@DatabaseField
	private UUID ClientId;
	@DatabaseField
	private UUID LocationId;
	@DatabaseField
	private String ClientName;
	@DatabaseField
	private boolean IsPasswordChanged;
	@DatabaseField
	private boolean IsSecuritySet;
	@DatabaseField
	private int SecurityQuestionId;
	@DatabaseField
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
	public UserType getUserTypeId() {
		return UserTypeId;
	}
	public void setUserTypeId(UserType userTypeId) {
		UserTypeId = userTypeId;
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
		return "DUserE [Username=" + Username + ", Password=" + Password
				+ ", Fullname=" + Fullname + ", UserTypeId=" + UserTypeId
				+ ", Email=" + Email + ", PhoneNumber=" + PhoneNumber
				+ ", ClientId=" + ClientId + ", LocationId=" + LocationId
				+ ", ClientName=" + ClientName + ", IsPasswordChanged="
				+ IsPasswordChanged + ", IsSecuritySet=" + IsSecuritySet
				+ ", SecurityQuestionId=" + SecurityQuestionId
				+ ", SecurityAnswer=" + SecurityAnswer + "]";
	}
	
}
