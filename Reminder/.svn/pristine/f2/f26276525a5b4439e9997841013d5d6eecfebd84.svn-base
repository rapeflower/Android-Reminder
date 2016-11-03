package com.sc.reminder.ui.calendar.roomorama.caldroid;

import hirondelle.date4j.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.trip.Trip;

/**
 * The CaldroidGridAdapter provides customized view for the dates gridview
 * 
 * @author thomasdao
 * 
 */
public class CaldroidGridAdapter extends BaseAdapter {
	protected ArrayList<DateTime> datetimeList;
	protected int month;
	protected int year;
	protected Context context;
	private ArrayList<Trip> trips = new ArrayList<Trip>();
	protected ArrayList<DateTime> disableDates;
	protected ArrayList<DateTime> selectedDates;

	// Use internally, to make the search for date faster instead of using
	// indexOf methods on ArrayList
	protected HashMap<DateTime, Integer> disableDatesMap = new HashMap<DateTime, Integer>();
	protected HashMap<DateTime, Integer> selectedDatesMap = new HashMap<DateTime, Integer>();

	protected DateTime minDateTime;
	protected DateTime maxDateTime;
	protected DateTime today;
	protected int startDayOfWeek;
	protected boolean sixWeeksInCalendar;
	protected Resources resources;

	/**
	 * caldroidData belongs to Caldroid
	 */
	protected HashMap<String, Object> caldroidData;
	/**
	 * extraData belongs to client
	 */
	protected HashMap<String, Object> extraData;

	public void setAdapterDateTime(DateTime dateTime) {
		this.month = dateTime.getMonth();
		this.year = dateTime.getYear();
		this.datetimeList = CalendarHelper.getFullWeeks(this.month, this.year,
				startDayOfWeek, sixWeeksInCalendar);
	}

	// GETTERS AND SETTERS
	
	public ArrayList<Trip> getTrips() {
		return trips;
	}

	public void setTrips(ArrayList<Trip> trips) {
		this.trips = trips;
	}
	
	public ArrayList<DateTime> getDatetimeList() {
		return datetimeList;
	}

	public DateTime getMinDateTime() {
		return minDateTime;
	}

	public void setMinDateTime(DateTime minDateTime) {
		this.minDateTime = minDateTime;
	}

	public DateTime getMaxDateTime() {
		return maxDateTime;
	}

	public void setMaxDateTime(DateTime maxDateTime) {
		this.maxDateTime = maxDateTime;
	}

	public ArrayList<DateTime> getDisableDates() {
		return disableDates;
	}

	public void setDisableDates(ArrayList<DateTime> disableDates) {
		this.disableDates = disableDates;
	}

	public ArrayList<DateTime> getSelectedDates() {
		return selectedDates;
	}

	public void setSelectedDates(ArrayList<DateTime> selectedDates) {
		this.selectedDates = selectedDates;
	}

	public HashMap<String, Object> getCaldroidData() {
		return caldroidData;
	}

	public void setCaldroidData(HashMap<String, Object> caldroidData) {
		this.caldroidData = caldroidData;

		// Reset parameters
		populateFromCaldroidData();
	}

	public HashMap<String, Object> getExtraData() {
		return extraData;
	}

	public void setExtraData(HashMap<String, Object> extraData) {
		this.extraData = extraData;
	}

	public HashMap<DateTime, Integer> getDisableDatesMap() {
		return disableDatesMap;
	}

