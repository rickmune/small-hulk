package com.safapp.dto;

import java.util.Date;
import java.util.UUID;

import com.safapp.entities.Account;
import com.safapp.entities.Outlet;
import com.safapp.entities.Route;
import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.IRouteRepository;
import com.safapp.utils.RepositoryRegistry;

public class OutletDTO extends BaseDTO {

	public OutletDTO(UUID id, boolean isActive, UUID accountId, UUID routeID,
			String name, String code, double longitude, double latitude) {
		super(id, isActive);
		AccountId = accountId;
		RouteID = routeID;
		Name = name;
		Code = code;
		Longitude = longitude;
		Latitude = latitude;
	}
	private UUID AccountId;
	private UUID RouteID;
	private String Name;
	private String Code;
	private double Longitude;
	private double Latitude;
	
	public UUID getAccountId() {
		return AccountId;
	}
	public void setAccountId(UUID accountId) {
		AccountId = accountId;
	}
	public UUID getRouteID() {
		return RouteID;
	}
	public void setRouteID(UUID routeID) {
		RouteID = routeID;
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
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	
	public Outlet Map() throws Exception{
		Date date = new Date();
		Account account = RepositoryRegistry.get(IAccountRepository.class).getById(AccountId);
		Route route = RepositoryRegistry.get(IRouteRepository.class).getById(RouteID);
		return new Outlet(getId(), date, date, isIsActive(), account, route, Name, Code, Longitude, Latitude);
	}
}
