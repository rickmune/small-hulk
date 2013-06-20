package com.safapp.service;

import android.util.Log;

import com.safapp.entities.Account;
import com.safapp.entities.User;
import com.safapp.utils.enums.UserType;

public class LoginService implements ILoginService {

	private static final String Tag = "LoginService";
	IMasterDataSync dataSync;
	
	public LoginService(IMasterDataSync dataSync) {
		this.dataSync = dataSync;
	}
	
	@Override
	public boolean phoneSetUp(String userName, String password) {
		return dataSync.getCountry();
	}

	@Override
	public boolean doLogin(String userName, String password, UserType type) {
		if(!dataSync.logIn(userName, password))
			return false;
		return true;
	}

	@Override
	public boolean register(User user, Account account) {
		Log.d(Tag, "register");
		if(!dataSync.AddAccount(account) && dataSync.AddUser(user)){
			return false;
		}
		return true;
	}

}
