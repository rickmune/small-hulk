package com.safapp.dto;

import java.util.UUID;

import com.safapp.utils.enums.UserType;

public class UserDto extends BaseDTO{

	public UserDto(UUID id, boolean isActive, String username, String password,
			String fullname, com.safapp.utils.enums.UserType userType,
			String email, String phoneNumber, UUID countryId) {
		super(id, isActive);
		Username = username;
		Password = password;
		Fullname = fullname;
		UserType = userType;
		Email = email;
		PhoneNumber = phoneNumber;
		CountryId = countryId;
	}
	
	private String Username;
	private String Password;
	private String Fullname;
	private UserType UserType;
	private String Email;
	private String PhoneNumber;
	private UUID CountryId;
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
	public UserType getUserType() {
		return UserType;
	}
	public void setUserType(UserType userType) {
		UserType = userType;
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
	public UUID getCountryId() {
		return CountryId;
	}
	public void setCountryId(UUID countryId) {
		CountryId = countryId;
	}
}
