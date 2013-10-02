package com.maina.formdata.utils.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.maina.formdata.R;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;
import com.maina.formdata.utils.ui.CheckableListAdapter.ViewModel;

public class CheckableListDialog extends BaseActivity {

	private static final String TAG = "CheckableListDialog";
	private CheckableListAdapter checkableListAdapter;
	@SuppressWarnings("rawtypes")
	private Class classInstance = null;
	private Method methodInstance = null;
	@SuppressWarnings("rawtypes")
	private Class argTypes = List.class;
	ListView listView;
	String ClassName = null;
	String MethodName = null;
	List<String> Params = null;
	public static final String RESULTID = "ListDialog_RESULTID";
	public static final String RESULTNAME = "ListDialog_RESULTNAME";
	Cursor cursor;
	private ProgressDialog dialog;
	private Button done;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.multi_list_util);
        done = (Button)findViewById(R.id.done_check);
        done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<ViewModel> list = checkableListAdapter.getItemchecked();
				Log.d(TAG,"onClick list.size(): "+list.size());
				String items = null;
				String name = null;
				for(ViewModel model : list){
					if(model.isChecked()){
						if(items == null)
							items = model.get_id().toString();
						else
							items += ","+model.get_id().toString();
						if(name == null)
							name = model.getName();
						else
							name += ","+model.getName();
					}
				}
				Intent mIntent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString(RESULTID, name);
				bundle.putString(RESULTNAME, items);
			    mIntent.putExtras(bundle);
			    setResult(RESULT_OK, mIntent);
			    finish();
			}
		});
        setResult(Activity.RESULT_CANCELED);
        Bundle bundle = getIntent().getExtras();
        ClassName = bundle.getString("ClassName");
        MethodName = bundle.getString("MethodName");
        Params = bundle.getStringArrayList("Params");
        String title = bundle.getString("title");
        ((TextView)findViewById(R.id.list_name)).setText(title);
        try {
			classInstance = Class.forName(ClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        Log.d(TAG,"ClassName: "+ClassName+" MethodName: "+MethodName);
        listView = (ListView)findViewById(android.R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View ListDialog, int position, long id) {
				ViewModel model = checkableListAdapter.getItemchecked().get(position);
				ViewGroup row = (ViewGroup)ListDialog;
				CheckBox check = (CheckBox) row.findViewById(R.id.check);
				check.toggle();
				model.setChecked(check.isChecked());
		        ((CheckableListAdapter)checkableListAdapter).getItemchecked().add(position,model);
		        TextView tv = (TextView)findViewById(R.id.cont_name);
		        Log.d(TAG,"onItemClick: "+ tv.getText().toString());
			}
		});
        new ProgressAsyncTask().execute();
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
			dialog = new ProgressDialog(CheckableListDialog.this);
			dialog.setMessage("Loading");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			cursor = getCursor();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voids) {
			if(dialog==null)return;
			else if(dialog != null && dialog.isShowing())dialog.dismiss();
			String [] from = {"_id", "name"};
			int [] to = {R.id.cont_id, R.id.cont_name};
			checkableListAdapter = new CheckableListAdapter(CheckableListDialog.this, 
					R.layout.checked_list_item, cursor, from, to, 0);
            listView.setAdapter(checkableListAdapter);
            startManagingCursor(cursor);
		}
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
	}
}
