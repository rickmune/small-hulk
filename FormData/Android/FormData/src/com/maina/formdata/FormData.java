package com.maina.formdata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
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
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.entity.PhonConfig;
import com.maina.formdata.enums.MessageTypes;
import com.maina.formdata.enums.RepositoryEnum;
import com.maina.formdata.enums.UserType;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormRespondentTypeRepository;
import com.maina.formdata.repository.IDTownRepository;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.IRepositoryBase;
import com.maina.formdata.repository.ISecurityQuestionRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.service.ILoginService;
import com.maina.formdata.service.LoginService;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.ui.ChangePassword;
import com.maina.formdata.ui.MessageNotification;
import com.maina.formdata.ui.ResetPassword;
import com.maina.formdata.ui.SetSecurityQuestion;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;
import com.maina.formdata.utils.MakePWD;
import com.maina.formdata.utils.SyncEntity;
import com.maina.formdata.utils.ui.GenUtils;

public class FormData extends BaseActivity{
	private static final String TAG = "LoginActivity";
	public static final int CHANGEPWD = 500;
	public static final int SECURITYQ = 501;
	public static final int RESETPASSWORD = 502;
	public static final int MESSAGES = 501;
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
	PhonConfig config = null;
    PowerManager.WakeLock wl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "E-DataForm");
		super.onCreate(savedInstanceState);
        wl.acquire();
		setContentView(R.layout.login_activity_new);
		username = (EditText)findViewById(R.id.username_f);
		password = (EditText)findViewById(R.id.password_f);
		login = (Button)findViewById(R.id.login_btn);
		
		try {
			classInstance =  Class.forName("com.maina.formdata.repository.IDUserRepository");
			MethodName = "getuserName";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GenUtils.getUrl(dataManager) == null){
					
				}else{
					
					SyncEntity<UserDto> entity = Login();
					if(entity == null){
						System.out.println("ISSUE WITH DB CONNECTION");
						dataManager = controller.getDatabaseAccess();
						entity = Login();
					}
					if(entity == null){
						System.out.println("ISSUE WITH DB CONNECTION");
						Toast.makeText(FormData.this, "Error during login. Stopping application. Restart it again"
								, Toast.LENGTH_LONG).show();
						System.out.println("entity == null");
                        try {
                            Thread.sleep(1000 * 3);
                            android.os.Process.killProcess(android.os.Process.myPid());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
						if(entity.getStatus()){
							progressAfterLogin(entity);
						}else{
							Toast.makeText(FormData.this, entity.getInfo(), Toast.LENGTH_LONG).show();
							
							WrongPwd(entity);
						}
					}
				}
			}
		});
	}

    @Override
    protected void onDestroy() {
        wl.release();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new InitilizeAsyncTask().execute();
    }

    private void WrongPwd(final SyncEntity<UserDto> entity){
		AlertDialog.Builder builder = new AlertDialog.Builder(FormData.this);
		builder.setTitle("Warning");
		builder
		.setMessage(entity.getInfo())
		.setCancelable(false)
		.setPositiveButton("From Server",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				remoteRequest(entity.getInfo());
				System.out.println("after remoteRequest");
				
			}
		  })
		.setNegativeButton("Retry",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		// create alert dialog and show it
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CHANGEPWD){
			if(resultCode == RESULT_OK){
				if(data != null){dto = FromBundle(data.getExtras(), dto);}
					
				dto.setIsPasswordChanged(true);
				try {					
					UserType t = (dto.getUserType() == 2 ? UserType.TDR : UserType.Admin);
					Repositoryregistry.get(IDUserRepository.class, dataManager).save(new DUserE(dto.getId(), dto.getUsername(), dto.getPassword(), 
							dto.getFullname(), t, dto.getEmail(), dto.getPhoneNumber(), dto.getClientId(),
							dto.getLocationId(), dto.getClientName(), dto.isIsPasswordChanged(),
							dto.isIsSecuritySet(), dto.getSecurityQuestionId(), dto.getSecurityAnswer()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				securityQuestion(dto);
			}
		}else if(requestCode == SECURITYQ){
			if(resultCode == RESULT_OK){
				dto.setIsSecuritySet(true);
				try {
					UserType t = (dto.getUserType() == 2 ? UserType.TDR : UserType.Admin);
					Repositoryregistry.get(IDUserRepository.class, dataManager).save(new DUserE(dto.getId(), dto.getUsername(), dto.getPassword(), 
							dto.getFullname(), t, dto.getEmail(), dto.getPhoneNumber(), dto.getClientId(),
							dto.getLocationId(), dto.getClientName(), dto.isIsPasswordChanged(),
							dto.isIsSecuritySet(), dto.getSecurityQuestionId(), dto.getSecurityAnswer()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				SyncEntity<UserDto> entity = new SyncEntity<UserDto>();
				entity.Status = true;
				List<UserDto> list = new ArrayList<UserDto>();
				list.add(dto);
				entity.setData(list);
				moveToNext(entity);
			}
		}
		
	}
	
	private void moveToNext(SyncEntity<UserDto> entity){
		System.out.println("in moveToNext");
		if(entity.getStatus()){
			dto = entity.getData().get(0);
			System.out.println(dto.toString());
			Bundle bundle = ToBundle( new Bundle(), dto);/*
			bundle.putString(CLIENTID, dto.getClientId().toString());
			bundle.putString(USERID, dto.getId().toString());
			bundle.putString(CLIENTNAME, dto.getClientName());
			bundle.putString(FormListActivity.USERNAME, dto.getUsername());
			bundle.putString(FormListActivity.LOCATIONID, dto.getLocationId().toString());*/
			Intent intent = new Intent(FormData.this, Home.class);
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
			setURL();
			return true;
		}else if(itemId == R.id.action_reset){
			resetPWD();
			return true;
		}else if(itemId == R.id.action_message){
			getMessages();
			return true;
		}else if(itemId == R.id.action_reset_db){
			RestDB();
			return true;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	void RestDB(){
		AlertDialog.Builder builder = new AlertDialog.Builder(FormData.this);
		builder.setTitle("Warning");
		builder
		.setMessage("This will Delete all Data. Ensure there are no unsent responses")
		.setCancelable(false)
		.setPositiveButton("Continue",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				try {
					delete();
				} catch (Exception e) { e.printStackTrace(); }
				dialog.cancel();
			}
		  })
		.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		// create alert dialog and show it
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@SuppressWarnings("unchecked")
	void delete() throws Exception{
		for (RepositoryEnum dir : EnumSet.allOf(RepositoryEnum.class)) {
			IRepositoryBase repo = Repositoryregistry.get(dir.IRepo, dataManager);
			if(repo instanceof IPhonConfig){
				((IPhonConfig)repo).allowEdit();
			}else{ 
				repo.DeleteAll();
			}
		}
	}
	
	private void doSync(){
		if(validate()){
			SyncEntity<UserDto>entity = Login();
			if(entity.getStatus())
				new SyncTask().execute(entity.getData().get(0).getClientId().toString());
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
			IPhonConfig config = Repositoryregistry.get(IPhonConfig.class, dataManager);
			try {
				PhonConfig pc = config.getConfig();
				if(pc == null){
					PhonConfig config2 = new PhonConfig();
					config2.setId(UUID.randomUUID());
                    //config2.setURL("http://test.icoders-solution.com");
					//config2.setURL("http://equity.icoders-solution.com");
					//config2.setURL("http://10.0.0.194:62093");
					config2.setURL("http://topimage.icoders-solution.com");
					config2.setEdit(false);
					config.save(config2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	private class SyncTask extends AsyncTask<String, Void, String[]>{

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
					Repositoryregistry.get(IDUserRepository.class, dataManager), dataManager, 
					Repositoryregistry.get(ISecurityQuestionRepository.class, dataManager),
					Repositoryregistry.get(IDTownRepository.class, dataManager));
			try {
				Log.d("SyncService", "SyncForm() called with params.length: "+ params.length);
				if(loginService.SyncForm(params)){
					return params;
				}else{
					return null;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] returns) {
			if(dialog == null)return;
			else if(dialog.isShowing())dialog.dismiss();
			if(returns != null && returns.length > 1){
				Log.d("SyncService", "onPostExecute() : "+ returns.length);
				try {
					SyncEntity<UserDto> entity = Repositoryregistry.get(IDUserRepository.class, dataManager).login(
							username.getText().toString(), MakePWD.getMD5(password.getText().toString()));
					progressAfterLogin(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				Log.d("SyncService", "onPostExecute() some problem");
				Toast.makeText(FormData.this, (String)CloudManager.getObject(CloudConstants.LOGINERROR.value), 
						Toast.LENGTH_LONG).show();
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
				new SyncTask().execute(username.getText().toString(), pwd);
			}
		});
		dialog.show();
	}
	
	private void setURL(){
		final Dialog dialog = new Dialog(FormData.this);
		LayoutInflater infalInflater = (LayoutInflater) FormData.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = infalInflater.inflate(R.layout.url_settings, null);
		dialog.setContentView(view);
		dialog.setTitle("Settings");
        final String http = "http://";
		final IPhonConfig phonConfig = Repositoryregistry.get(IPhonConfig.class, dataManager);
		final EditText urlEdit = (EditText)view.findViewById(R.id.url_input);
        urlEdit.setText(http);
		try {
			config = phonConfig.getConfig();
			if(config != null && config.getURL() != null && !config.getURL().equals("")){
				if(!config.isEdit()){
					urlEdit.setEnabled(false);
					urlEdit.setFocusable(false);
				}
				Log.d("setURL", "config != null: "+config.getURL());
				((TextView)view.findViewById(R.id.url_text)).setText(config.getURL());
			}else {
				Log.d("setURL", "config == null");
				((TextView)view.findViewById(R.id.url_text)).setText("Not Set");
			}
		} catch (Exception e) { e.printStackTrace(); }
		(view.findViewById(R.id.btn_cancel)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		(view.findViewById(R.id.btn_ok)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Object object = urlEdit.getText();
				if(object == null || object.toString().equals("") || object.toString().endsWith(http)){
					Toast.makeText(FormData.this, "Must enter url", Toast.LENGTH_LONG).show();
				}else{
					Log.d("setURL", "config != null: " + object.toString());
					dialog.dismiss();					
					if(config == null){
						config = new PhonConfig();
						config.setId(UUID.randomUUID());
					}
					config.setURL(object.toString());
					try {
						config.setEdit(false);
						phonConfig.save(config);
					} catch (Exception e) { e.printStackTrace(); }
				}
			}
		});
		dialog.show();
	}
	
	private void progressAfterLogin(SyncEntity<UserDto> entityEnv){
		UserDto entity = entityEnv.getData().get(0);
		if(!entity.isIsPasswordChanged()){
			changePWD(entity);
		}else if(!entity.isIsSecuritySet()){
			securityQuestion(entity);
		}else{
			moveToNext(entityEnv);
		}
	}
	
	private void changePWD(UserDto dto){
		System.out.println("in changePWD");
		System.out.println(dto.toString());
		Bundle bundle = ToBundle( new Bundle(), dto);
		
		Intent intent = new Intent(FormData.this, ChangePassword.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, CHANGEPWD);
	}
	
	private void securityQuestion(UserDto dto){
		System.out.println("in securityQuestion");
		System.out.println(dto.toString());
		Bundle bundle = ToBundle( new Bundle(), dto);
		
		Intent intent = new Intent(FormData.this, SetSecurityQuestion.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, SECURITYQ);
	}
	
	private void resetPWD(){
		System.out.println("in resetPWD");
		
		Intent intent = new Intent(FormData.this, ResetPassword.class);
		//intent.putExtras(bundle);
		startActivityForResult(intent, RESETPASSWORD);
	}
	
	private void getMessages(){
		System.out.println("in getMessages");
		Intent intent = new Intent(FormData.this, MessageNotification.class);
		Bundle bundle = new Bundle();
		bundle.putInt("messageType", MessageTypes.Password.value);
		intent.putExtras(bundle);
		startActivityForResult(intent, MESSAGES);
	}
}
