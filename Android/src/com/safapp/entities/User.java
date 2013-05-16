package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

public class User extends BaseEntity{

	public User(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String username, String password, String name) {
		super(id, createdOn, updatedOn, isActive);
		Username = username;
		Password = password;
		Name = name;
	}
	private String Username;
	private String Password;
	private String Name;
	
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
