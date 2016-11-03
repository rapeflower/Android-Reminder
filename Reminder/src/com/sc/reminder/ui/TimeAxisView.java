package com.sc.reminder.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.sc.reminder.R;
import com.sc.reminder.model.trip.Trip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ScrollView;

public class TimeAxisView extends View {
	
	private Paint paint;
	private Context context;
	private Calendar calendar;
	private int width;
	private int height = 3000;
	private ArrayList<Trip> trips;
	private String[] timeStrings = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};

	public TimeAxisView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.calendar = Calendar.getInstance();
		this.context = context;
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}
	
	public TimeAxisView(Context context,Calendar c,int position,ArrayList<Trip> trips) {
		super(context);
		// TODO Auto-generated constructor stub
		this.calendar = Calendar.getInstance();
		this.context = context;
		paint = new Paint();
		paint.setColor(Color.BLACK);
		this.trips = trips;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		drawAxis(canvas);
		
		drawEvents(canvas);
		
		//drawPicture(canvas);
		
		super.onDraw(canvas);
	}
	
	private void drawAxis(Canvas canvas){
		paint.setTextSize(35);
		int startX = 150;
		int startY = height/24;
		int stopX = width;
		int offset = height/24;
		
		for(int i =0;i<24;i++){
			startY = offset*i;
			paint.setColor(Color.WHITE);
			if(i == 0){
				canvas.drawText(timeStrings[i], 20, startY+35, paint);
			}else{
				canvas.drawText(timeStrings[i], 20, startY+17, paint);
			}
			//canvas.drawText(timeStrings[i], 20, startY+17, paint);
			paint.setColor(Color.BLACK);
			canvas.drawLine(startX, startY, stopX, startY, paint);
		}
		paint.setColor(Color.WHITE);
		canvas.drawLine(150, 0, 150, 3000, paint);
	}
	
	private void drawEvent(Canvas canvas){
		int startX = 150;
		int stopX = width;
		Rect r = new Rect(startX, 680, width, 980);
		paint.setColor(Color.parseColor("#AAAAAA"));
		canvas.drawRect(r, paint);
	}
	
	private void drawPicture(Canvas canvas){
		InputStream is = getResources().openRawResource(R.drawable.trip_type0);
		Bitmap mBitmap = BitmapFactory.decodeStream(is);
		int bh = mBitmap.getHeight();
	    int bw = mBitmap.getWidth();
	    canvas.drawBitmap(mBitmap, 150-bw/2,680, paint);
	}
	
	
	private int top = 0;
	private int bottom = 0;
	private void drawEvents(Canvas canvas){
		int hour = 0;
		int minute = 0;
		//int second;
		
		for(int i = 0;i<trips.size();i++){
			String start = trips.get(i).getStarttime();
			hour = Integer.parseInt(start.substring(11, 13));
			Log.i("top", start.substring(11, 13));
			minute = Integer.parseInt(start.substring(14, 16));
			//second = Integer.parseInt(start.substring(17, 18));
			top = hour*(3000/24)+minute*(3000/1440);
			
			String end = trips.get(i).getEndtime();
			hour = Integer.parseInt(end.substring(11, 13));
			minute = Integer.parseInt(end.substring(14, 16));
			bottom = hour*(3000/24)+minute*(3000/1440);Log.i("bottom", start.substring(11, 13));
			
			Rect r = new Rect(150, top, width, bottom);
			paint.setColor(Color.parseColor("#AAAAAA"));
			canvas.drawRect(r, paint);
			
			InputStream is = getResources().openRawResource(R.drawable.trip_type0);
			Bitmap mBitmap = BitmapFactory.decodeStream(is);
			//int bh = mBitmap.getHeight();
		    int bw = mBitmap.getWidth();
		    canvas.drawBitmap(mBitmap, 150-bw/2,top, paint);
		    paint.setColor(Color.parseColor("#000000"));
		    canvas.drawText(trips.get(i).getTitle(), 180, top + 38, paint);
		}
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		width = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(width, 3000);
	}

}
