package com.safapp.ui.utils;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerArrayAdapter extends ArrayAdapter<String []> {

	private List<String[]> toDisplay = null;
	
	public SpinnerArrayAdapter(Context context, int textViewResourceId, List<String[]> objects) {
		super(context, textViewResourceId, objects);
		toDisplay = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ((TextView) v).setTextSize(30);
        ((TextView) v).setTextColor(color.white);
        ((TextView) v).setSelectAllOnFocus(true);
        return v;
    }
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        ((TextView) v).setTextColor(color.white);
        ((TextView) v).setGravity(Gravity.CENTER);
        ((TextView) v).setText(toDisplay.get(position)[0]);
        ((TextView) v).setTag(toDisplay.get(position)[1]);
        return v;
    }
}
