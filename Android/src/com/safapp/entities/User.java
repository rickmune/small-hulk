package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.safapp.utils.enums.UserType;

@DatabaseTable
public class User extends BaseEntity{

	public User(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String username, String password, String fullName, String email,
			String phoneNumber, com.safapp.entities.Country country, UserType userType) {
		super(id, createdOn, updatedOn, isActive);
		Username = username;
		Password = password;
		FullName = fullName;
		Email = email;
		PhoneNumber = phoneNumber;
		Country = country;
		UserType = userType;
	}
	@DatabaseField
	private String Username;
	@DatabaseField
	private String Password;
	@DatabaseField
	private String FullName;
	@DatabaseField
	private String Email;
	@DatabaseField
	private String PhoneNumber;
	@DatabaseField
	private Country Country;
	@DatabaseField
	private UserType UserType;
	
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
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
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
	public Country getCountry() {
		return Country;
	}
	public void setCountry(Country country) {
		Country = country;
	}
	public UserType getUserType() {
		return UserType;
	}
	public void setUserType(UserType userType) {
		UserType = userType;
	}
}
