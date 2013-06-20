package com.safapp.service;

import com.safapp.entities.Account;
import com.safapp.entities.Category;
import com.safapp.entities.Outlet;
import com.safapp.entities.Product;
import com.safapp.entities.Route;
import com.safapp.entities.User;

public interface IMasterDataSync extends IServiceBase{

	public boolean getCountry();
	
	public boolean logIn(String userName, String Password);
	
	public boolean getProduct(String accountId);
	
	public boolean getCategory(String accountId);
	
	public boolean getUser();
	
	public boolean getRoute(String accountId);
	
	public boolean getOutlet(String accountid);
	
	public boolean AddUser(User user);
	
	public boolean AddAccount(Account account);
	
	public boolean AddRoute(Route route);
	
	public boolean AddOutlet(Outlet outlet);
	
	public boolean AddCategory(Category category);
	
	public boolean Addproduct(Product product);
}
