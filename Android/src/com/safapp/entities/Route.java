package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.safapp.dto.RouteDTO;

@DatabaseTable
public class Route extends BaseEntity {
	
	public Route() {
	}
	
	public Route(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			com.safapp.entities.Account account, String name, String code) {
		super(id, createdOn, updatedOn, isActive);
		Account = account;
		Name = name;
		Code = code;
	}
	public Route(UUID id, com.safapp.entities.Account account, String name,
			String code) {
		super(id);
		Account = account;
		Name = name;
		Code = code;
	}
	
	@DatabaseField(foreignAutoCreate = true)
	private Account Account;
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Code;
	
	public Account getAccount() {
		return Account;
	}
	public void setAccount(Account account) {
		Account = account;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public RouteDTO Map(){
		return new RouteDTO(getId(), isIsActive(), Account.getId(), Name, Code);
	}
}