	public void setDisableDatesMap(HashMap<DateTime, Integer> disableDatesMap) {
		this.disableDatesMap = disableDatesMap;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param month
	 * @param year
	 * @param caldroidData
	 * @param extraData
	 */
	public CaldroidGridAdapter(Context context, int month, int year,
			HashMap<String, Object> caldroidData,
			HashMap<String, Object> extraData) {
		super();
		this.month = month;
		this.year = year;
		this.context = context;
		this.caldroidData = caldroidData;
		this.extraData = extraData;
		this.resources = context.getResources();

		// Get data from caldroidData
		populateFromCaldroidData();
	}

	/**
	 * Retrieve internal parameters from caldroid data
	 */
	@SuppressWarnings("unchecked")
	private void populateFromCaldroidData() {
		disableDates = (ArrayList<DateTime>) caldroidData
				.get(CaldroidFragment.DISABLE_DATES);
		if (disableDates != null) {
			disableDatesMap.clear();
			for (DateTime dateTime : disableDates) {
				disableDatesMap.put(dateTime, 1);
			}
		}

		selectedDates = (ArrayList<DateTime>) caldroidData
				.get(CaldroidFragment.SELECTED_DATES);
		if (selectedDates != null) {
			selectedDatesMap.clear();
			for (DateTime dateTime : selectedDates) {
				selectedDatesMap.put(dateTime, 1);
			}
		}

		minDateTime = (DateTime) caldroidData
				.get(CaldroidFragment._MIN_DATE_TIME);
		maxDateTime = (DateTime) caldroidData
				.get(CaldroidFragment._MAX_DATE_TIME);
		startDayOfWeek = (Integer) caldroidData
				.get(CaldroidFragment.START_DAY_OF_WEEK);
		sixWeeksInCalendar = (Boolean) caldroidData
				.get(CaldroidFragment.SIX_WEEKS_IN_CALENDAR);

		this.datetimeList = CalendarHelper.getFullWeeks(this.month, this.year,
				startDayOfWeek, sixWeeksInCalendar);
	}

	protected DateTime getToday() {
		if (today == null) {
			today = CalendarHelper.convertDateToDateTime(new Date());
		}
		return today;
	}

	@SuppressWarnings("unchecked")
	protected void setCustomResources(DateTime dateTime, View backgroundView,
			RelativeLayout textView) {
		// Set custom background resource
		HashMap<DateTime, Integer> backgroundForDateTimeMap = (HashMap<DateTime, Integer>) caldroidData
				.get(CaldroidFragment._BACKGROUND_FOR_DATETIME_MAP);
		if (backgroundForDateTimeMap != null) {
			// Get background resource for the dateTime
			Integer backgroundResource = backgroundForDateTimeMap.get(dateTime);

			// Set it
			if (backgroundResource != null) {
				backgroundView.setBackgroundResource(backgroundResource
						.intValue());
			}
		}

		// Set custom text color
		HashMap<DateTime, Integer> textColorForDateTimeMap = (HashMap<DateTime, Integer>) caldroidData
				.get(CaldroidFragment._TEXT_COLOR_FOR_DATETIME_MAP);
		if (textColorForDateTimeMap != null) {
			// Get textColor for the dateTime
			Integer textColorResource = textColorForDateTimeMap.get(dateTime);

			// Set it
			if (textColorResource != null) {
//				textView.setTextColor(resources.getColor(textColorResource
//						.intValue()));
			}
		}
	}

	/**
	 * Customize colors of text and background based on states of the cell
	 * (disabled, active, selected, etc)
	 * 
	 * To be used only in getView method
	 * 
	 * @param position
	 * @param cellView
	 */
	protected void customizeTextView(int position, RelativeLayout cellView) {
		long t1 = System.currentTimeMillis();
		
		try {
			TextView rel_tv = (TextView) cellView.findViewById(R.id.cal_tv);
			ImageView cal_iv1 = (ImageView) cellView.findViewById(R.id.cal_iv1);
			ImageView cal_iv2 = (ImageView) cellView.findViewById(R.id.cal_iv2);
			
			cal_iv1.setVisibility(View.INVISIBLE);
			cal_iv2.setVisibility(View.INVISIBLE);
			
			rel_tv.setTextColor(Color.WHITE);
	
			// Get dateTime of this cell
			DateTime dateTime = this.datetimeList.get(position);
			
			// Set color of the dates in previous / next month
			if (dateTime.getMonth() != month) {
				disableDatesMap.put(dateTime, 1);
			}
	
			boolean shouldResetDiabledView = false;
			boolean shouldResetSelectedView = false;
	
			// Customize for disabled dates and date outside min/max dates
			if ((minDateTime != null && dateTime.lt(minDateTime))
					|| (maxDateTime != null && dateTime.gt(maxDateTime))
					|| (disableDates != null && disableDatesMap
							.containsKey(dateTime))) {
				rel_tv.setVisibility(View.INVISIBLE);
	//			rel_tv.setTextColor(resources
	//					.getColor(R.color.caldroid_white));//CaldroidFragment.disabledTextColor
				if (CaldroidFragment.disabledBackgroundDrawable == -1) {
	//				cellView.setBackgroundResource(R.drawable.disable_cell);
				} else {
					cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
				}
	
	//			if (dateTime.equals(getToday())) {
	//				cellView.setBackgroundResource(R.drawable.red_border_gray_bg);
	//			}
			} else {
				shouldResetDiabledView = true;
			}
	
			// Customize for selected dates
			if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
				if (CaldroidFragment.selectedBackgroundDrawable != -1) {
					cellView.setBackgroundResource(CaldroidFragment.selectedBackgroundDrawable);
				} else {
					cellView.setBackgroundColor(resources
							.getColor(R.color.caldroid_sky_blue));
				}
	
				rel_tv.setTextColor(CaldroidFragment.selectedTextColor);
			} else {
				shouldResetSelectedView = true;
			}
	
			if (shouldResetDiabledView && shouldResetSelectedView) {
				// Customize for today
				if (dateTime.equals(getToday())) {
					rel_tv.setTextColor(Color.BLACK);
					cal_iv2.setVisibility(View.VISIBLE);
					cal_iv2.setBackgroundResource(R.drawable.circle_inner_big);
	//				cal_iv.setImageResource(R.drawable.circle_border_red);
				}
			}
			if (dateTime.getMonth() == month) {
			
				for (Trip trip : trips) {
		//			LogUtils.e("trip.getStarttime() = " + trip.getStarttime());
					DateTime dt = CalendarHelper.convertDateToDateTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(trip.getStarttime()));
					if (dt.equals(dateTime)) {
						
						HashMap<String, Trip> tps = (HashMap<String, Trip>) cellView.getTag();
						
						if (null == tps) {
							tps = new HashMap<String, Trip>();
						}
						
						tps.put(trip.getMid(), trip);
						
						cellView.setTag(tps);
						
						cal_iv1.setVisibility(View.VISIBLE);
	//					LogUtils.e("trip.getTypes() = " + trip.getTypes());
						
						switch (Integer.parseInt(trip.getTypes())) {
							case 0:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 1:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 2:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 3:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 4:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 5:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 6:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
							case 7:
								cal_iv1.setBackgroundResource(R.drawable.circle_border_red);
								break;
						}					
					}
				}
			}
			rel_tv.setText("" + dateTime.getDay());
		
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		// Set custom color if required
//		setCustomResources(dateTime, cellView, cellView);
		
		long t2 = System.currentTimeMillis();
		LogUtils.e("t = " + String.valueOf(t2 - t1));	
	}
	
