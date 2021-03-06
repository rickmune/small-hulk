package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.safapp.dto.UserDto;
import com.safapp.utils.enums.UserType;

@DatabaseTable
public class User extends BaseEntity{

	public User() {
	}
	
	public User(UUID id, Date createdOn, Date updatedOn, boolean isActive) {
		super(id, createdOn, updatedOn, isActive);
	}
	public User(UUID id) {
		super(id);
	}
	public User(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String username, String password, String fullName, String email,
			String phoneNumber, UserType userType, Account account) {
		super(id, createdOn, updatedOn, isActive);
		Username = username;
		Password = password;
		FullName = fullName;
		Email = email;
		PhoneNumber = phoneNumber;
		UserType = userType;
		Account = account;
	}
	
	public final static String EMAIL = "email";
	public final static String PHONE = "phone";
	
	@DatabaseField
	private String Username;
	@DatabaseField
	private String Password;
	@DatabaseField
	private String FullName;
	@DatabaseField(columnName = EMAIL)
	private String Email;
	@DatabaseField(columnName = PHONE)
	private String PhoneNumber;
	@DatabaseField
	private UserType UserType;
	@DatabaseField(foreign = true)
	private Account Account;
	
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

	public UserType getUserType() {
		return UserType;
	}
	public void setUserType(UserType userType) {
		UserType = userType;
	}
	public Account getAccount() {
		return Account;
	}
	public void setAccount(Account account) {
		Account = account;
	}
	
	public UserDto makeDTO(){
		return new UserDto(getId(), isIsActive(), Username, 
				Password, FullName, UserType, EMAIL, PhoneNumber, Account.getId());
	}
}
