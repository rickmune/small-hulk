package com.maina.formdata.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.maina.formdata.FormData;
import com.maina.formdata.FormListActivity;
import com.maina.formdata.R;
import com.maina.formdata.utils.SyncAsyncTask;

public class HomeActivity extends BaseActivity{
	
	private static final String TAG = "HomeActivity";
	String UserName, LocationId, ClientName, UserId, ClientId;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("HomeActivity","onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		setTitle("Details");
		Bundle bundle = getIntent().getExtras();
		UserName = bundle.getString(FormListActivity.USERNAME);
		LocationId = bundle.getString(FormListActivity.LOCATIONID);
		UserId = bundle.getString(FormData.USERID);
		ClientId = bundle.getString(FormData.CLIENTID);
		ClientName = bundle.getString(FormData.CLIENTNAME);
		TextView userName = (TextView)findViewById(R.id.user_name);
		TextView clientName = (TextView)findViewById(R.id.client_name);
		TextView lastSync = (TextView)findViewById(R.id.last_sync_name);
		userName.setText( UserName );
		clientName.setText( ClientName );
		lastSync.setText(lastSync.getTag().toString());
		((Button)findViewById(R.id.survey_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveToNext();
			}
		});
		((Button)findViewById(R.id.dismiss_button)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() isFinishing(): "+isFinishing());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.home_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected");
		int itemId = item.getItemId();
		if(itemId == R.id.action_sync){
			Log.d(TAG, "onOptionsItemSelected action_sync");
			doSync();
			return true;
		}else if(itemId == R.id.next){
			Log.d(TAG, "onOptionsItemSelected next");
			moveToNext();
			return true;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void doSync(){
		SyncAsyncTask asyncTask = new SyncAsyncTask(HomeActivity.this, dataManager, 
				new ProgressDialog(HomeActivity.this));
		asyncTask.execute(new String[]{ClientId});
	}
	
	private void moveToNext(){
		System.out.println("in moveToNext");
		Bundle bundle = new Bundle();
		bundle.putString(FormData.CLIENTID, ClientId);
		bundle.putString(FormData.USERID, UserId);
		bundle.putString(FormListActivity.USERNAME, UserName);
		bundle.putString(FormListActivity.LOCATIONID, LocationId);
		Intent intent = new Intent(HomeActivity.this, FormListActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
}