	protected void customizeTextView2(int position, String tag, HashMap<String, Trip> tps, OnCustomizeDone ocd) {
		int textColor = -1;
		int textVisible = -1;
		int iv1Visible = -1;
		int iv1Src = -1;
		int iv2Visible = -1;
		int iv2Src = -1;
		int viewColor = -1;
		HashMap<String, Trip> textTag = null;
		
		try {
			iv1Visible = View.INVISIBLE;
			iv2Visible = View.INVISIBLE;
			
			textColor = Color.WHITE;
			
			// Get dateTime of this cell
			DateTime dateTime = this.datetimeList.get(position);
			
			// Set color of the dates in previous / next month
			if (dateTime.getMonth() != month) {
				disableDatesMap.put(dateTime, 1);
			}
			
			boolean shouldResetDiabledView = false;
			boolean shouldResetSelectedView = false;
			
			// Customize for disabled dates and date outside min/max dates
			if ((minDateTime != null && dateTime.lt(minDateTime))
					|| (maxDateTime != null && dateTime.gt(maxDateTime))
					|| (disableDates != null && disableDatesMap
					.containsKey(dateTime))) {
				textVisible = View.INVISIBLE;
			} else {
				shouldResetDiabledView = true;
			}
			
			// Customize for selected dates
			if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
				viewColor = resources.getColor(R.color.caldroid_sky_blue);
				textColor = CaldroidFragment.selectedTextColor;
			} else {
				shouldResetSelectedView = true;
			}
			
			if (shouldResetDiabledView && shouldResetSelectedView) {
				// Customize for today
				if (dateTime.equals(getToday())) {
					textColor = Color.BLACK;
					iv2Visible = View.VISIBLE;
					iv2Src = R.drawable.circle_inner_big;
				}
			}
			if (dateTime.getMonth() == month) {
				
				for (Trip trip : trips) {
					//			LogUtils.e("trip.getStarttime() = " + trip.getStarttime());
					DateTime dt = CalendarHelper.convertDateToDateTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(trip.getStarttime()));
					if (dt.equals(dateTime)) {
						
						if (null == tps) {
							tps = new HashMap<String, Trip>();
						}
						
						tps.put(trip.getMid(), trip);
						textTag = tps;
						
						iv1Visible = View.VISIBLE;
						//					LogUtils.e("trip.getTypes() = " + trip.getTypes());
						
						switch (Integer.parseInt(trip.getTypes())) {
						case 0:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 1:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 2:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 3:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 4:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 5:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 6:
							iv1Src = R.drawable.circle_border_red;
							break;
						case 7:
							iv1Src = R.drawable.circle_border_red;
							break;
						}					
					}
				}
				
			}
			
