package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Category extends BaseEntity {
	
	public Category(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name, String desciption, User user) {
		super(id, createdOn, updatedOn, isActive);
		Name = name;
		Desciption = desciption;
		this.user = user;
	}
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Desciption;
	@DatabaseField
	private User user;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDesciption() {
		return Desciption;
	}
	public void setDesciption(String desciption) {
		Desciption = desciption;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
