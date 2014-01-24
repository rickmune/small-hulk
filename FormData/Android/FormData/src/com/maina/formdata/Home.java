/**
 * 
 */
package com.maina.formdata;

import java.util.ArrayList;
import java.util.EnumSet;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.maina.formdata.entity.PhonConfig;
import com.maina.formdata.enums.RepositoryEnum;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.IRepositoryBase;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.ui.HomeActivity;
import com.maina.formdata.ui.InforActivity;
import com.maina.formdata.utils.SyncAsyncTask;
import com.maina.formdata.utils.ui.GridviewAdapter;

/**
 * @author Patrick
 *
 */
public class Home extends BaseActivity{

	private GridviewAdapter mAdapter;
	private ArrayList<String> listName;
	private ArrayList<Integer> listIcons;
	private GridView gridView;
	String ClientId, UserId, Username, LocationId, ClientName;
	PhonConfig config;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setTitle("Home");
        if(savedInstanceState == null) restore(getIntent().getExtras());
        prepareList();
        mAdapter = new GridviewAdapter(this,listName, listIcons);
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new IconClicked());
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(FormData.CLIENTID, ClientId);
		outState.putString(FormData.USERID, UserId);
		outState.putString(FormListActivity.USERNAME, Username);
		outState.putString(FormListActivity.LOCATIONID, LocationId);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		restore(savedInstanceState);
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	void restore(Bundle bundle){
		ClientId = bundle.getString(FormData.CLIENTID);
		UserId = bundle.getString(FormData.USERID);
		Username = bundle.getString(FormListActivity.USERNAME);
		LocationId = bundle.getString(FormListActivity.LOCATIONID);
		ClientName = bundle.getString(FormData.CLIENTNAME);
	}
	
	class IconClicked implements GridView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Toast.makeText(Home.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
			if(position == 0){
				System.out.println("in moveToNext");
				Intent intent = new Intent(Home.this, FormListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(FormData.CLIENTID, ClientId);
				bundle.putString(FormData.USERID, UserId);
				bundle.putString(FormListActivity.USERNAME, Username);
				bundle.putString(FormListActivity.LOCATIONID, LocationId);
				intent.putExtras(bundle);
				startActivity(intent);
			}else if(position == 1){//Infor
				Intent intent = new Intent(Home.this, InforActivity.class);
				startActivity(intent);
			}else if(position == 2){//Details
				Intent intent = new Intent(Home.this, HomeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(FormData.CLIENTID, ClientId);
				bundle.putString(FormData.CLIENTNAME, ClientName);
				bundle.putString(FormData.USERID, UserId);
				bundle.putString(FormListActivity.USERNAME, Username);
				bundle.putString(FormListActivity.LOCATIONID, LocationId);
				intent.putExtras(bundle);
				startActivity(intent);
			}else if(position == 3){//Settings
				changeURL();
			}else if(position == 4){//Reset
				reset();
			}else if(position == 5){
				doSync();
			}
		}
		
	}
	
	private void doSync(){
		SyncAsyncTask asyncTask = new SyncAsyncTask(Home.this, dataManager, 
				new ProgressDialog(Home.this));
		asyncTask.execute(new String[]{ClientId});
	}
	
	private void prepareList()
    {
    	  listName = new ArrayList<String>();
    	  
    	  listName.add("Forms");
    	  listName.add("Infor");
    	  listName.add("Details");
    	  
    	  listName.add("Settings");
    	  listName.add("Reset");
          
          
          listName.add("Sync");
          
          listIcons = new ArrayList<Integer>();
          
          listIcons.add(R.drawable.fd_logo);
          listIcons.add(R.drawable.infor);
          listIcons.add(R.drawable.user);
          
          listIcons.add(R.drawable.settings);          
          listIcons.add(R.drawable.reset);
          
          listIcons.add(R.drawable.sync);
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
		.setMessage("This will Delete all Data. Ensure there are no unsent responses")
		.setCancelable(false)
		.setPositiveButton("Continue",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				try {
					delete();
				} catch (Exception e) { e.printStackTrace(); }
				dialog.cancel();
				finish();
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
}
