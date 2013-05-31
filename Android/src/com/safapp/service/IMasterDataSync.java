package com.safapp.service;

import com.safapp.entities.Account;
import com.safapp.entities.User;

public interface IMasterDataSync extends IServiceBase{

	public boolean getCountry();
	
	public boolean logIn(String userName, String Password);
	
	public boolean getProduct(String accountId);
	
	public boolean getCategory(String accountId);
	
	public boolean getUser();
	
	public boolean getAddUser(User user);
	
	public boolean getAddAccount(Account account);
}
