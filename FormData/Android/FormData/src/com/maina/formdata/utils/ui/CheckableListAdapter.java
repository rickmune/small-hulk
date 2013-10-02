package com.maina.formdata.utils.ui;

import java.util.ArrayList;

import com.maina.formdata.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class CheckableListAdapter  extends SimpleCursorAdapter{

	private final static String TAG = "CheckableListAdapter";
	private final int layout;
	Cursor mCursor;
	private  ArrayList<ViewModel> itemChecked;
	protected LayoutInflater mLayoutInflater;
	
	public CheckableListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.layout = layout;
		mCursor = c;
		mLayoutInflater = LayoutInflater.from(context);
		swapCursor(c);
	}
	
	@Override
	public int getCount() {
		int count =0;
		try{
			count =(mCursor == null ? 0: mCursor.getCount());
		}catch(Exception e){
			Log.d(TAG,"Exception while getting count",e);
		}
		Log.d(TAG,"getCount:count is: "+count);
		return count;
	}
	
	public ArrayList<ViewModel> getItemchecked() {
		return itemChecked;
	}
	
	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	        final ViewHolder viewHolder;
	        final int pos = position;
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(layout, null);
				viewHolder = new ViewHolder();
				viewHolder.text = (TextView) convertView.findViewById(R.id.cont_name);
				viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.check);
				viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								Log.d(TAG, "onCheckedChanged: "+buttonView.getTag()+" state: "+isChecked+
										" pos: "+pos);
								ViewModel model = itemChecked.get(pos);
								model.setChecked(isChecked);
								itemChecked.set(pos, model);
								Log.d(TAG, "itemChecked.size(): "+itemChecked.size());
							}
						});
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
	        }
			mCursor.moveToPosition(position);
			String tag = null;
	    	try {
				tag = mCursor.getString(mCursor.getColumnIndex("_id"));
			}catch(Exception e) {
				Log.d(TAG, "SQL Error or _id not exist!", e);
			}
	    	viewHolder.text.setText(mCursor.getString(mCursor.getColumnIndex("name")));
	    	viewHolder.text.setTag(tag);
	    	viewHolder.checkbox.setTag(tag);

	        /*if(itemChecked.get(position).isChecked() == true){
	        	viewHolder.checkbox.setChecked(true);
	        }else{
	        	viewHolder.checkbox.setChecked(false);
	        }*/
	        return convertView;
	 }
	
	@Override
	public Cursor swapCursor(Cursor c) {
		itemChecked = new ArrayList<ViewModel>();
		if (c != null && c.getCount() > 0) {
			Log.d(TAG, "checkBox: count not 0: " + c.getCount());
			c.moveToFirst();
			while(!c.isAfterLast()){
				Log.d(TAG,"Cursor position: "+c.getPosition());
				itemChecked.add(c.getPosition(), new ViewModel(c.getString(c.getColumnIndex("_id")), false, 
						c.getString(c.getColumnIndex("name")),c.getPosition()));
				c.moveToNext();
			}
		}
		mCursor = c;
		return super.swapCursor(c);
	}
	
	public class ViewModel{
		private String _id;
		private boolean checked;
		private String name;
		private int position;
		public ViewModel(String _id, boolean checked,String name, int position){
			this._id = _id;
			this.checked = checked;
			this.name = name;
			this.position = position;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public String get_id() {
			return _id;
		}
		public void set_id(String _id) {
			this._id = _id;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
	}
}
