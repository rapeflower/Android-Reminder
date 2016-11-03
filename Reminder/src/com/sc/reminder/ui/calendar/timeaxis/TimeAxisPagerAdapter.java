package com.sc.reminder.ui.calendar.timeaxis;

import java.util.ArrayList;
import java.util.Calendar;

import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.ui.PagerScrollView;
import com.sc.reminder.ui.TimeAxisView;
import com.sc.reminder.ui.FullDayEventView;

import android.animation.LayoutTransition;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TimeAxisPagerAdapter extends PagerAdapter{
	
	private static final int COUNT = 560;
	private Context context;
	private Calendar c;
	private Time time;
	private ArrayList<Trip> fullDayTrips = null;
	private ArrayList<Trip> dayTrips = null;
	//private Object views[] = new Object[80];
	
	public TimeAxisPagerAdapter(Context context){
		this.context = context;
		this.c = Calendar.getInstance();
		time = new Time();
		time.setToNow();
		c.set(time.year, time.month, time.monthDay);
		c.add(Calendar.DATE, -280);
	}
	
	public TimeAxisPagerAdapter(Context context,Time t){
		this.c = Calendar.getInstance();
		this.context = context;
		c.set(t.year, t.month, t.monthDay);
		c.add(Calendar.DATE, -280);
		
	}

	public ArrayList<Trip> getFullDayTrips() {
		return fullDayTrips;
	}

	public void setFullDayTrips(ArrayList<Trip> trips) {
		this.fullDayTrips = trips;
	}

	public ArrayList<Trip> getDayTrips() {
		return dayTrips;
	}

	public void setDayTrips(ArrayList<Trip> dayTrips) {
		this.dayTrips = dayTrips;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return COUNT;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ScrollView scrollView = new ScrollView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 50);
		//scrollView.setLayoutParams(params);
		//PagerScrollView scrollView = new PagerScrollView(context);
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		//layout.setLayoutTransition(LayoutTransition.)
		Trip trip = null;
		trip = getFullDayEvent(position);
		FullDayEventView fullDayEventView;
		if(trip != null){
			fullDayEventView = new FullDayEventView(context,trip);
			layout.addView(fullDayEventView);
		}
		
		TimeAxisView timeAxisView = new TimeAxisView(context,c,position,getDayEvents(position));
		timeAxisView.invalidate();
		//scrollView.removeAllViews();
		scrollView.addView(timeAxisView);
		scrollView.setVerticalScrollBarEnabled(false);
		layout.addView(scrollView);
		//views[position] = scrollView;
		container.addView(layout);
		
		return layout;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//super.destroyItem(container, position, object);
		container.removeView((View) object);
		//container.removeView((View) views[position]);
	}
	
	private Trip getFullDayEvent(int position){
		
		c.add(Calendar.DATE, position);
		int year=0,month=0,monthDay=0;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH)+1;
		monthDay = c.get(Calendar.DATE);
		String timeString = "";
		String m="";
		String d="";
		if(month<10){
			m = "0";
		}
		if(monthDay<10){
			d = "0";
		}
		timeString = Integer.toString(year) + "-" +m+ Integer.toString(month) + "-" + d + Integer.toString(monthDay);
		Log.i("妈蛋", Integer.toString(c.get(Calendar.DATE)));
		c.add(Calendar.DATE, 0 - position);
		if(fullDayTrips != null){
			Log.i("空", "kong");
			for(int i = 0;i<fullDayTrips.size();i++){
				String s = fullDayTrips.get(i).getStarttime();
				if(s.contains(timeString)){
					return fullDayTrips.get(i);
				}
				//Log.i("时间", s);
			}
		}
		Log.i("时间", timeString);
		return null;
	}
	
	
	private ArrayList<Trip> getDayEvents(int position){
		
		ArrayList<Trip> trips = new ArrayList<Trip>();
		
		c.add(Calendar.DATE, position);
		int year=0,month=0,monthDay=0;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH)+1;
		monthDay = c.get(Calendar.DATE);
		String timeString = "";
		String m="";
		String d="";
		if(month<10){
			m = "0";
		}
		if(monthDay<10){
			d = "0";
		}
		timeString = Integer.toString(year) + "-" +m+ Integer.toString(month) + "-" + d + Integer.toString(monthDay);
		Log.i("妈蛋", Integer.toString(c.get(Calendar.DATE)));
		c.add(Calendar.DATE, 0 - position);
		if(dayTrips != null){
			Log.i("空", "kong");
			for(int i = 0;i<dayTrips.size();i++){
				String s = dayTrips.get(i).getStarttime();
				if(s.contains(timeString)){
					trips.add(dayTrips.get(i));
				}
				//Log.i("时间", s);
			}
		}
		Log.i("时间", timeString);
		
		return trips;
	}

}
