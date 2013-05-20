package com.safapp.ui;

import android.os.Bundle;
import android.view.View;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.safapp.androidclient.R;
import com.safapp.utils.CloudDataHolder;
import com.safapp.utils.database.DataBaseManager;
import com.safapp.utils.enums.CloudConstants;

public class Login extends OrmLiteBaseActivity<DataBaseManager>{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		DataBaseManager databaseManager = getHelper();
		CloudDataHolder.putObject(CloudConstants.DATABASEMANAGER.value,databaseManager, false);
	}
	
	public void  onSettingsClick(View view){
		
	}
	
	public void onLoginClick(View view) {
		
	}
}
