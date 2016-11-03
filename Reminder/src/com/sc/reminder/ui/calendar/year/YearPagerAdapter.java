package com.sc.reminder.ui.calendar.year;

import java.util.HashMap;

import com.sc.reminder.ui.YearView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.format.Time;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;

public class YearPagerAdapter extends PagerAdapter implements OnTouchListener{
	
	private static final int YEAR_COUNT = YearViewPagerFragment.MAX_CALENDAR_YEAR - YearViewPagerFragment.MIN_CALENDAR_YEAR;
	private Time mSelectedMonth;
	private Context mContext;
	protected GestureDetector mGestureDetector;
    YearView mClickedView;
    YearView mSingleTapUpView;
    float mClickedXLocation;                // Used to find which month was clicked
    float mClickedYLocation;                // Used to find which month was clicked
    long mClickTime;                        // Used to calculate minimum click animation time
 // there is no click animation on flings
    private static int mOnDownDelay;
    private static int mTotalClickDelay;
 // Used to insure minimal time for seeing the click animation before switching views
    private static final int mOnTapDelay = 100;
 // Minimal distance to move the finger in order to cancel the click animation
    private static float mMovedPixelToCancel;
    
    protected String mHomeTimeZone;
    protected Time mRealSelectedYear = null;
	
	
	public YearPagerAdapter(Context c) {
		this.mContext = c;
		 ViewConfiguration vc = ViewConfiguration.get(c);
	    	mMovedPixelToCancel = vc.getScaledTouchSlop();
	    	mOnDownDelay = ViewConfiguration.getTapTimeout();
	    	mMovedPixelToCancel = vc.getScaledTouchSlop();
	        mTotalClickDelay = mOnDownDelay + mOnTapDelay;
	    	init();
	}
	
	protected void init() {
        mGestureDetector = new GestureDetector(mContext, new CalendarGestureListener());
        mSelectedMonth = new Time();
        mSelectedMonth.setToNow();
        mRealSelectedYear = new Time();
        mRealSelectedYear.set(mSelectedMonth);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return YEAR_COUNT;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		YearView view;
		HashMap<String, Integer> drawingParams = null;
		
			view = new YearView(mContext);
			// Set up the new view
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            view.setClickable(true);
            view.setOnTouchListener(this);
		
		if (drawingParams == null) {
            drawingParams = new HashMap<String, Integer>();
        }
        drawingParams.clear();
        
        int selectedMonth = -1;
        selectedMonth = mSelectedMonth.month;
        drawingParams.put(YearView.VIEW_PARAMS_SELECTED_MONTH, selectedMonth);
        drawingParams.put(YearView.VIEW_PARAMS_YEAR,YearViewPagerFragment.MIN_CALENDAR_YEAR + position);
        view.setYearParams(drawingParams);
		view.invalidate();
		container.addView(view);
		
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//super.destroyItem(container, position, object);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		int action = event.getAction();
		
		if (mGestureDetector.onTouchEvent(event)) {
			mSingleTapUpView = (YearView) v;
            long delay = System.currentTimeMillis() - mClickTime;
            // Make sure the animation is visible for at least mOnTapDelay - mOnDownDelay ms
            v.postDelayed(mDoSingleTapUp,
                    delay > mTotalClickDelay ? 0 : mTotalClickDelay - delay);
            return true;
        }else {
        	// Animate a click - on down: show the selected day in the "clicked" color.
            // On Up/scroll/move/cancel: hide the "clicked" color.
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mClickedView = (YearView)v;
                    mClickedXLocation = event.getX();
                    mClickedYLocation = event.getY();
                    mClickTime = System.currentTimeMillis();
                    v.postDelayed(mDoClick, mOnDownDelay);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_SCROLL:
                case MotionEvent.ACTION_CANCEL:
                    clearClickedView((YearView)v);
                    break;
                case MotionEvent.ACTION_MOVE:
                    // No need to cancel on vertical movement, ACTION_SCROLL will do that.
                    if (Math.abs(event.getX() - mClickedXLocation) > mMovedPixelToCancel) {
                        clearClickedView((YearView)v);
                    }
                    break;
                default:
                    break;
            }
		}
        return false;
	}
	
	protected void onMonthTapped(Time monthTime) {
    	//go to the specific month
    }
	
	private void clearClickedView(YearView v) {
        v.removeCallbacks(mDoClick);
        synchronized(v) {
            v.clearClickedMonth();
        }
        mClickedView = null;
    }
	
	private final Runnable mDoSingleTapUp = new Runnable() {
        @Override
        public void run() {
            if (mSingleTapUpView != null) {
                Time month = mSingleTapUpView.getMonthFromLocation(mClickedXLocation,mClickedYLocation);
                if (month != null) {
                    onMonthTapped(month);
                }
                clearClickedView(mSingleTapUpView);
                mSingleTapUpView = null;
            }
        }
    };
	
    private final Runnable mDoClick = new Runnable() {
        @Override
        public void run() {
            if (mClickedView != null) {
                synchronized(mClickedView) {
                    mClickedView.setClickedMonth(mClickedXLocation, mClickedYLocation);
                }
                mClickedView = null;
            }
        }
    };
    
	protected class CalendarGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

}
