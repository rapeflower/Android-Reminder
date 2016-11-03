package com.sc.reminder.ui.calendar.day;

import java.util.ArrayList;
import java.util.HashMap;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.TripDao;
import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.ui.calendar.timeaxis.TimeAxisPagerAdapter;
import com.sc.reminder.view.trip.SearchActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewDebug.FlagToString;
import android.widget.TextView;

public class DayFragment extends Fragment{
	
	private Context context;
	private ViewPager week_pager;
	private ViewPager day_inifo_pager;
	private TextView dateView;
	private Time time;
	private String timeString;
	private Handler handler = new DayHandler();
	private WeekPagerAdapter weekPagerAdapter;
	private TimeAxisPagerAdapter timeAxisPagerAdapter;
	private int flag = 0;
	
	public DayFragment(Context context,Time t){
		this.context = context;
		this.time = t;
		timeString = Integer.toString(time.year) + "年" + Integer.toString(time.month + 1) +"月" + Integer.toString(time.monthDay) + "日";
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		new DayEventsThread().start();
		
		// TODO Auto-generated method stub
		View v =  inflater.inflate(R.layout.day_fragment, container,false);
		week_pager = (ViewPager) v.findViewById(R.id.week_pager);
		
		day_inifo_pager = (ViewPager) v.findViewById(R.id.day_inifo_pager);
		
		
		
		dateView = (TextView) v.findViewById(R.id.date);
		
		dateView.setText(timeString);
		
		weekPagerAdapter = new WeekPagerAdapter(context, time);
		timeAxisPagerAdapter = new TimeAxisPagerAdapter(context);
		/*week_pager.setAdapter(weekPagerAdapter);
		day_inifo_pager.setAdapter(timeAxisPagerAdapter);
		week_pager.setCurrentItem(40);
		day_inifo_pager.setCurrentItem(280);*/
		
		return v;
	}
	
	private class DayHandler extends Handler{
		
		private HashMap<String, ArrayList<Trip>> hashMap = new HashMap<String, ArrayList<Trip>>();
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			hashMap = (HashMap<String, ArrayList<Trip>>) msg.obj;
			timeAxisPagerAdapter.setFullDayTrips(hashMap.get("fullDayTrips"));
			timeAxisPagerAdapter.setDayTrips(hashMap.get("dayTrips"));
			
			week_pager.setAdapter(weekPagerAdapter);
			day_inifo_pager.setAdapter(timeAxisPagerAdapter);
			week_pager.setCurrentItem(40);
			day_inifo_pager.setCurrentItem(280);
			
			week_pager.setOnPageChangeListener(new OnPageChangeListener() {
				
				private int currentPosition = -1;
				//private boolean flag = true;
				private int state = -1;
				
				/**
				 * page改变
				 */
				@Override
				public void onPageSelected(int arg0) {
					flag++;
					// TODO Auto-generated method stub
					if(flag == 1){
						if(arg0 > currentPosition){
							day_inifo_pager.setCurrentItem(day_inifo_pager.getCurrentItem() + 7);
						}else if(arg0 < currentPosition){
							day_inifo_pager.setCurrentItem(day_inifo_pager.getCurrentItem() - 7);
						}else{
							
						}
						currentPosition = arg0;
					}else{
						flag = 0;
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
			});
			
			day_inifo_pager.setOnPageChangeListener(new OnPageChangeListener() {
				
				private int currentPosition = -1;
				//private boolean flag = true;
				
				@Override
				public void onPageSelected(int arg0) {
					flag++;
					if(flag == 1){
						if(arg0 > currentPosition){
							week_pager.setCurrentItem(week_pager.getCurrentItem() + 1);
						}else if(arg0 < currentPosition){
							week_pager.setCurrentItem(week_pager.getCurrentItem() - 1);
						}else{
							
						}
						currentPosition = arg0;
					}else{
						flag = 0;
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
			});
			
			super.handleMessage(msg);
		}
	}
	
	private class DayEventsThread extends Thread{
		
		private ArrayList<Trip> fullDayTrips = new ArrayList<Trip>();
		private ArrayList<Trip> dayTrips = new ArrayList<Trip>();
		private HashMap<String, ArrayList<Trip>> hashMap = new HashMap<String, ArrayList<Trip>>();
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			TripDao t = new TripDao(context);
			fullDayTrips = t.queryByAllDay("true");//
			dayTrips = t.queryByAllDay("false");
			hashMap.put("fullDayTrips", fullDayTrips);
			hashMap.put("dayTrips", dayTrips);
			Message message = new Message();
			
			message.obj = hashMap;
			handler.sendMessage(message);
			
			//super.run();
		}
	}

}
