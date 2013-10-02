package com.maina.formdata.ui;

import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.maina.formdata.datamanager.Datamanager;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;

public class BaseActivity extends OrmLiteBaseActivity<Datamanager> {
	protected IDataManager dataManager = null;
	protected boolean destroy = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		if(CloudManager.getObject(CloudConstants.DATABASEMANAGER.value) == null){
			dataManager = getHelper();
			CloudManager.putObject(CloudConstants.DATABASEMANAGER.value, dataManager, true);
			destroy = true;
			Log.d("BaseActivity", "onCreate() destroy: "+destroy);
		}else{
			if(dataManager == null)dataManager = (IDataManager) CloudManager.getObject(CloudConstants.DATABASEMANAGER.value);
			Log.d("BaseActivity", "onCreate() destroy else: "+destroy);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(CloudManager.getObject(CloudConstants.DATABASEMANAGER.value) == null){
			dataManager = getHelper();
			CloudManager.putObject(CloudConstants.DATABASEMANAGER.value, dataManager, true);
			destroy = true;
			Log.d("BaseActivity", "onCreate() destroy: "+destroy);
		}
	}
	
	@Override
	protected void onDestroy() {
		Log.d("BaseActivity", "onDestroy getHelper().isOpen(): "+getHelper().isOpen());
		if(getHelper().isOpen() && destroy){
			Log.d("BaseActivity", "Releasing Helper");
			//releaseHelper((Datamanager) dataManager);
			//CloudManager.deleteObject(CloudConstants.DATABASEMANAGER.value);
		}
		super.onDestroy();
	}
}
