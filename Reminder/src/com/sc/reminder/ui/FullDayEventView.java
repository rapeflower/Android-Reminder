package com.sc.reminder.ui;

import java.io.InputStream;

import com.sc.reminder.R;
import com.sc.reminder.model.trip.Trip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.MeasureSpec;

public class FullDayEventView extends View {
	
	private Context context;
	private int width;
	private Paint paint;
	private Trip trip;

	public FullDayEventView(Context context,Trip trip) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		paint = new Paint();
		this.trip = trip;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawColor(Color.parseColor("#888888"));
		canvas.drawText("全天：", 30, 45, paint);
		drawPicture(canvas);
		
		canvas.drawText(trip.getTitle(), 180, 45, paint);
		super.onDraw(canvas);
	}
	
	private void drawPicture(Canvas canvas){
		InputStream is = getResources().openRawResource(R.drawable.trip_type6);
		Bitmap mBitmap = BitmapFactory.decodeStream(is);
		int bh = mBitmap.getHeight();
	    int bw = mBitmap.getWidth();
	    canvas.drawBitmap(mBitmap, 150-bw/2,30-bw/2, paint);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		width = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(width, 60);
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
