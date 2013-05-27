package com.safapp.ui;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.safapp.androidclient.R;
import com.safapp.entities.Account;
import com.safapp.entities.Country;
import com.safapp.entities.User;
import com.safapp.service.ILoginService;
import com.safapp.utils.ServiceRegistry;
import com.safapp.utils.enums.UserType;

public class Registration extends Activity{

	UUID CountyId;
	UserType userType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		RadioGroup button = (RadioGroup)findViewById(R.id.radio_user_type);
		button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.radiophone_type){
					userType = UserType.Phone;
				}else if(checkedId == R.id.radioemail_type){
					userType = UserType.Email;
				}
			}
		});
		
	}
	
	public void  onSettingsClick(View view){
		
	}
	
	public void onRegisterClick(View view){
		String accoutName = ((EditText)findViewById(R.id.register_account_name)).getText().toString().trim();
		String phoneNumber = ((EditText)findViewById(R.id.register_phone_no)).getText().toString().trim();
		String email = ((EditText)findViewById(R.id.register_email)).getText().toString().trim();
		String password = ((EditText)findViewById(R.id.loginPassword)).getText().toString().trim();
		String confirmPassword = ((EditText)findViewById(R.id.loginPasswordConfirm)).getText().toString().trim();
		String fullName = ((EditText)findViewById(R.id.register_full_name)).getText().toString().trim();
		if(accoutName.length() < 1 || phoneNumber.length() < 1 || email.length() < 1 || password.length() < 1 
				|| confirmPassword.length() < 1 || fullName.length() < 1 ){
			Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
			return;
		}
		if(password.equals(confirmPassword)){
			Toast.makeText(this, "passwords do not match", Toast.LENGTH_LONG).show();
			return;
		}
		String username = (userType == UserType.Email ? email : phoneNumber);
		Date date = new Date();
		Account account = new Account(UUID.randomUUID(), date, date, true, accoutName);
		User user = new User(UUID.randomUUID(), date, date, true, username, confirmPassword, fullName, 
				email, phoneNumber, new Country(CountyId), userType, account);
		boolean done = ServiceRegistry.get(ILoginService.class).register(user, account);
		if(done)
			startActivity(new Intent(Registration.this, DashBoard.class));
	}
}
