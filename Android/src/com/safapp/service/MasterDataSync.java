package com.safapp.service;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.safapp.dto.UserDto;
import com.safapp.utils.GlobalSettings;
import com.safapp.utils.SyncEntity;
import com.safapp.utils.http.IHttpUtils;

public class MasterDataSync implements IMasterDataSync {

	public MasterDataSync(IHttpUtils httpUtils) {
		this.httpUtils = httpUtils;
	}

	private static final String Tag = "MasterDataSync";
	
	IHttpUtils httpUtils;
	@Override
	public boolean getGetCountry() {
		
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
			string = httpUtils.GetRequest(GlobalSettings.userWebService(), params);
			SyncEntity<UserDto> syncEntity = deserialize(string, new TypeToken<SyncEntity<UserDto>>(){}.getType());
			Log.d(Tag, "Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			List<UserDto> dtos = syncEntity.getT();
			Log.d(Tag, "dtos.size(): "+ dtos.size());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private <T> T deserialize(String json, Type t){
		Log.d(Tag, "deserialize b4: "+json);
		GsonBuilder b = new GsonBuilder();
		b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Log.d(Tag, "deserialize EntitySyncInfoserializer");
    	Gson gson = b.create();
    	JsonParser parser = new JsonParser();
    	try{
    		JsonObject object1 = parser.parse(json.trim()).getAsJsonObject();
    		return gson.fromJson(object1, t);
    	}catch(Exception e){
    		Log.d(Tag, e.getMessage());
    		JsonArray array = parser.parse(json.trim()).getAsJsonArray();
    		return gson.fromJson(array, t);
    	}
	}
}
