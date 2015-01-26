package com.maina.formdata.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.maina.formdata.R;
import com.maina.formdata.repository.IMessageRepository;
import com.maina.formdata.repository.Repositoryregistry;

public class MessageNotification extends BaseActivity{

	ListView listView;
	Cursor cursor;
	ProgressDialog dialog;
	CustomCursorAdapter customAdapter;
	int messageType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_notification);
		try {
			messageType = getIntent().getExtras().getInt("messageType");
		} catch (Exception e) {
			messageType = 100;
		}
		
		(findViewById(R.id.msg_dismiss_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		listView = (ListView)findViewById(android.R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View ListDialog, int position, long id) {
				
			}
		});
		
		new GetMessages().execute();
	}
	
	private class GetMessages extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(MessageNotification.this);
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
			customAdapter = new CustomCursorAdapter(MessageNotification.this, cursor);
            listView.setAdapter(customAdapter);
            startManagingCursor(cursor);
		}
	}
	
	private Cursor getCursor(){
		Cursor cursor = null;
		try {
			cursor = Repositoryregistry.get(IMessageRepository.class, dataManager).getMessages(messageType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}

	@Override
	protected void onDestroy() {
		Log.d("MessageNotification", "onDestroy()");
		super.onDestroy();
	}

	private class CustomCursorAdapter extends CursorAdapter {
		
		public CustomCursorAdapter(Context context, Cursor c) {
			super(context, c, false);
			Log.d("CustomCursorAdapter","CustomCursorAdapter");
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			String id = cursor.getString(cursor.getColumnIndex("_id"));
			String nameItem = cursor.getString(cursor.getColumnIndex("name"));
			String datein = cursor.getString(cursor.getColumnIndex("DateIn"));
			
			TextView date = (TextView) view.findViewById(R.id.msg_date);
			date.setText(datein);
			date.setTag(id);
			
			TextView pre = (TextView) view.findViewById(R.id.msg_pre_text);
			pre.setText((nameItem == null ? "" : (nameItem.split(":")[0])));
			pre.setTag(id);
			
			TextView name = (TextView) view.findViewById(R.id.msg_text);
			name.setText((nameItem == null ? "" : (nameItem.split(":")[1])));
			name.setTag(id);
			
			
			Log.d("CustomCursorAdapter","bindView nameItem: "+nameItem+" id: "+id);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	        View retView = inflater.inflate(R.layout.msg_items, parent, false);
	        return retView;
		}
	}
}
