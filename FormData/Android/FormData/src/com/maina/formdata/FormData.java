package com.maina.formdata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormRespondentTypeRepository;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.service.ILoginService;
import com.maina.formdata.service.LoginService;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;
import com.maina.formdata.utils.MakePWD;
import com.maina.formdata.utils.SyncEntity;

public class FormData extends BaseActivity{
	private static final String TAG = "LoginActivity";
	public static final String USERID = "USERID";
	public static final String CLIENTID = "CLIENTID";
	@SuppressWarnings("rawtypes")
	private Class classInstance = null;
	private Method methodInstance = null;
	String MethodName = null;
	EditText username, password;
	Button login;
	private ProgressDialog dialog;
	private UserDto dto;
	private String Error = "";
	boolean pass = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		username = (EditText)findViewById(R.id.username_f);
		password = (EditText)findViewById(R.id.password_f);
		login = (Button)findViewById(R.id.login_btn);
		
		try {
			classInstance =  Class.forName("com.maina.formdata.repository.IDUserRepository");
			MethodName = "getuserName";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 new InitilizeAsyncTask().execute();
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SyncEntity<UserDto> entity = Login();
				if(entity.getStatus()){
					moveToNext(entity);
				}else{
					Toast.makeText(FormData.this, entity.getInfo(), Toast.LENGTH_LONG).show();
					remoteRequest(entity.getInfo());
					System.out.println("after remoteRequest");
				}
			}
		});
	}
	
	private void moveToNext(SyncEntity<UserDto> entity){
		System.out.println("in moveToNext");
		if(entity.getStatus()){
			dto = entity.getData().get(0);
			System.out.println(dto.toString());
			Bundle bundle = new Bundle();
			bundle.putString(CLIENTID, dto.getClientId().toString());
			bundle.putString(USERID, dto.getId().toString());
			Intent intent = new Intent(FormData.this, FormListActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}else{
			System.out.println("in moveToNext entity.getStatus(): "+entity.getStatus());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.form_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("FormListActivity", "onOptionsItemSelected");
		int itemId = item.getItemId();
		if(itemId == R.id.action_sync){
			Log.d(TAG, "onOptionsItemSelected action_sync");
			doSync();
			return true;
		}else if(itemId == R.id.action_settings){
			Log.d(TAG, "onOptionsItemSelected action_settings");
			return true;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void doSync(){
		if(validate()){
			SyncEntity<UserDto>entity = Login();
			if(entity.getStatus())
				new SyncAsyncTask().execute(new String[]{entity.getData().get(0).getClientId().toString()});
			else
				remoteRequest(entity.getInfo());
		}else{
			Toast.makeText(FormData.this, Error, Toast.LENGTH_LONG).show();
		}
	}
	
	private SyncEntity<UserDto> Login(){
		SyncEntity<UserDto> dUserE = null;
		if(validate()){
			String userName = username.getText().toString();
			try {
				String pwd = password.getText().toString();
				try {
					pwd = MakePWD.getMD5(password.getText().toString());
				} catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
				dUserE = Repositoryregistry.get(IDUserRepository.class, dataManager).login(userName, pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Toast.makeText(FormData.this, Error, Toast.LENGTH_LONG).show();
			dUserE = new SyncEntity<UserDto>();
			dUserE.setInfo(Error);dUserE.setStatus(false);
		}
		
		return dUserE;
	}
	
	private class InitilizeAsyncTask extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(FormData.this);
			dialog.setMessage("Starting");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params){
			System.out.println("in doInBackground getuserName");
			return getuserName();
		}
		
		@Override
		protected void onPostExecute(String userName) {
			if(dialog == null)return;
			else if(dialog != null && dialog.isShowing())dialog.dismiss();
			username.setText((userName == null ? "" : userName));
            //startService(new Intent(LoginActivity.this, SyncService.class));
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getuserName(){
		String ret = null;
		System.out.println("in getuserName()");
		try {
			methodInstance = this.classInstance.getDeclaredMethod(MethodName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			ret = (String) methodInstance.invoke(Repositoryregistry.get(classInstance, 
					(IDataManager)CloudManager.getObject(CloudConstants.DATABASEMANAGER.value)));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private class SyncAsyncTask extends AsyncTask<String, Void, String[]>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(FormData.this);
			dialog.setMessage("Fetching data...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String[] doInBackground(String... params){
			ILoginService loginService = new LoginService(Repositoryregistry.get(IDFormRepository.class, dataManager), 
					new HttpUtils(), 
					Repositoryregistry.get(IDFormRespondentTypeRepository.class, dataManager), 
					Repositoryregistry.get(IDFormItemRepository.class, dataManager), 
					Repositoryregistry.get(IDFormItemAnswerRepository.class, dataManager), 
					Repositoryregistry.get(IDFormItemRespondentTypeRepository.class, dataManager),
					Repositoryregistry.get(IDUserRepository.class, dataManager));
			try {
				Log.d("SyncService", "SyncForm() called with params.length: "+ params.length);
				loginService.SyncForm(params);
				return params;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] returns) {
			if(dialog == null)return;
			else if(dialog != null && dialog.isShowing())dialog.dismiss();
			if(returns != null && returns.length > 1){
				try {
					SyncEntity<UserDto> entity = Repositoryregistry.get(IDUserRepository.class, dataManager).login(
							username.getText().toString(), password.getText().toString());
					moveToNext(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	private boolean validate(){
		if(username.getText() == null){
			Error += "UserName Cannot be Empty";
			return false;
		}
		if(password.getText() == null){
			Error += "Password Cannot be Empty";
			return false;
		}
		Error = "";
		return true;
	}

	private void remoteRequest(String msg){
		final Dialog dialog = new Dialog(FormData.this);
		LayoutInflater infalInflater = (LayoutInflater) FormData.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = infalInflater.inflate(R.layout.remote_request, null);
		dialog.setContentView(view);
		dialog.setTitle("Info");
		((TextView)view.findViewById(R.id.remote_title)).setText("Saved data will be deleted and redownloaded");
		((TextView)view.findViewById(R.id.remote_message)).setText(msg);
		Button ok = (Button)view.findViewById(R.id.request_ok);
		Button cancel = (Button)view.findViewById(R.id.request_cancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				String pwd = password.getText().toString();
				try {
					pwd = MakePWD.getMD5(password.getText().toString());
				} catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
				new SyncAsyncTask().execute(new String[]{username.getText().toString(), pwd});
			};
		});
		dialog.show();
	}
}
