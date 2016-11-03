package com.sc.reminder.view.trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.TripDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.ui.calendar.day.DayFragment;
import com.sc.reminder.ui.calendar.roomorama.caldroid.CaldroidFragment;
import com.sc.reminder.ui.calendar.roomorama.caldroid.CaldroidListener;
import com.sc.reminder.ui.calendar.year.YearViewPagerFragment;
import com.sc.reminder.utils.ScApplication;

public class Home extends FragmentActivity implements OnClickListener{
	
	private TextView tv_main_txsx;
	private TextView tv_main_bwl;
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	private TextView btn_titlebar_left;
	private ImageButton btn_titlebar_right;
	private ImageButton btn_titlebar_search;
	private PopupWindow popupWindow; 
	private View popWindowView;
	
	RelativeLayout rl_main_control;
	LinearLayout ll_main_control;
	LinearLayout ll_main_controler;
	
	TripDao td = null;
	
	ArrayList<String> deleteIds = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home_trip);
		
		ScApplication.getInstance().showLoadingDialog();
		
		initView();
		
		td = new TripDao(this);
		
		caldroidFragment = new CaldroidFragment();
		
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
			caldroidFragment.setTrips(td.queryAll());
			caldroidFragment.setArguments(args);
		}
		
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar, caldroidFragment);
		t.commit();

		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				try {
//					TextView cal_tv = (TextView) view.findViewById(R.id.cal_tv);
					HashMap<String, Trip> tps = (HashMap<String, Trip>) view.getTag();
					
					rl_main_control.setVisibility(View.INVISIBLE);
					ll_main_controler.removeAllViews();
					
					if (null != tps) {
						rl_main_control.setVisibility(View.VISIBLE);
						LogUtils.e("tps size = " + tps.size());
						
						Set<Entry<String,Trip>> set = tps.entrySet();
						Iterator<Entry<String, Trip>> it = set.iterator();
						while (it.hasNext()) {
							Entry<String, Trip> entry = it.next();
							String id = entry.getKey();
							if (deleteIds.contains(id)) {
								continue;
							}
							Trip trip = entry.getValue();
							RelativeLayout trip_item = (RelativeLayout) View.inflate(Home.this, R.layout.trip_item, null);
							trip_item.setTag(trip);
							trip_item.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View view) {
									
									Trip trip = (Trip) view.getTag();
									
									Intent intent = new Intent(Home.this, TripDetail.class);
									intent.putExtra("mid", trip.getMid());
									startActivityForResult(intent, 1);
								}
							});
							ImageView iv_main_envent_img = (ImageView) trip_item.findViewById(R.id.iv_main_envent_img);
							TextView tv_main_envent_time = (TextView) trip_item.findViewById(R.id.tv_main_envent_time);
							TextView tv_main_envent_name = (TextView) trip_item.findViewById(R.id.tv_main_envent_name);
							
							switch (Integer.parseInt(trip.getTypes())) {
								case 0:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type0);
									break;
								case 1:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type1);
									break;
								case 2:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type2);
									break;
								case 3:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type3);
									break;
								case 4:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type4);
									break;
								case 5:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type5);
									break;
								case 6:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type6);
									break;
								case 7:
									iv_main_envent_img.setBackgroundResource(R.drawable.trip_type7);
									break;
							}
							
							tv_main_envent_time.setText(new SimpleDateFormat("HH:mm").format((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(trip.getStarttime())));
							tv_main_envent_name.setText(trip.getTitle());
							
							ll_main_controler.addView(trip_item);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onChangeMonth(int month, int year) {
			}

			@Override
			public void onLongClickDate(Date date, View view) {
			}

			@Override
			public void onCaldroidViewCreated() {
			}

		};

		caldroidFragment.setCaldroidListener(listener);
		
		ScApplication.getInstance().dismissLoadingDialog();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
//		caldroidFragment.setTrips(td.queryAll());
//		caldroidFragment.refreshView();
	}
	
	private void initView() {
		popWindowView = getLayoutInflater().inflate(R.layout.hometrip_popupwindow, null);
		popupWindow = new PopupWindow(popWindowView,400,400);
		popWindowView.findViewById(R.id.year).setOnClickListener(this);
		popWindowView.findViewById(R.id.month).setOnClickListener(this);
		popWindowView.findViewById(R.id.day).setOnClickListener(this);
		btn_titlebar_left = (TextView) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		btn_titlebar_right = (ImageButton) findViewById(R.id.btn_titlebar_right);
		btn_titlebar_right.setOnClickListener(this);
		btn_titlebar_search = (ImageButton) findViewById(R.id.btn_titlebar_search);
		btn_titlebar_search.setOnClickListener(this);
		rl_main_control = (RelativeLayout) findViewById(R.id.rl_main_control);
		ll_main_control = (LinearLayout) findViewById(R.id.ll_main_control);
		ll_main_controler = (LinearLayout) findViewById(R.id.ll_main_controler);
		tv_main_txsx = (TextView) findViewById(R.id.tv_main_txsx);
		tv_main_txsx.setOnClickListener(this);
		tv_main_bwl = (TextView) findViewById(R.id.tv_main_bwl);
		tv_main_bwl.setOnClickListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left://今天
				popupWindow.setOutsideTouchable(true);
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
				popupWindow.update();
				popupWindow.showAsDropDown(btn_titlebar_left,0, 0);
				break;
			case R.id.year:
				//startActivity(new Intent(Home.this, com.sc.reminder.view.trip.SearchActivity.class));
				
				Fragment fragment = new YearViewPagerFragment();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.calendar, fragment);
				//ft.addToBackStack(null);
				ft.commit();
				popupWindow.dismiss();
				break;
			case R.id.month:
				FragmentTransaction t = getSupportFragmentManager().beginTransaction();
				t.replace(R.id.calendar, caldroidFragment);
				//t.addToBackStack(null);
				t.commit();
				popupWindow.dismiss();
				break;
			case R.id.day:
				Time time = new Time();
				time.setToNow();
				Fragment fragment2 = new DayFragment(this, time);
				FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
				ft1.replace(R.id.calendar, fragment2);
				//ft.addToBackStack(null);
				ft1.commit();
				popupWindow.dismiss();
				break;
			case R.id.btn_titlebar_gl://管理
				startActivity(new Intent(Home.this, com.sc.reminder.view.remind.Home.class));
				break;
			case R.id.btn_titlebar_search://搜索
				startActivity(new Intent(Home.this, com.sc.reminder.view.trip.SearchActivity.class));
				break;
			case R.id.tv_main_txsx://提醒事项
				startActivity(new Intent(Home.this, com.sc.reminder.view.remind.Home.class));
				break;
			case R.id.tv_main_bwl://备忘录
				startActivity(new Intent(Home.this, com.sc.reminder.view.memo.Home.class));
				break;
			case R.id.btn_titlebar_right://添加
				Intent intent = new Intent(Home.this, TripEdit.class);
				startActivityForResult(intent, 1);
				break;
		}
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		rl_main_control.setVisibility(View.INVISIBLE);
		ll_main_controler.removeAllViews();
		caldroidFragment.setTrips(td.queryAll());
		caldroidFragment.refreshView();
		
		switch (resultCode) {
			case 1://从新建后返回
				if (null != data) {
					Intent intent = new Intent(Home.this, TripDetail.class);
					intent.putExtra("mid", data.getStringExtra("mid"));
					startActivityForResult(intent, 1);
				}
				break;
				
			case 2://从详情删除后返回
				String delete_mid = data.getStringExtra("mid");
				deleteIds.add(delete_mid);
				break;
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
			}
		return super.onTouchEvent(event);
	}
}
