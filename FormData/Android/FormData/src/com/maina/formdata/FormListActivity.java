package com.maina.formdata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.ui.FormItemActivity;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;
import com.maina.formdata.utils.ui.CustomCursorAdapter;
import com.maina.formdata.utils.ui.ListDialog;

public class FormListActivity extends BaseActivity {

	private static final String TAG = "FormListActivity";
	@SuppressWarnings("rawtypes")
	private Class classInstance = null;
	private Method methodInstance = null;
	@SuppressWarnings("rawtypes")
	private Class argTypes = List.class;
	ListView listView;
	String ClassName = null;
	String MethodName = null;
	List<String> Params = null;
	Cursor cursor;
	private ProgressDialog dialog;
	private CustomCursorAdapter customAdapter;
	public static final int RespondentTypeId = 200;
	private UUID formId;
	String formName, UserName, LocationId;
	public static final String RESPONDENTTYPE = "_RESPONDENTTYPE";
	public static final String FORMID = "_FORMID";
	public static final String FORMNAME = "_FORMNAME";
	public static final String USERNAME = "_USERNAME";
	public static final String LOCATIONID = "_LOCATIONID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_list);
		setTitle("Forms");
		Log.d("FormListActivity", "onCreate (dataManager== null): "+(dataManager== null));
		Bundle bundle = getIntent().getExtras();
		UserName = bundle.getString(USERNAME);
		LocationId = bundle.getString(LOCATIONID);
		listView = (ListView)findViewById(android.R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View ListDialog, int position, long id) {
				TextView v =(TextView) ListDialog.findViewById(R.id.list_name);
				Log.d(TAG, "clicked on item: " + position+ " : "+v.getTag());
				formId = UUID.fromString(v.getTag().toString());
				formName = (String) v.getText();
				chooseRespondentType();
			}
		});
        try {
			classInstance =  Class.forName("com.maina.formdata.repository.IDFormRepository");
			MethodName = "getForms";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        new ProgressAsyncTask().execute();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.form_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("FormListActivity", "onOptionsItemSelected");
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("unchecked")
	private Cursor getCursor(){
		Cursor cursor = null;
		try {
			methodInstance = this.classInstance.getDeclaredMethod(MethodName, argTypes);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			cursor = (Cursor)methodInstance.invoke(Repositoryregistry.get(classInstance, 
					(IDataManager)CloudManager.getObject(CloudConstants.DATABASEMANAGER.value)), Params);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return cursor;
	}
	
	private class ProgressAsyncTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(FormListActivity.this);
			dialog.setMessage("Loading");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params){
			cursor = getCursor();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voids) {
			if(dialog == null)return;
			else if(dialog != null && dialog.isShowing())dialog.dismiss();
			customAdapter = new CustomCursorAdapter(FormListActivity.this, cursor);
            listView.setAdapter(customAdapter);
            startManagingCursor(cursor);
            startService(new Intent(FormListActivity.this, SyncService.class));
		}
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
	
	private void chooseRespondentType(){
		ArrayList<String> map = new ArrayList<String>();
		map.add("DformE_id"); 
		map.add(formId.toString());
		Intent intent = new Intent(this, ListDialog.class);
		Bundle bundle = new Bundle();
		bundle.putString("ClassName", "com.maina.formdata.repository.IDFormRespondentTypeRepository");
		bundle.putString("MethodName", "getByFormId");
		bundle.putString("title", "Select Respondent");
		bundle.putStringArrayList("Params", map);
		intent.putExtras(bundle);
		startActivityForResult(intent, RespondentTypeId);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if (requestCode == RespondentTypeId) {
				String result = data.getStringExtra(ListDialog.RESULTID);
				Log.d(TAG, "RespondentType: "+data.getStringExtra(ListDialog.RESULTNAME));
				Intent intent = new Intent(FormListActivity.this, FormItemActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(FORMID, formId.toString());
				bundle.putString(FORMNAME, formName);
				bundle.putString(RESPONDENTTYPE, result);
				bundle.putString(USERNAME, UserName);
				bundle.putString(LOCATIONID, LocationId);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(FORMID, formId.toString());
		outState.putString(FORMNAME, formName);
		outState.putString(USERNAME, UserName);
		outState.putString(LOCATIONID, LocationId);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		formName = savedInstanceState.getString(FORMNAME);
		UserName = savedInstanceState.getString(USERNAME);
		LocationId = savedInstanceState.getString(LOCATIONID);
		try {
			formId = UUID.fromString(savedInstanceState.getString(FORMID));
		} catch (Exception e) { e.printStackTrace();}
		
		super.onRestoreInstanceState(savedInstanceState);
	}
}
