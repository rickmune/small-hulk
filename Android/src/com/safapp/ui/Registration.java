package com.safapp.ui;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.safapp.R;
import com.safapp.entities.Account;
import com.safapp.entities.Country;
import com.safapp.entities.User;
import com.safapp.repositories.ICountryRepository;
import com.safapp.service.ILoginService;
import com.safapp.utils.RepositoryRegistry;
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
		Spinner spinner = (Spinner) findViewById(R.id.register_country);
		Cursor cursor = null;
		try {
			cursor = RepositoryRegistry.get(ICountryRepository.class).getAllCountries();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cursor.getCount() > 0) {
			String[] from = new String[] { "entityName" };
			int[] to = new int[] { android.R.id.text1 };
			SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, 
					cursor, from, to);
			mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(mAdapter);
		}
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos,
					long log) {
				Cursor c = (Cursor) parent.getItemAtPosition(pos);
				CountyId = UUID.fromString(c.getString(c.getColumnIndexOrThrow("_id")));
			}

			public void onNothingSelected(AdapterView<?> arg0) {

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
		Country country = null;
		try {
			country = RepositoryRegistry.get(ICountryRepository.class).getById(CountyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = new Date();
		Account account = new Account(UUID.randomUUID(), date, date, true, accoutName, country);
		User user = new User(UUID.randomUUID(), date, date, true, username, confirmPassword, fullName, 
				email, phoneNumber, userType, account);
		boolean done = ServiceRegistry.get(ILoginService.class).register(user, account);
		if(done){
			startActivity(new Intent(Registration.this, Home.class));
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
