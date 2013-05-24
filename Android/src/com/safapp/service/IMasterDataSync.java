package com.safapp.service;

public interface IMasterDataSync extends IServiceBase{

	public boolean getGetCountry();
	
	public boolean logIn(String userName, String Password);
	
	public boolean getProduct(String accountId);
	
	public boolean getCategory(String accountId);
	
	public boolean getUser();
}