			ocd.onDone("" + dateTime.getDay(), textColor, textVisible, iv1Visible, iv1Src, iv2Visible, iv2Src, viewColor, textTag);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.datetimeList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout cellView = (RelativeLayout) convertView;

		// For reuse
		if (convertView == null) {
			cellView = (RelativeLayout) inflater.inflate(R.layout.date_cell_circle, null);
		}
		
		customizeTextView(position, cellView);
		
//		final String ctag = cellView.toString();
//		cellView.setTag(ctag);
//		
//		final HashMap<String, Trip> tps = (HashMap<String, Trip>) cellView.findViewById(R.id.cal_tv).getTag();
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				customizeTextView2(position, ctag, tps, new OnCustomizeDone() {
//					
//					@Override
//					public void onDone(final String text, final int textColor,
//							final int textVisible, final int iv1Visible, final int iv1Src,
//							final int iv2Visible, final int iv2Src, final int viewColor,
//							final HashMap<String, Trip> textTag) {
//						((Activity)context).runOnUiThread(new Runnable() {
//							
//							@Override
//							public void run() {
//								View cView = parent.findViewWithTag(ctag);
//								if (null != cView) {
////									cView.setBackgroundColor(viewColor);
//									
//									TextView rel_tv = (TextView) cView.findViewById(R.id.cal_tv);
//									ImageView cal_iv1 = (ImageView) cView.findViewById(R.id.cal_iv1);
//									ImageView cal_iv2 = (ImageView) cView.findViewById(R.id.cal_iv2);
//									
//									rel_tv.setVisibility(textVisible);
//									rel_tv.setText(text);
//									rel_tv.setTextColor(textColor);
//									rel_tv.setTag(textTag);
//									
//									cal_iv1.setVisibility(iv1Visible);
//									if (-1 != iv1Src) {
//										cal_iv1.setBackgroundResource(iv1Src);
//									}
//									cal_iv2.setVisibility(iv2Visible);
//									if (-1 != iv2Src) {
//										cal_iv2.setBackgroundResource(iv2Src);
//									}
//								}								
//							}
//						});
//					}
//				});
//			}
//		}).start();
		
		return cellView;
	}
	
	public interface OnCustomizeDone {
		void onDone(String text, int textColor, int textVisible, int iv1Visible, int iv1Src, int iv2Visible, int iv2Src, int viewColor, HashMap<String, Trip> textTag);
	}

}
