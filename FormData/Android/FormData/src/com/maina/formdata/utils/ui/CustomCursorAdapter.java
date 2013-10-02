package com.maina.formdata.utils.ui;

import com.maina.formdata.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c, false);
		Log.d("CustomCursorAdapter","CustomCursorAdapter");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String id = cursor.getString(cursor.getColumnIndex("_id"));
		String nameItem = cursor.getString(cursor.getColumnIndex("name"));
		TextView code = (TextView) view.findViewById(R.id.list_code);
		code.setTag(id);
		TextView name = (TextView) view.findViewById(R.id.list_name);
		name.setText(nameItem);
		name.setTag(id);
		Log.d("CustomCursorAdapter","bindView nameItem: "+nameItem+" id: "+id);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_item_row, parent, false);
        return retView;
	}
}
