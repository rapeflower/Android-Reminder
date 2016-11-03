package com.sc.reminder.ui;

import android.view.View;



import java.util.HashMap;

import com.sc.reminder.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;

public class YearView extends View {
	
	public static final String VIEW_PARAMS_YEAR = "year";
	/**
     * This sets one of the month in this view as selected 
     */
    public static final String VIEW_PARAMS_SELECTED_MONTH = "selected_month";
 // Which month is selected [0-11] or -1 if no month is selected
    protected int mSelectedMonth = DEFAULT_SELECTED_MONTH;
    protected static final int DEFAULT_SELECTED_MONTH = -1;
	private static String TAG = "SimpleYearView";
	Paint mPaint;
	private static final int startYearX = 25;
	private static final int startYearY = 55;
	private int startMonthX = startYearX;
	private int startMonthY = startYearY + 50;
	private int startMonthOffsetY = startMonthY - monthTextSize;
	private static int monthWidth;
	private static final int monthHeight = 210;//160
	private static final int dayWidth = 30;//20
	private static final int dayHeight = 30;//20
	private static final int yearTextSize = 45;
	private static final int yearTextColor = Color.WHITE;
	private static final int monthTextSize = 28;
	private static int monthTextColor;
	private static final int monthDayTextSize = 14;
	private static final int monthDayTextColor = Color.WHITE;
	private static final int selectDayCircleRadius = 11;
	private static int mWidth;
	private static final int mHeight = 915;//715
	private int dividerColor;
	private String mTimeZone = Time.getCurrentTimezone();
	// If this view contains the selected month
    protected boolean mHasSelectedMonth = false;
 // The left edge of the selected month
    protected int mSelectedLeft = -1;
    // The right edge of the selected month
    protected int mSelectedRight = -1;
 // The top edge of the selected month
    protected int mSelectedTop = -1;
    // The bottom edge of the selected month
    protected int mSelectedBottom = -1;
	Time mTodayTime;
	protected Rect r = new Rect();
	protected Paint p = new Paint();
	
	
	protected int mMonthOutlineColor;
	private int mClickedMonthColor;
	private int mClickedMonthIndex = -1;
	private static final int mClickedAlpha = 128;
	public YearView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mTodayTime = new Time(mTimeZone);
		mTodayTime.setToNow();
	}

	public YearView(Context context, Time now) {
		super(context);
		mPaint = new Paint();
		mTodayTime = now;
	}

	public YearView(Context context) {
		super(context);
		Resources res = context.getResources();
		mPaint = new Paint();
		mMonthOutlineColor = res.getColor(R.color.mini_month_today_outline_color);
		mClickedMonthColor = res.getColor(R.color.month_clicked_background_color);
		monthTextColor = res.getColor(R.color.main_theme_color);
		dividerColor = res.getColor(R.color.divider_color);
	}
	
	public void setYearParams(HashMap<String, Integer> params){
		mTodayTime = new Time(mTimeZone);
		mTodayTime.set(1, 0, params.get(VIEW_PARAMS_YEAR));
		setTag(params);
		if (params.containsKey(VIEW_PARAMS_SELECTED_MONTH)) {
            mSelectedMonth = params.get(VIEW_PARAMS_SELECTED_MONTH);
        }
        mHasSelectedMonth = mSelectedMonth != -1;
        
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//canvas.drawColor(Color.BLUE);
		//draw year
		drawYear(canvas, mPaint, mTodayTime);
		
		//draw divider
		drawDivider(canvas, mPaint);
		
		//draw 12 month
		drawTwelveMonths(canvas, mPaint, mTodayTime);
	
		drawClick(canvas);
		
		super.onDraw(canvas);
	}
	
	
	private void drawYear(Canvas canvas, Paint paint, Time t) {
		mPaint.setColor(yearTextColor);
		mPaint.setTextSize(yearTextSize);
		canvas.drawText(String.format(
				getResources().getString(R.string.year_string), t.year),
				startYearX, startYearY, mPaint);
	}
	
	private void drawDivider(Canvas canvas, Paint paint) {
		mPaint.setColor(dividerColor);
		int drawY = startYearY + 15;
		canvas.drawLine(startYearX, drawY, mWidth, drawY, mPaint);
	}
	
	private void drawTwelveMonths(Canvas canvas, Paint paint, Time t) {
		int drawX,drawY;
		Time nowTime = new Time();
		nowTime.setToNow();
		boolean drawSelectDay = false;
		for(int row = 1; row <= 4; row++){
			for(int column = 1; column <= 3; column++){
				int month = column + (row - 1) * 3;
				
				// draw month
				drawX = startMonthX + (column - 1) * monthWidth;
				drawY = startMonthY + (row - 1) * monthHeight;
				drawMonth(canvas, mPaint, month, drawX, drawY);
				//draw month day
				t.set(1, month - 1, t.year);
				drawSelectDay = (nowTime.month == month - 1 && nowTime.year == t.year);
				if(drawSelectDay){
					int alpha = p.getAlpha();
		            p.setColor(Color.parseColor("#DDDDDD"));
		            p.setAlpha(mClickedAlpha);
		            RectF r = new RectF(drawX,drawY-monthTextSize,drawX+dayWidth*7,drawY+dayHeight*7-monthTextSize);
		            //canvas.drawRect(r, p);
		            canvas.drawRoundRect(r, 15, 15, p);
		            p.setAlpha(alpha);
				}
				drawMonthDay(canvas, mPaint, t, drawX, drawY + 20,
						drawSelectDay, nowTime.monthDay);
			}
		}
	}
	
	
	private void drawMonth(Canvas canvas, Paint paint, int month, int startX,
			int startY) {
		mPaint.setColor(monthTextColor);
		mPaint.setTextSize(monthTextSize);

		canvas.drawText(String.format(
				getResources().getString(R.string.month_string), month),
				startX, startY, mPaint);
	}
	
	private void drawMonthDay(Canvas canvas, Paint paint,Time time,int startX,
			int startY, boolean drawSelectDay, int selectDay) {
		
		mPaint.setColor(monthDayTextColor);
		mPaint.setTextSize(monthDayTextSize);
		int drawMonthDayRow = 1;
		int monthDay;
		int drawX, drawY;
		int weekDay = 1;
		
		for(monthDay = 1; monthDay <= 31; monthDay++){	
				if(monthDay <= time.getActualMaximum(Time.MONTH_DAY)){
					if(monthDay == 1){//get first day's weekday, we calculate rest by ourself
						time.set(monthDay , time.month, time.year);
						time.normalize(true);//need get the weekDay
						weekDay = time.weekDay;
					}else {
						weekDay = (weekDay + 1) % 7;
					}
					
					//only it is Sunday and it is not the first day of the month, 
					//we will draw start on a new line
					if(weekDay == 0 && monthDay != 1){
						drawMonthDayRow++;
					}
					drawX = startX + weekDay * dayWidth;
					drawY = startY + (drawMonthDayRow - 1) * dayHeight;
					
					//if it is the selected day, draw a circle and text
					if (drawSelectDay && selectDay == monthDay) {
						//mPaint.setColor(monthTextColor);
						mPaint.setColor(Color.parseColor("#ff0000"));
                        canvas.drawCircle(drawX + 8, drawY - 5, selectDayCircleRadius, mPaint);
                        mPaint.setColor(Color.WHITE);
					}else {
						mPaint.setColor(monthDayTextColor);				
					}
					if(monthDay < 10){//a single number need a little offset
						drawX += 4;
					}
					canvas.drawText(monthDay + "", drawX, drawY, mPaint);

				}else {
					return;
				}
	
		}
		
	}

	/**
     * Calculates the Month that the given x and y position is in, accounting for month
     * number. Returns a Time referencing that month or null if
     *
     * @param x The x position of the touch event
     * @param y The y position of the touch event
     * @return A time object for the tapped day or null if the position wasn't
     *         in a month
     */
    public Time getMonthFromLocation(float x, float y) {
        //position wasn't in a month
    	if(x <= startMonthX || x >= (startMonthX + monthWidth * 3) ||
    			y <= startMonthOffsetY || y >= (startMonthOffsetY + monthHeight * 4)){
    		return null;
    	}else {
    		int row =(int)(y -startMonthOffsetY)  / monthHeight; // from 0 to 3
        	int column = (int)(x - startMonthX) / monthWidth;// from 0 to 2
        	int month = (column + 1) + row * 3; 
        	Log.v(TAG, "month:" + month);
        	
            Time time = new Time(mTimeZone);
            time.set(1, month - 1, mTodayTime.year);
            return time;
		}

    	
    }
    
    
    /**
     * This calculates the positions for the selected month lines.
     */
    protected Rect getClickPositionsRect() {
        if (mClickedMonthIndex != -1) {
            int left = startMonthX + mClickedMonthIndex % 3 * monthWidth;
            //int right = left + monthWidth;
            int right = left + dayWidth*7;
            int top = startMonthOffsetY + mClickedMonthIndex / 3 * monthHeight;
            //int bottom = top + monthHeight;
            int bottom = top + dayHeight*7;
            return new Rect(left, top, right, bottom);
        }else {
			return new Rect(0, 0, 0, 0);
		}
    }
    
    protected RectF getClickPositionRectF(){
    	if (mClickedMonthIndex != -1) {
            int left = startMonthX + mClickedMonthIndex % 3 * monthWidth;
            //int right = left + monthWidth;
            int right = left + dayWidth*7;
            int top = startMonthOffsetY + mClickedMonthIndex / 3 * monthHeight;
            //int bottom = top + monthHeight;
            int bottom = top + dayHeight*7;
            return new RectF(left, top, right, bottom);
        }else {
			return new RectF(0, 0, 0, 0);
		}
    }
    
    /**
     * Draw the "clicked" color on the tapped month
     * @param canvas
     */
    private void drawClick(Canvas canvas) {
        if (mClickedMonthIndex != -1) {
            int alpha = p.getAlpha();
            p.setColor(mClickedMonthColor);
            p.setAlpha(mClickedAlpha);
            RectF r = getClickPositionRectF();
            //canvas.drawRect(r, p);
            canvas.drawRoundRect(r, 15, 15, p);
            p.setAlpha(alpha);
        }
    }
    
    public void clearClickedMonth() {
    	mClickedMonthIndex = -1;
        invalidate();
    }
    
    /**
     * 设置被点击月份背景色
     * @param xLocation
     * @param yLocation
     */
    public void setClickedMonth(float xLocation, float yLocation) {
    	Time monthTime = getMonthFromLocation(xLocation, yLocation);
    	if(monthTime != null){
    		mClickedMonthIndex = monthTime.month;
    	}
        Log.v(TAG, "mClickedMonthIndex:" + mClickedMonthIndex);
        invalidate();
    }
    
    
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		monthWidth = (mWidth - startMonthX) / 3;
		setMeasuredDimension(mWidth, mHeight);
    }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		monthWidth = (mWidth - startMonthX) / 3;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}
