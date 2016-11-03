package com.sc.reminder.ui;


import java.util.Calendar;
import java.util.Date;

import com.sc.reminder.utils.TimeUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class WeekView extends View {
	
	private Paint paint;
	private Paint circlePaint;
	private int weekWidth=500;//默认值无效
	private int weekHeight=80;
	private int startX = 0;
	private int dayWidth;
	private int dayTextSize = 55;
	private String[] days;
	private int mAscent;
	private int offset1;
	private int offset2;
	private Time nowTime;
	private boolean isCurrentDay = false;
	private int selectDayCircleRadius = 40;

	public WeekView(Context context) {
		super(context);
		paint = new Paint();
		paint.setTextSize(dayTextSize);
		circlePaint = new Paint();
		circlePaint.setColor(Color.WHITE);
	}
	
	public WeekView(Context context,Calendar c,int position){
		super(context);
		nowTime = new Time();
		nowTime.setToNow();
		paint = new Paint();
		paint.setTextSize(dayTextSize);
		circlePaint = new Paint();
		circlePaint.setColor(Color.WHITE);
		Time t = new Time();
		c.add(Calendar.DATE, position*7);
		if((c.get(Calendar.YEAR) == nowTime.year) && (c.get(Calendar.MONTH) == nowTime.month)){
			isCurrentDay = true;
		}else{
			isCurrentDay = false;
		}
		t.set(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		this.days = TimeUtils.getSevenDays(t);
		c.add(Calendar.DATE, -position*7);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		int drawX=0;
		int drawY=68;
		paint.setTextSize(dayTextSize);
		paint.setColor(Color.BLUE);
		
		for(int i = 0;i < 7;i++){
			drawX = startX + i*dayWidth;
			if(days[i].equals(Integer.toString(nowTime.monthDay))){
				canvas.drawCircle(drawX+dayWidth/2, drawY-dayTextSize/3, selectDayCircleRadius, circlePaint);
			}
			if(Integer.parseInt(days[i]) < 10){
				canvas.drawText(days[i], drawX+offset1, drawY, paint);
			}else{
				canvas.drawText(days[i], drawX+offset2, drawY, paint);
			}
		}
		
		super.onDraw(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		weekWidth = measureWidth(widthMeasureSpec);
		weekHeight = measureHeight(heightMeasureSpec);
		dayWidth = (weekWidth-2*startX)/7;
		offset1 = (dayWidth - dayTextSize/2)/2;
		offset2 = (dayWidth - dayTextSize)/2;
		paint.setTextSize(85);
		
		setMeasuredDimension(weekWidth, weekHeight);
		
	}
	
	private int measureWidth(int measureSpec) {  
        int result = 0;  
        int specMode = MeasureSpec.getMode(measureSpec);  
        int specSize = MeasureSpec.getSize(measureSpec);  
  
        if (specMode == MeasureSpec.EXACTLY) {  
            // We were told how big to be  
            result = specSize;  
        } else {  
            // Measure the text  
            result = (int) paint.measureText(days.toString()) + getPaddingLeft() + getPaddingRight();  
            if (specMode == MeasureSpec.AT_MOST) {  
                // Respect AT_MOST value if that was what is called for by  
                // measureSpec  
                result = Math.min(result, specSize);// 60,480  
            }  
        }  
  
        return result;  
    }  
  
    private int measureHeight(int measureSpec) {  
        int result = 0;  
        int specMode = MeasureSpec.getMode(measureSpec);  
        int specSize = MeasureSpec.getSize(measureSpec);  
  
        mAscent = (int) paint.ascent();  
        if (specMode == MeasureSpec.EXACTLY) {  
            // We were told how big to be  
            result = specSize;  
        } else {  
            // Measure the text (beware: ascent is a negative number)  
            result = (int) (-mAscent + paint.descent()) + getPaddingTop() + getPaddingBottom();  
            if (specMode == MeasureSpec.AT_MOST) {  
                // Respect AT_MOST value if that was what is called for by  
                // measureSpec  
                result = Math.min(result, specSize);  
            }  
        }  
        return result;  
    }

}

