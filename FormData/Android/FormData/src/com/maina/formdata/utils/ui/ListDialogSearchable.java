package com.maina.formdata.utils.ui;

import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.maina.formdata.R;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.utils.SortedArray;

public class ListDialogSearchable extends BaseActivity {

	public static final String RESULTID = "LListDialogSearchable_RESULTID";
	public static final String RESULTNAME = "ListDialogSearchable_RESULTNAME";
	public static final String QUERYSTRING = "ListDialogSearchable_QUERYSTRING";
	public static final String  TITLE = "ListDialogSearchable_TITLE";
	EfficientAdapternew adapternew;
	private ProgressDialog dialog;
	private String queryString;
	private SortedArray arrayList;
	private ListView summaryList;
	TextView title;
	EditText search;
	SearchTask searchingT;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.list_util_search);
        summaryList = (ListView)findViewById(R.id.list);
        title = (TextView)findViewById(R.id.list_name);
        search = (EditText)findViewById(R.id.list_name_search);
        searchingT = new SearchTask();
        search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(searchingT.getStatus() == AsyncTask.Status.RUNNING){
					searchingT.cancel(true);
				}
				if(searchingT.getStatus() == AsyncTask.Status.FINISHED)
					searchingT = new SearchTask();
				searchingT.execute(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
        setResult(Activity.RESULT_CANCELED);
        
        Bundle bundle = getIntent().getExtras();
        queryString = bundle.getString(QUERYSTRING);
        title.setText(bundle.getString(TITLE));
        
        adapternew = new EfficientAdapternew(ListDialogSearchable.this, new SortedArray());
        summaryList.setAdapter(adapternew);
        new DBTask().execute(queryString);
	}
	
	private class DBTask extends AsyncTask <String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			try {
				arrayList = dataManager.getListDataHolder(params[0]);
				System.out.println("doInBackground Done now Adding");
				if(arrayList == null) {
					arrayList = new SortedArray();
				}
				for(ListDataHolder holder : arrayList){
					System.out.println("ID: "+holder._id+" Value: "+holder.Value);
					adapternew.addItem(holder);
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voids) {
			if(dialog != null && dialog.isShowing())dialog.dismiss();
			
			//System.out.println("onPostExecute arrayList Size: "+arrayList.size());
			search.setVisibility(View.VISIBLE);
			adapternew.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			search.setVisibility(View.GONE);
			dialog = new ProgressDialog(ListDialogSearchable.this);
			dialog.setMessage("Loading Please Wait");
			dialog.show();
		}
	}

	public class EfficientAdapternew extends BaseAdapter implements Filterable {

		private SortedArray map;
		private LayoutInflater mInflater;
		
		public EfficientAdapternew(Context context,  SortedArray map) {
			Log.d("ListDialogSearchable","Enter In: EfficientAdapter()SortedArrayList: " + map.size());
			this.map = map;
			mInflater = LayoutInflater.from(context);
		}
		
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			Log.d("ListDialogSearchable","notifyDataSetChanged() Called");
		}
		
		public void addItem(ListDataHolder item){
			map.insertSorted(item);
		}
				
		@Override
		public int getCount() {
			return map.size();
		}

		@Override
		public ListDataHolder getItem(int positon) {
			return map.get(positon);
		}

		@Override
		public long getItemId(int positon) {
			return positon;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ListDataHolder dataHolder = getItem(position);
			SmallHolder smallH;
			if(convertView == null){
				smallH = new SmallHolder();
				convertView = mInflater.inflate(R.layout.list_item_row_search, null);
				smallH.textView = (TextView)convertView.findViewById(R.id.list_item_name);
				smallH.line = (TextView)convertView.findViewById(R.id.underline);
				convertView.setTag(smallH);
			}else {
				smallH = (SmallHolder) convertView.getTag();
			}
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView textV = ((SmallHolder) v.getTag()).textView;
					Intent mIntent = new Intent();
					Bundle bundle = new Bundle();
					UUID[] values = (UUID[]) textV.getTag();
					bundle.putString(RESULTID, values[0].toString());
					bundle.putString(RESULTNAME, textV.getText().toString());
				    mIntent.putExtras(bundle);
				    setResult(RESULT_OK, mIntent);
				    finish();
				}
			});
			smallH.textView.setText(dataHolder.Value);
			smallH.textView.setTag(new UUID[]{dataHolder._id});
			return convertView;
		}
		
		class SmallHolder{
			TextView textView;
			TextView line;
		}

		@Override
		public Filter getFilter() {
			return null;
		}
		
	}

	private class SearchTask extends AsyncTask <String, Void, Void>{

		private SortedArray searchArrayList;
		
		@SuppressLint("DefaultLocale")
		@Override
		protected Void doInBackground(String... params) {
			try {
				if(arrayList == null)arrayList = new SortedArray();
				if(params[0].equals("")){
					searchArrayList = arrayList;
				}else{
					searchArrayList = new SortedArray();
					for(ListDataHolder holder : arrayList){
						System.out.println("doInBackground Value: "+ holder.Value +" : "+params[0]);
						if(holder.Value.toLowerCase().startsWith(params[0].toLowerCase())){
							System.out.println("doInBackground Value: " +holder.Value);
							searchArrayList.insertSorted(holder);
						}
					}
				}				
				System.out.println("doInBackground Done now Adding: " +searchArrayList.size());
								
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voids) {
			if(dialog != null && dialog.isShowing())dialog.dismiss();
			if(searchArrayList == null) {
				searchArrayList = new SortedArray();
			}
			EfficientAdapternew adapter = new EfficientAdapternew(ListDialogSearchable.this, new SortedArray());
			for(ListDataHolder holder : searchArrayList){
				System.out.println("ID: "+holder._id+" Value: "+holder.Value);
				adapter.addItem(holder);
			}
			System.out.println("onPostExecute arrayList Size: "+arrayList.size());
			summaryList.setAdapter(adapter);
			//adapternew.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ListDialogSearchable.this);
			dialog.setMessage("Searching Please Wait");
			dialog.show();
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
}
