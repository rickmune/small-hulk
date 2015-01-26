/**
 * 
 */
package com.maina.formdata;

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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maina.formdata.dto.UserDto;
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.entity.PhonConfig;
import com.maina.formdata.enums.RepositoryEnum;
import com.maina.formdata.enums.UserType;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.IRepositoryBase;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.ui.ChangePassword;
import com.maina.formdata.ui.HomeActivity;
import com.maina.formdata.ui.InforPieChartActivity;
import com.maina.formdata.ui.MessageNotification;
import com.maina.formdata.ui.SetSecurityQuestion;
import com.maina.formdata.utils.SyncAsyncTask;
import com.maina.formdata.utils.SyncEntity;
import com.maina.formdata.utils.ui.GridviewAdapter;

/**
 * @author Patrick
 *
 */
public class Home extends BaseActivity{

	public static final int PASSWORD = 120;
	public static final int MESSAGES = 501;
	
	private GridviewAdapter mAdapter;
	private ArrayList<String> listName;
	private ArrayList<Integer> listIcons;
	private GridView gridView;
    private String ClientId;//, UserId, Username, LocationId, ClientName;
    private PhonConfig config;
    private UserDto dto;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == PASSWORD){
			dto = FromBundle(data.getExtras(), dto);
			restore(dto);
		}else if(requestCode == FormData.CHANGEPWD){
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
				//securityQuestion(dto);
			}
		}else if(requestCode == FormData.SECURITYQ){
			if(resultCode == RESULT_OK){
				if(data != null){dto = FromBundle(data.getExtras(), dto);}
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
				//moveToNext(entity);
			}
		}
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setTitle("Home");
        if(savedInstanceState == null) {
        	dto = FromBundle(getIntent().getExtras(), dto);
			restore(dto);
        }
        prepareList();
        mAdapter = new GridviewAdapter(this,listName, listIcons);
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new IconClicked());
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState = ToBundle(outState, dto);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		dto = FromBundle(savedInstanceState, dto);
		restore(dto);		
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	void restore(UserDto dto){
		ClientId = dto.getClientId().toString();//
		/*UserId = dto.getId().toString();
		Username = dto.getUsername();
		LocationId = dto.getLocationId().toString();
		ClientName = dto.getClientName();*/
	}
	
	class IconClicked implements GridView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Toast.makeText(Home.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
			if(position == 0){
				System.out.println("in moveToNext");
				Intent intent = new Intent(Home.this, FormListActivity.class);
				//Intent intent = new Intent(Home.this, SelectTown.class);
				Bundle bundle = ToBundle(new Bundle(), dto);
				/*bundle.putString(FormData.CLIENTID, ClientId);
				bundle.putString(FormData.USERID, UserId);
				bundle.putString(FormListActivity.USERNAME, Username);
				bundle.putString(FormListActivity.LOCATIONID, LocationId);*/
				intent.putExtras(bundle);
				startActivity(intent);
			}else if(position == 1){//Infor
				//Intent intent = new Intent(Home.this, InforActivity.class);
				Intent intent = new Intent(Home.this, InforPieChartActivity.class);
				startActivity(intent);
			}else if(position == 2){//Details
				Intent intent = new Intent(Home.this, HomeActivity.class);
				Bundle bundle = ToBundle(new Bundle(), dto);
				/*bundle.putString(FormData.CLIENTID, ClientId);
				bundle.putString(FormData.USERID, UserId);
				bundle.putString(FormListActivity.USERNAME, Username);
				bundle.putString(FormListActivity.LOCATIONID, LocationId);*/
				intent.putExtras(bundle);
				startActivity(intent);
			}else if(position == 3){//Settings
				SettingsList().show();
				//changeURL();
			}/*else if(position == 4){//Reset
				reset();
			
			}*/else if(position == 4){
				doSync();
			}else if(position == 5){
				getMessages();
			}
		}
	}
	
	private Dialog SettingsList(){
		AlertDialog.Builder alertB =  new AlertDialog.Builder(Home.this);
		alertB.setTitle("Select Action");
		String[] items = {"Change Password", "Change Security Question", "Change URL"};
		
		ListView modeList = new ListView(this);
		ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_style, 
				android.R.id.text1, items);
		modeList.setAdapter(modeAdapter);
		alertB.setView(modeList);
		final Dialog alertD = alertB.create();
		modeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				alertD.dismiss();
				if(position == 0){
					changePWD();
				}else if(position == 1){
					securityQuestion();
				}else if(position == 2){
					changeURL();
				}
			}
		});
		return alertD;
	}
	
	private void doSync(){
		SyncAsyncTask asyncTask = new SyncAsyncTask(Home.this, dataManager, 
				new ProgressDialog(Home.this));
		asyncTask.execute(new String[]{ClientId});
	}
	
	private void prepareList(){
    	  listName = new ArrayList<String>();
    	  listName.add("Forms");
    	  listName.add("Infor");
    	  listName.add("Details");
    	  
    	  listName.add("Settings");
    	  //listName.add("Re-intiallize");
          listName.add("Sync");
          listName.add("Notifications");
          
          listIcons = new ArrayList<Integer>();
          
          listIcons.add(R.drawable.fd_logo);
          listIcons.add(R.drawable.infor);
          listIcons.add(R.drawable.user);
          
          listIcons.add(R.drawable.settings);          
          //listIcons.add(R.drawable.reset);
          
          listIcons.add(R.drawable.sync);
          listIcons.add(R.drawable.message_icon);
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
	
	private void reset(){
		AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
		builder.setTitle("Warning");
		builder
		.setMessage("Please Logout And access the menu to re-initillize the Application. First Ensure there are no unsent responses")
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				/*try {
					delete();
				} catch (Exception e) { e.printStackTrace(); }
				
				finish();*/
			}
		  });
		// create alert dialog and show it
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void changeURL(){
		final Dialog dialog = new Dialog(this);
		LayoutInflater infalInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = infalInflater.inflate(R.layout.url_settings, null);
		dialog.setContentView(view);
		dialog.setTitle("URL Settings");
		final IPhonConfig phonConfig = Repositoryregistry.get(IPhonConfig.class, dataManager);
		final EditText urlEdit = (EditText)view.findViewById(R.id.url_input);
		try {
			config = phonConfig.getConfig();
			if(config != null && config.getURL() != null && !config.getURL().equals("")){
				if(!config.isEdit()){
					urlEdit.setEnabled(false);
					urlEdit.setFocusable(false);
				}
				Log.d("setURL", "config != null: "+config.getURL());
				((TextView)view.findViewById(R.id.url_text)).setText(config.getURL());
				urlEdit.setText(config.getURL());
			}else {
				Log.d("setURL", "config == null");
				((TextView)view.findViewById(R.id.url_text)).setText("Not Set");
			}
		} catch (Exception e) { e.printStackTrace(); }
		
		((Button)view.findViewById(R.id.btn_cancel)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		((Button)view.findViewById(R.id.btn_ok)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Object object = urlEdit.getText();
				if(object == null || object.toString().equals("")){
					Toast.makeText(Home.this, "Must enter url", Toast.LENGTH_LONG).show();
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
	
	private void changePWD(){
		System.out.println("in changePWD");
		System.out.println(dto.toString());
		Bundle bundle = ToBundle( new Bundle(), dto);
		
		Intent intent = new Intent(Home.this, ChangePassword.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, FormData.CHANGEPWD);
	}
	
	private void securityQuestion(){
		System.out.println("in securityQuestion");
		System.out.println(dto.toString());
		Bundle bundle = ToBundle( new Bundle(), dto);
		
		Intent intent = new Intent(Home.this, SetSecurityQuestion.class);
		intent.putExtras(bundle);
		startActivityForResult(intent, FormData.SECURITYQ);
	}
	
	private void getMessages(){
		System.out.println("in getMessages");
		Intent intent = new Intent(Home.this, MessageNotification.class);
		Bundle bundle = new Bundle();
		bundle.putInt("messageType", 100);
		intent.putExtras(bundle);
		startActivityForResult(intent, MESSAGES);
	}
}
