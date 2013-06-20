package com.safapp.dto;

import java.util.Date;
import java.util.UUID;

import com.safapp.entities.Account;
import com.safapp.entities.User;
import com.safapp.repositories.IAccountRepository;
import com.safapp.utils.RepositoryRegistry;
import com.safapp.utils.enums.UserType;

public class UserDto extends BaseDTO{

	public UserDto(UUID id, boolean isActive, String username, String password,
			String fullname, com.safapp.utils.enums.UserType userType,
			String email, String phoneNumber, UUID accountId) {
		super(id, isActive);
		Username = username;
		Password = password;
		Fullname = fullname;
		UserType = userType;
		Email = email;
		PhoneNumber = phoneNumber;
		AccountId = accountId;
	}
	
	private String Username;
	private String Password;
	private String Fullname;
	private UserType UserType;
	private String Email;
	private String PhoneNumber;
	private UUID AccountId;
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
	public UUID getAccountId() {
		return AccountId;
	}
	public void setAccountId(UUID accountId) {
		AccountId = accountId;
	}
	
	public User Map() throws Exception{
		Date date = new Date();
		Account account = RepositoryRegistry.get(IAccountRepository.class).getById(AccountId);
		return new User(getId(), date, date, isIsActive(), Username, Password, Fullname, Email, 
				PhoneNumber, UserType, account);
	}
}
