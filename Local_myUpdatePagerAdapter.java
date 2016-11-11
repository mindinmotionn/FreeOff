package com.freeoffer.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.fragments.LocalUpdateFragment;
import com.freeoffer.app.fragments.MyUpdateFragment;


import java.util.ArrayList;
import java.util.List;

public class Local_myUpdatePagerAdapter extends FragmentPagerAdapter {
	
	Bundle bundle = new Bundle();
	CustomDialog_Ok customDialog;
	private Context mycontext;
	
	private List<Fragment> fragments;

	public Local_myUpdatePagerAdapter(FragmentManager fm) {
		
		super(fm);
		
		this.fragments = new ArrayList<Fragment>();
		fragments.add(new LocalUpdateFragment());
		fragments.add(new MyUpdateFragment());
		
	}	
	
	@Override
	public Fragment getItem(int index) {

				
		/*switch (index) {
		case 0:
			// Top Rated fragment activity

			return new LocalUpdateFragment();
			
		case 1:
			// Games fragment activity
			return new MyUpdateFragment();
		
		}

		return null;*/
		return fragments.get(index);
	}
	

	
	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return fragments.size();
	}
	
	public void getData(Bundle bundle){
	    this.bundle = bundle;
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		Log.d("Rup", "the destroy item is " + position);
		super.destroyItem(container, position, object);
	}
	
}
