package com.safapp.ui;

import com.safapp.androidclient.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class DashBoard extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dashboard, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Button)getActivity().findViewById(R.id.dashboard_purchases)).setOnClickListener(new DashButtonListener());
		((Button)getActivity().findViewById(R.id.dashboard_sales)).setOnClickListener(new DashButtonListener());
		((Button)getActivity().findViewById(R.id.dashboard_reports)).setOnClickListener(new DashButtonListener());
		((Button)getActivity().findViewById(R.id.dashboard_admin)).setOnClickListener(new DashButtonListener());
	}
	
	class DashButtonListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			Intent intent = null;
			switch (view.getId()) {
			case R.id.dashboard_purchases:
				
				break;
			case R.id.dashboard_sales:
				
				break;
			case R.id.dashboard_reports:
				
				break;
			case R.id.dashboard_admin:
				intent = new Intent(getActivity(), Admin.class);
				break;
			default:
				break;
			}
			if(intent != null)
				startActivity(intent);
		}		
	}

}
