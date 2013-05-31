package com.safapp.service;

import java.util.Hashtable;
import java.util.List;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.safapp.dto.CountryDTO;
import com.safapp.dto.UserDto;
import com.safapp.entities.Account;
import com.safapp.entities.User;
import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.ICountryRepository;
import com.safapp.repositories.IUserRepository;
import com.safapp.utils.GlobalSettings;
import com.safapp.utils.JsonConverter;
import com.safapp.utils.SyncEntity;
import com.safapp.utils.http.IHttpUtils;

public class MasterDataSync extends ServiceBase implements IMasterDataSync {

	ICountryRepository countryRepository;
	IAccountRepository accountRepository;
	IUserRepository userRepository;
	
	public MasterDataSync(IHttpUtils httpUtils, ICountryRepository countryRepository) {
		this.httpUtils = httpUtils;
		this.countryRepository = countryRepository;
	}

	private static final String Tag = "MasterDataSync";
	
	IHttpUtils httpUtils;
	@Override
	public boolean getCountry() {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getcountryWebService(), params);
			SyncEntity<CountryDTO> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<CountryDTO>>(){}.getType());
			Log.d(Tag, "Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			List<CountryDTO> dtos = syncEntity.getData();
			for(CountryDTO dto : dtos){
				int y = countryRepository.save(dto.Map());
				Log.d(Tag, "save country: "+ y);
			}
			Log.d(Tag, "dtos.size(): "+ dtos.size());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean logIn(String userName, String Password) {
		
		return false;
	}

	@Override
	public boolean getProduct(String accountId) {
		return false;
	}

	@Override
	public boolean getCategory(String accountId) {
		
		return false;
	}

	public boolean getUser(){
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getuserWebService(), params);
			SyncEntity<UserDto> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<UserDto>>(){}.getType());
			Log.d(Tag, "Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			List<UserDto> dtos = syncEntity.getData();
			Log.d(Tag, "dtos.size(): "+ dtos.size());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean getAddUser(User user) {
		int x=0;
		try {
			x = userRepository.save(user);
			Log.d(Tag, "userRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String resp = null;
		String json = JsonConverter.MapObject(user.makeDTO());
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("userDto", json);
		try {
			resp = httpUtils.PostRequest(GlobalSettings.putUserWebService(), params);
			Log.d(Tag, "getAddUser: "+ resp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getAddAccount(Account account) {
		int x = 0;
		try {
			x = accountRepository.save(account);
			Log.d(Tag, "accountRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String resp = null;
		String json = JsonConverter.MapObject(account.toDTO());
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("accountDTO", json);
		try {
			resp = httpUtils.PostRequest(GlobalSettings.putAccountWebService(), params);
			Log.d(Tag, "getAddAccount: "+ resp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
}
