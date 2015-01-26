package com.maina.formdata.ui;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.maina.formdata.FormData;
import com.maina.formdata.R;
import com.maina.formdata.R.color;
import com.maina.formdata.dto.BasicResponse;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.enums.UserType;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.htttputil.IHttpUtils;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.utils.MakePWD;
import com.maina.formdata.utils.SyncEntity;
import com.maina.formdata.utils.ui.GenUtils;

public class ChangePassword extends BaseActivity{
	
	private EditText OldPWD, NewPWD, ConfirmPWD;
	private ProgressDialog dialog;
	private String Error;
	IHttpUtils httpUtils;
	UserDto dto;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        setTitle("Change Password");
        if(savedInstanceState == null) dto = FromBundle(getIntent().getExtras(), new UserDto());
        OldPWD = (EditText)findViewById(R.id.old_password);
        NewPWD = (EditText)findViewById(R.id.new_password);
        ConfirmPWD = (EditText)findViewById(R.id.confrim_password);
        if(httpUtils == null) httpUtils = new HttpUtils();
        (findViewById(R.id.cancel_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChangePassword.this, FormData.class);
				intent.putExtras(ToBundle(new Bundle(), dto));
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
        (findViewById(R.id.ok_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!validate()){
					findViewById(R.id.chg_header).setBackgroundColor(color.orange);
					((TextView)findViewById(R.id.chg_header)).setText(Error);
					Toast.makeText(ChangePassword.this, Error, Toast.LENGTH_LONG).show();
				}else{
					changePWD();
				}
			}
		});
	}
	
	private void changePWD(){
		new ChangePWD().execute();
	}
	
	private boolean validate(){
		((TextView)findViewById(R.id.chg_header)).setText("");
		Error = "";
		boolean valid = true;
		try {
			if(OldPWD.getText() == null || OldPWD.getText().toString().equals("")){
				Error = "Previous Password is required!";
				valid = false;
			}
			if(NewPWD.getText() == null || NewPWD.getText().toString().equals("")){
				Error += "New Password is required!";
				valid = false;
			}
			if(ConfirmPWD.getText() == null || ConfirmPWD.getText().toString().equals("")){
				Error += "New Password is required!";
				valid = false;
			}
			if(valid){
				Error = "";
				String encrypt = MakePWD.getMD5(OldPWD.getText().toString());
				System.out.println("Password 1: "+OldPWD.getText().toString()+
						" Password 2: " + encrypt + " Db: " + dto.getPassword());
				if(!encrypt.equals(dto.getPassword())){
					Error = "Old Password is Incorrect";
					valid = false;
				}else{
					if(!NewPWD.getText().toString().equals(ConfirmPWD.getText().toString())){
						Error = "New Passwords do not match!";
						valid = false;
					}else{
						String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{7,}";//(?=\\S+$)$";
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(NewPWD.getText().toString());
						if(!m.matches()){
							Error = "Password must be at least of length 7 and contain Alphanumeric Characters!";
							valid = false;
						}
					}
					
				}
			}
		} catch (Exception e) {
			valid = false;
		}
		return valid;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState = ToBundle(outState, dto);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		dto = FromBundle(savedInstanceState, new UserDto());
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	class ChangePWD extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ChangePassword.this);
			dialog.setMessage("Contacting Server, Please Wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			String resp = null;
			try {
				String url = GenUtils.getUrl(dataManager)+"/api/client/user/changepassword";
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("userId", dto.getId().toString());
				table.put("password", MakePWD.getMD5(NewPWD.getText().toString()));
				
				resp = httpUtils.PostRequestWithKey(url, table);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
		
		@Override
		protected void onPostExecute(String returns) {
			if(dialog != null && dialog.isShowing())dialog.dismiss();
			if(returns == null){
				Error = "Server did not respond. Ensure your network is Ok the try changing your password again";
				((TextView)findViewById(R.id.chg_header)).setText(Error);
				Toast.makeText(ChangePassword.this, Error, Toast.LENGTH_LONG).show();
			}else{
				try {
					BasicResponse basicResponse = deserialize(returns, new TypeToken<BasicResponse>() {}.getType());
					if(basicResponse.Status){
						updatePWD();
					}else{
						Error = "Password not Changed. Try changing your password again";
						((TextView)findViewById(R.id.chg_header)).setText(Error);
						Toast.makeText(ChangePassword.this, Error, Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) { 
					e.printStackTrace();
					Error = "Error Occured. Ensure your network is Ok the try changing your password again";
					((TextView)findViewById(R.id.chg_header)).setText(Error);
					Toast.makeText(ChangePassword.this, Error, Toast.LENGTH_LONG).show();
				}
			}
		}
		
	}
	
	private void updatePWD(){
		try {
			try {
				SyncEntity<UserDto> entity = Repositoryregistry.get(IDUserRepository.class, dataManager).login(
						dto.getUsername(), dto.getPassword());
				UserDto user = entity.getData().get(0);
				String password = MakePWD.getMD5(NewPWD.getText().toString());
				user.setPassword(password);
				dto.setPassword(password);
				UserType t = (user.getUserType() == 2 ? UserType.TDR : UserType.Admin);
				Repositoryregistry.get(IDUserRepository.class, dataManager).save(new DUserE(user.getId(), user.getUsername(), user.getPassword(), 
						user.getFullname(), t, user.getEmail(), user.getPhoneNumber(), user.getClientId(),
						user.getLocationId(), user.getClientName(), user.isIsPasswordChanged(),
						user.isIsSecuritySet(), user.getSecurityQuestionId(), user.getSecurityAnswer()));
				Toast.makeText(ChangePassword.this, Error, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent();
				intent.putExtras(ToBundle(new Bundle(), dto));
				setResult(RESULT_OK, intent);
				finish();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
}
