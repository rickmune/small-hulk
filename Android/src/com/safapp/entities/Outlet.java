package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.safapp.dto.OutletDTO;

@DatabaseTable
public class Outlet extends BaseEntity {
	
	public Outlet() {
	}
	public Outlet(UUID id, Account account, Route route, String name, String code,
			double longitude, double latitude) {
		super(id);
		Account = account;
		Route = route;
		Name = name;
		Code = code;
		Longitude = longitude;
		Latitude = latitude;
	}
	public Outlet(UUID id, Date createdOn, Date updatedOn, boolean isActive, Account account,
			Route route, String name, String code, double longitude, double latitude) {
		super(id, createdOn, updatedOn, isActive);
		Account = account;
		Route = route;
		Name = name;
		Code = code;
		Longitude = longitude;
		Latitude = latitude;
	}
	@DatabaseField(foreignAutoCreate = true)
	private Account Account;
	@DatabaseField(foreignAutoCreate = true)
	private Route Route;
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Code;
	@DatabaseField
	private double Longitude;
	@DatabaseField
	private double Latitude;
	
	public Account getAccount() {
		return Account;
	}
	public void setAccount(Account account) {
		Account = account;
	}
	public Route getRoute() {
		return Route;
	}
	public void setRoute(Route route) {
		Route = route;
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
	public double getLogitude() {
		return Longitude;
	}
	public void setLogitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public OutletDTO Map(){
		return new OutletDTO(getId(), isIsActive(), Account.getId(), Route.getId(), Name, Code, Longitude, Latitude);
	}
}
