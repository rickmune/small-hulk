package com.maina.formdata.ui;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.maina.formdata.FormListActivity;
import com.maina.formdata.R;
import com.maina.formdata.repository.IDTownRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.utils.ui.ListDialogSearchable;

public class SelectTown extends BaseActivity{
	
	private static final String TAG = ",SelectTown";
	private static final int GETTOWN = 101; 
	private String UserName, LocationId;//, ClientId, UserId;
	protected Button btnSpinner;
	
	String ClassName = null;
	String MethodName = null;
	List<String> Params = null;
	LinearLayout buttonHolder;
	private ProgressDialog dialog;
	LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.WRAP_CONTENT
    );
	ChildrenAvailable childrenAvailable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("SelectTown", "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_town);
		setTitle("Select Town");
		
		Bundle bundle = getIntent().getExtras();		
		UserName = bundle.getString(FormListActivity.USERNAME);
		LocationId = bundle.getString(FormListActivity.LOCATIONID);
		//ClientId = bundle.getString(FormData.CLIENTID);
		//UserId = bundle.getString(FormData.USERID);
				
		buttonHolder = (LinearLayout)findViewById(R.id.button_holder);
		
		((Button)findViewById(R.id.town_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showSList();
			}
		});
		
		((Button)findViewById(R.id.town_cancel)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		((Button)findViewById(R.id.town_ok)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveToNext();
			}
		});
	
		childrenAvailable = new ChildrenAvailable();
	}
	
	protected void onResume(){
		super.onResume();
		if(dialog == null)
			dialog = new ProgressDialog(SelectTown.this);
		if(childrenAvailable.getStatus() == AsyncTask.Status.PENDING)
			childrenAvailable.execute(true);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(FormListActivity.USERNAME, UserName);
		outState.putString(FormListActivity.LOCATIONID, LocationId);
		super.onSaveInstanceState(outState);
	}	
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		UserName = savedInstanceState.getString(FormListActivity.USERNAME);
		LocationId = savedInstanceState.getString(FormListActivity.LOCATIONID);
		
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if (requestCode == GETTOWN){
				LocationId = data.getStringExtra(ListDialogSearchable.RESULTID);
				String name = data.getStringExtra(ListDialogSearchable.RESULTNAME);
				Log.d(TAG, "Location Name: " + name + " Location Id: " + LocationId );
				if(btnSpinner != null){
					btnSpinner.setText(name);
				}else{
					((Button)findViewById(R.id.town_btn)).setText(name);
				}
				try{
					new ChildrenAvailable().execute(false);
				}catch(Exception e){e.printStackTrace();}
				
			}
		}
	}
	
	private void moveToNext(){
		System.out.println("in moveToNext");
		Bundle bundle = new Bundle();
		//bundle.putString(FormData.CLIENTID, ClientId);
		//bundle.putString(FormData.USERID, UserId);
		bundle.putString(FormListActivity.USERNAME, UserName);
		bundle.putString(FormListActivity.LOCATIONID, LocationId);
		Intent intent = new Intent(SelectTown.this, FormListActivity.class);
		intent.putExtras(bundle);
		//startActivity(intent);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void showSList(){
		String sql = "SELECT id as _id, Name as name from DTownE tf" +
				" WHERE tf.ParentId = '" + LocationId + "'" +
				" ORDER BY name asc";
		Intent intent = new Intent(this, ListDialogSearchable.class);
		Bundle bundle = new Bundle();
		bundle.putString(ListDialogSearchable.TITLE, "Select Town");
		bundle.putString(ListDialogSearchable.QUERYSTRING, sql);
		intent.putExtras(bundle);
		startActivityForResult(intent, GETTOWN);
	}

	class ChildrenAvailable extends AsyncTask<Boolean, Void, Integer>{

		boolean move;
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Loading");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected Integer doInBackground(Boolean... params) {
			Log.d(TAG, "doInBackground params[0]: " +params[0] + " LocationId: " +LocationId);
			move = params[0];
			return Repositoryregistry.get(IDTownRepository.class, dataManager).ChildrenAvailble(LocationId);
		}
		
		@Override
		protected void onPostExecute(Integer num) {
			if(dialog == null)return;
			else if(dialog != null && dialog.isShowing())dialog.dismiss();
			Log.d(TAG, "onPostExecute params[0]: " +move + " Availble Children: " + num );
			if(move){
				if(num <= 0){
					moveToNext();
				}
			}else{
				if(num > 0){
					addChildBtn();
				}
			}
		}
		
	}
	
	void addChildBtn(){
		btnSpinner = new Button(SelectTown.this);
		btnSpinner.setText("Select Town");
		//btnSpinner.set
		btnSpinner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showSList();
			}
		});
		buttonHolder.addView(btnSpinner);
	}
}
