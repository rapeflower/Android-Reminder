package com.sc.reminder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.text.format.Time;
import android.util.Log;

public class TimeUtils {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	public static int BEFORE = 1;
	public static int AFTER = 2;
	
	public static int getCurrentYear(){
		Time t= new Time();
		t.setToNow();
		return t.year;
	}
	
	public static int getCurrentMonth(){
		Time t = new Time();
		t.setToNow();
		return t.month;
	}
	
	public static int getCurrentDay(){
		Time t= new Time();
		t.setToNow();
		return t.monthDay;
	}
	
	public static int getWeekDay(){
		return 0;
	}
	
	/**
	 * 测试有问题
	 * @param t
	 * @return
	 */
	public static Date timeToDate(Time t){
		String string = Integer.toString(t.year) + "-" + Integer.toString(t.month + 1) + "-" + Integer.toString(t.monthDay);
		//Log.i("月份", Integer.toString(t.month + 1));
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获得指定日期的当前星期的日期数组
	 * @param t
	 * @return
	 */
	public static String [] getSevenDays(Time t){
		List<String> l = new ArrayList<String>();
		/*String string = Integer.toString(t.year) + "-" + Integer.toString(t.month) + "-" + Integer.toString(t.monthDay);
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(t.year,t.month,t.monthDay);
		
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		for(int i =weekDay;i>1;i--){
			calendar.add(Calendar.DATE, -1);
			int day = calendar.get(Calendar.DATE);
			l.add(0,Integer.toString(day));
			//Log.i("caobi", Integer.toString(day));
		}
		calendar.add(Calendar.DATE, weekDay-1);
		//calendar.setTime(date);
		l.add(Integer.toString(calendar.get(Calendar.DATE)));
		for(int i =weekDay;i<7;i++){
			calendar.add(Calendar.DATE, 1);
			int day = calendar.get(Calendar.DATE);
			l.add(Integer.toString(day));
			//Log.i("caobi", Integer.toString(day));
		}
		
		String [] s = new String [l.size()];
		for(int i = 0;i<l.size();i++){
			s[i] = l.get(i);
			//Log.i("caobica", s[i]);
		}
		
		return s;
		
	}
	
	
	/**
	 * 获取指定日期的下一个和或上一个星期的日期数组
	 * @return
	 */
	public static String[] getNextSevenDays(Time t,int info){
		List<String> l = new ArrayList<String>();
		String string = Integer.toString(t.year) + "-" + Integer.toString(t.month) + "-" + Integer.toString(t.monthDay);
		Date date = null;
		Time t1 = new Time();
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if(info == BEFORE){
			for(int i =weekDay;i>0;i--){
				calendar.add(Calendar.DATE, -1);
			}
		}
		if(info == AFTER){
			for(int i =weekDay;i<8;i++){
				calendar.add(Calendar.DATE, 1);
			}
		}
		
		t1.set(calendar.get(Calendar.MONDAY), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
		return getSevenDays(t1);
	}
}
