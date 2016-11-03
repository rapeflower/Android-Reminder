package com.sc.reminder.ui.calendar.year;

import com.sc.reminder.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class YearViewPagerFragment extends Fragment{
	
	public static final int MIN_CALENDAR_YEAR = 1949;
	public static final int MAX_CALENDAR_YEAR = 2049;
	
	private ViewPager viewPager;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.year_viewpager, container, false);
		viewPager = (ViewPager) v.findViewById(R.id.year_pager); 
		viewPager.setAdapter(new YearPagerAdapter(getActivity()));
		viewPager.setCurrentItem(65);
		//viewPager.setCurrentItem(new Time().year - MIN_CALENDAR_YEAR );
		
		return v;
	}

}
