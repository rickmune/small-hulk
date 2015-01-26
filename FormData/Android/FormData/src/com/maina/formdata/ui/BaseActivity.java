package com.maina.formdata.ui;

import java.lang.reflect.Type;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maina.formdata.Controller;
import com.maina.formdata.FormListActivity;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.enums.DformItemType;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;
import com.maina.formdata.utils.DformItemTypeserializer;

public class BaseActivity extends FragmentActivity {//ActionBarActivity {//
	protected IDataManager dataManager = null;
    protected Controller controller;
	protected boolean destroy = false;
	public static final String PASSWORD = "_PASSWORD";
	public static final String USERID = "USERID";
	public static final String CLIENTID = "CLIENTID";
	public static final String CLIENTNAME = "_CLIENTNAME";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
        if(controller == null)controller = (Controller)getApplicationContext();
        if(dataManager == null) dataManager = controller.getDatabaseAccess();
        if(CloudManager.getObject(CloudConstants.DATABASEMANAGER.value) == null){
            CloudManager.putObject(CloudConstants.DATABASEMANAGER.value, dataManager, true);
            destroy = true;
            Log.d("BaseActivity", "onCreate() destroy: "+destroy);
        }
		/*if(CloudManager.getObject(CloudConstants.DATABASEMANAGER.value) == null){
			dataManager = getHelper();
			CloudManager.putObject(CloudConstants.DATABASEMANAGER.value, dataManager, true);
			destroy = true;
			Log.d("BaseActivity", "onCreate() destroy: "+destroy);
		}else{
			if(dataManager == null)dataManager = (IDataManager) CloudManager.getObject(CloudConstants.DATABASEMANAGER.value);
			Log.d("BaseActivity", "onCreate() destroy else: "+destroy);
		}  */
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        if(controller == null)controller = (Controller)getApplicationContext();
        if(dataManager == null) dataManager = controller.getDatabaseAccess();
		if(CloudManager.getObject(CloudConstants.DATABASEMANAGER.value) == null){
			CloudManager.putObject(CloudConstants.DATABASEMANAGER.value, dataManager, true);
			destroy = true;
			Log.d("BaseActivity", "onCreate() destroy: "+destroy);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	protected <T> T deserialize(String json, Type t) throws Exception{
		Log.d("BaseActivity", "deserialize b4: "+json);
		
		GsonBuilder b = new GsonBuilder();
		b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Log.d("BaseActivity", "deserialize EntitySyncInfoserializer");
		b.registerTypeAdapter(DformItemType.class, new DformItemTypeserializer());
    	Gson gson = b.create();
    	JsonParser parser = new JsonParser();
    	try{
    		JsonObject object1 = parser.parse(json.trim()).getAsJsonObject();
    		Log.d("BaseActivity", "(object1 == null): "+(object1 == null));
    		T y = gson.fromJson(object1, t);
    		Log.d("BaseActivity", (y != null ? y.getClass().getName() : "is NUll"));
    		return y;
    	}catch(Exception e){
    		e.printStackTrace();
    		Log.d("BaseActivity", e.getMessage());
			JsonArray array = parser.parse(json.trim()).getAsJsonArray();
			Log.d("BaseActivity", "(array == null): "+(array == null));
			T y = gson.fromJson(array, t);
    		Log.d("BaseActivity", (y != null ? y.getClass().getName() : "is NUll"));
			return y;
    	}
	}
	
	public Bundle ToBundle(Bundle bundle, UserDto dto){
		bundle.putString(CLIENTID, dto.getClientId().toString());
		bundle.putString(USERID, dto.getId().toString());
		bundle.putString(CLIENTNAME, dto.getClientName());
		bundle.putString(FormListActivity.USERNAME, dto.getUsername());
		bundle.putString(FormListActivity.LOCATIONID, dto.getLocationId().toString());
		bundle.putBoolean("IsPasswordChanged", dto.isIsPasswordChanged());
		bundle.putBoolean("IsSecuritySet", dto.isIsSecuritySet());
		bundle.putInt("SecurityQuestionId", dto.getSecurityQuestionId());
		bundle.putString("SecurityAnswer", dto.getSecurityAnswer());
		bundle.putString(PASSWORD, dto.getPassword());
		return bundle;
	}
	
	public UserDto FromBundle(Bundle bundle, UserDto dto){
		if(dto == null)dto = new UserDto();
		dto.setClientId(UUID.fromString(bundle.getString(CLIENTID)));
		dto.setId(UUID.fromString(bundle.getString(USERID)));
		dto.setUsername(bundle.getString(FormListActivity.USERNAME));
		dto.setClientName(bundle.getString(CLIENTNAME));
		dto.setLocationId(UUID.fromString(bundle.getString(FormListActivity.LOCATIONID)));
		dto.setIsPasswordChanged(bundle.getBoolean("IsPasswordChanged"));
		dto.setIsSecuritySet(bundle.getBoolean("IsSecuritySet"));
		dto.setSecurityQuestionId(bundle.getInt("SecurityQuestionId"));
		dto.setSecurityAnswer(bundle.getString("SecurityAnswer"));
		dto.setPassword(bundle.getString(PASSWORD));
		return dto;
	}

    public IDataManager getDataManager(){
        return dataManager;
    }
}
