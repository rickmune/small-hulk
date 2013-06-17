package com.safapp.ui.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class TabAdaptors  extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener, TitleProvider {

	protected List<Fragment> fragments=new ArrayList<Fragment>();
	protected final ArrayList<String> mTabs = new ArrayList<String>();
	protected final Context mContext;
	
	public TabAdaptors(FragmentManager fm, Context Context) {
		super(fm);
		mContext = Context;
	}

	public void addTab(Class<?> clss, Bundle args) {
	    mTabs.add(clss.getName());
	    
	    fragments.add(android.support.v4.app.Fragment.instantiate(mContext, mTabs.get(fragments.size()), args));
	    notifyDataSetChanged();
	}
	
	@Override
	public String getTitle(int position) {
		switch (position) {
		case 0:
			return "Containers";
		default:
			return "Purchase";
		}
            	
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}
}
