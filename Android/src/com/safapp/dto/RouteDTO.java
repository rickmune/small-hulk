package com.safapp.dto;

import java.util.Date;
import java.util.UUID;

import com.safapp.entities.Account;
import com.safapp.entities.Route;
import com.safapp.repositories.IAccountRepository;
import com.safapp.utils.RepositoryRegistry;

public class RouteDTO extends BaseDTO {

	public RouteDTO(UUID id, boolean isActive, UUID accountId, String name, String code) {
		super(id, isActive);
		AccountId = accountId;
		Name = name;
		Code = code;
	}
	private UUID AccountId;
	private String Name;
	private String Code;
	
	public UUID getAccountId() {
		return AccountId;
	}
	public void setAccountId(UUID accountId) {
		AccountId = accountId;
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
	
	public Route Map() throws Exception{
		Date date = new Date();
		Account account = RepositoryRegistry.get(IAccountRepository.class).getById(AccountId);
		return new Route(getAccountId(), date, date, isIsActive(), account, Name, Code);
	}
}
