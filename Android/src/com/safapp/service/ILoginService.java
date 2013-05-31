package com.safapp.service;

import com.safapp.entities.Account;
import com.safapp.entities.User;
import com.safapp.utils.enums.UserType;

public interface ILoginService extends IServiceBase{

	public boolean phoneSetUp(String userName, String password);
	
	public boolean doLogin(String userName, String password, UserType type);
	
	public boolean register(User user, Account account);
}
