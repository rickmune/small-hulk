package com.safapp.ui.activities.sales;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.safapp.androidclient.R;
import com.safapp.ui.utils.ScrollTab;
import com.safapp.ui.utils.ScrollTabsAdapter;
import com.safapp.ui.utils.TabAdaptors;
import com.safapp.ui.utils.TitleProvider;

public class Sales extends FragmentActivity implements ScrollTab.OnSwipeTabs{

	private final static String Tag = "Sales ";
	private ScrollTab mTabs;
	ViewPager mViewPager;
	SwipeyTabsPagerAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salestab_activity);
		Log.d(Tag, "onCreate");
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mTabs = (ScrollTab) findViewById(R.id.swipeytabs);
		adapter = new SwipeyTabsPagerAdapter(getSupportFragmentManager(), this);
		adapter.addTab(SalesFrag.class, null);
		adapter.addTab(SalesSummary.class, null);
		mViewPager.setAdapter(adapter);
		mTabs.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(mTabs);
		mViewPager.setCurrentItem(0);
	}
	
	@Override
	public void refresh(int position) {
		
	}
	
	private class SwipeyTabsPagerAdapter extends TabAdaptors implements TitleProvider, ScrollTabsAdapter{

		public SwipeyTabsPagerAdapter(FragmentManager fm, Context Context) {
			super(fm, Context);
		}
		
		@Override
		public String getTitle(int position) {
			switch (position) {
			case 0:
				return "Sales";
			case 1:
				return "Summary";
			default:
				return "";
			} 	
		}

		@Override
		public TextView getTab(final int position, ScrollTab root) {
			TextView view = (TextView) LayoutInflater.from(mContext).inflate(
					R.layout.scroll_tab_indicator, root, false);
			view.setText(getTitle(position));
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mViewPager.setCurrentItem(position);
				}
			});
			
			return view;
		}
	}
	
}
