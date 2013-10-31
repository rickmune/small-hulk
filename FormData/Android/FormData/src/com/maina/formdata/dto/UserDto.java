package com.maina.formdata.dto;

import java.util.UUID;

public class UserDto extends DBase {

	public UserDto(UUID id, boolean isActive, String username, String password,
			String fullname, int userType, String email, String phoneNumber,
			UUID clientId, UUID locationId) {
		super(id);
		Username = username;
		Password = password;
		Fullname = fullname;
		UserTypeId = userType;
		Email = email;
		PhoneNumber = phoneNumber;
		ClientId = clientId;
		LocationId = locationId;
	}

	private String Username;
	private String Password;
	private String Fullname;
	private int UserTypeId;
	private String Email;
	private String PhoneNumber;
	private UUID ClientId;
	private UUID LocationId;

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

	@Override
	public String toString() {
		return "UserDto [Username=" + Username + ", Password=" + Password
				+ ", Fullname=" + Fullname + ", UserTypeId=" + UserTypeId
				+ ", Email=" + Email + ", PhoneNumber=" + PhoneNumber
				+ ", ClientId=" + ClientId + ", LocationId=" + LocationId + "]";
	}
	
}
