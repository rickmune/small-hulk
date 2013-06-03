package com.safapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.safapp.androidclient.R;

public class AdminDash extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.admindashboard, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Button)getActivity().findViewById(R.id.admin_dashboard_categories)).setOnClickListener(new AdminDashButtonListener());
		((Button)getActivity().findViewById(R.id.admin_dashboard_products)).setOnClickListener(new AdminDashButtonListener());
	}
	
	class AdminDashButtonListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			Intent intent = null;
			switch (view.getId()) {
			case R.id.admin_dashboard_categories:
				intent = new Intent(getActivity(), AddCategory.class);
				break;
			case R.id.admin_dashboard_products:
				intent = new Intent(getActivity(), AddProduct.class);
				break;
			default:
				break;
			}
			if(intent != null)
				startActivity(intent);
		}		
	}
}
