package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Category extends BaseEntity {
	
	public Category(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name, String desciption, Account account) {
		super(id, createdOn, updatedOn, isActive);
		Name = name;
		Desciption = desciption;
		this.Account = account;
	}
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Desciption;
	@DatabaseField
	private Account Account;
	
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
	public Account getAccount() {
		return Account;
	}
	public void setUser(Account account) {
		this.Account = account;
	}
}
