package com.safapp.utils.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.safapp.R;
import com.safapp.utils.RepositoryRegistry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ListDialog extends Activity {

	private static final String TAG = "ListDialog";
	private CustomCursorAdapter customAdapter;
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
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.list_util);
        setResult(Activity.RESULT_CANCELED);
        Bundle bundle = getIntent().getExtras();
        ClassName = bundle.getString("ClassName");
        MethodName = bundle.getString("MethodName");
        Params = bundle.getStringArrayList("Params");
        try {
			classInstance = Class.forName(ClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        Log.d("ListDialog","ClassName: "+ClassName+" MethodName: "+MethodName);
        listView = (ListView)findViewById(android.R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View ListDialog, int position, long id) {
				TextView v =(TextView) ListDialog.findViewById(R.id.list_name);
				Log.d(TAG, "clicked on item: " + position+ " : "+v.getTag());
				Intent mIntent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString(RESULTID, v.getTag().toString());
				bundle.putString(RESULTNAME, v.getText().toString());
			    mIntent.putExtras(bundle);
			    setResult(RESULT_OK, mIntent);
			    finish();
			}
		});
        new ProgressAsyncTask().execute();
        /*new Handler().post(new Runnable() {
            @SuppressWarnings("deprecation")
			@Override
            public void run() {
            	cursor = getCursor();
                customAdapter = new CustomCursorAdapter(ListDialog.this, cursor);
                listView.setAdapter(customAdapter);
                startManagingCursor(cursor);
            }
        });*/
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
			cursor = (Cursor)methodInstance.invoke(RepositoryRegistry.get(classInstance), Params);
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
			dialog = new ProgressDialog(ListDialog.this);
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
		
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Void voids) {
			if(dialog==null)return;
			else if(dialog != null && dialog.isShowing())dialog.dismiss();
			customAdapter = new CustomCursorAdapter(ListDialog.this, cursor);
            listView.setAdapter(customAdapter);
            startManagingCursor(cursor);
		}
	}
}
