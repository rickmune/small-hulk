package com.maina.formdata.utils.ui;

import java.util.ArrayList;

import com.maina.formdata.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter
{
	private ArrayList<String> listNames;
	private ArrayList<Integer> listIcons;
	private Activity activity;
	
	public GridviewAdapter(Activity activity,ArrayList<String> listName, ArrayList<Integer> listIcon) {
		super();
		this.listNames = listName;
		this.listIcons = listIcon;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		return listNames.size();
	}

	@Override
	public String getItem(int position) {
		return listNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static class ViewHolder
	{
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();
		if(convertView==null){
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.gridview_row, null);
			view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
			view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(view);
		}else{
			view = (ViewHolder) convertView.getTag();
		}		
		view.txtViewTitle.setText(listNames.get(position));
		view.imgViewFlag.setImageResource(listIcons.get(position));

		return convertView;
	}

}
