package com.safapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.safapp.androidclient.R;
import com.safapp.service.ILoginService;
import com.safapp.utils.CloudDataHolder;
import com.safapp.utils.ServiceRegistry;
import com.safapp.utils.database.DataBaseManager;
import com.safapp.utils.enums.CloudConstants;
import com.safapp.utils.enums.UserType;

public class Login extends OrmLiteBaseActivity<DataBaseManager>{

	private static final String Tag = "Login";
	private int checkedRadio;
	private View login_phonenumber;
	private View login_emaillayout;
	private EditText loginPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		DataBaseManager databaseManager = getHelper();
		CloudDataHolder.putObject(CloudConstants.DATABASEMANAGER.value,databaseManager, false);
		login_emaillayout = (LinearLayout)findViewById(R.id.login_emaillayout);
		login_phonenumber = (LinearLayout)findViewById(R.id.login_phonenumber);
		loginPassword = (EditText)findViewById(R.id.loginPassword);
		
		RadioGroup button = (RadioGroup)findViewById(R.id.radiologintype);
		button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.radiophonenumber){
					checkedRadio = checkedId;
					Log.d(Tag, "onCheckedChanged phonenumber");
					login_phonenumber.setVisibility(View.VISIBLE);
					login_emaillayout.setVisibility(View.GONE);
				}else if(checkedId == R.id.radioemail){
					checkedRadio = checkedId;
					login_phonenumber.setVisibility(View.GONE);
					login_emaillayout.setVisibility(View.VISIBLE);
					Log.d(Tag, "onCheckedChanged email");
				}
			}
		});
	}
	
	/**/
	public void  onSettingsClick(View view){
		
	}
	
	public void onLoginClick(View view) {
				
		String password = loginPassword.getText().toString().trim();
		String userName = "";
		UserType type = null;
		if(checkedRadio == R.id.radioemail){
			userName = ((EditText)login_emaillayout.findViewById(R.id.login_email)).getText().toString().trim();
			type = UserType.Email;
		}else if(checkedRadio == R.id.radiophonenumber){
			userName = ((EditText)login_phonenumber.findViewById(R.id.login_phone)).getText().toString().trim();
			type = UserType.Phone;
		}
		if(ServiceRegistry.get(ILoginService.class).doLogin(userName, password, type))
			startActivity(new Intent(Login.this, DashBoard.class));
		else
			Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
	}
	
}
