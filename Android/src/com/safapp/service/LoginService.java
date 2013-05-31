package com.safapp.service;

import android.util.Log;

import com.safapp.entities.Account;
import com.safapp.entities.User;
import com.safapp.utils.enums.UserType;

public class LoginService implements ILoginService {

	private static final String Tag = "LoginService";
	IMasterDataSync dataSync;
		
	@Override
	public boolean phoneSetUp(String userName, String password) {
		return dataSync.getCountry();
	}

	@Override
	public boolean doLogin(String userName, String password, UserType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(User user, Account account) {
		Log.d(Tag, "register");
		if(!dataSync.getAddAccount(account) && dataSync.getAddUser(user)){
			return false;
		}
		//sync other stuff
		return true;
	}

}
