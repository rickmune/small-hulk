package com.safapp.service;

public interface IMasterDataSync {

	public String getGetCountry();
	
	public String logIn(String userName, String Password);
	
	public String getProduct(String accountId);
	
	public String getCategory(String accountId);
}
