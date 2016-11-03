package com.sc.reminder.ui.calendar.day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sc.reminder.ui.WeekView;
import com.sc.reminder.utils.TimeUtils;

import android.R.integer;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class WeekPagerAdapter extends PagerAdapter{
	
	private Context context;
	private static final int COUNT = 80;
	private int currentPosition = 0;
	private Calendar c;
	private Time time;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public WeekPagerAdapter(Context context){
		this.context = context;
		this.c = Calendar.getInstance();
		time = new Time();
		time.setToNow();
		c.set(time.year, time.month + 1, time.monthDay);
		c.add(Calendar.DATE, -280);
		
	}
	
	public WeekPagerAdapter(Context context,Time t){
		this.c = Calendar.getInstance();
		this.context = context;
		//c.setTime(TimeUtils.timeToDate(t));
		c.set(t.year, t.month, t.monthDay);//修改加一
		//Log.i("年年", Integer.toString(c.get(Calendar.YEAR)));
		//Log.i("月月", Integer.toString(c.get(Calendar.MONTH)));
		c.add(Calendar.DATE, -280);
		
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
		WeekView v = new WeekView(context, c, position);
		v.invalidate();
		container.addView(v);
		
		return v;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//super.destroyItem(container, position, object);
		container.removeView((View) object);
	}
	
	private String[] getDays(int position){
		
		if(position > currentPosition){
			
		}
		
		return null;
	}

}
