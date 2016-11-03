package com.sc.reminder.view.trip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.TripDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.view.remind.AssignList;
import com.sc.reminder.view.remind.Contacts;

public class TripDetail extends Activity implements OnClickListener{

	ImageButton btn_titlebar_left;
	TextView tv_titlebar_right;
	
	ImageView iv_view_trip_detail_type;
	ImageView iv_view_trip_detail_lock;
	ImageView iv_view_trip_detail_open;
	ImageView iv_view_trip_detail_pri;
	TextView tv_view_trip_detail_title;
	TextView tv_view_trip_detail_starttime;
	TextView tv_view_trip_detail_endtime;
	TextView tv_view_trip_detail_remindtime;
	TextView tv_view_trip_detail_content;
	Button btn_view_trip_detail_sc;
	RelativeLayout rl_view_trip_detail_temp2;
	
	String mid = "";
	
	TripDao td = null;
	Trip trip = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.trip_detail);
		
		initView();
		
		mid = getIntent().getStringExtra("mid");
		
		td = new TripDao(this);
		
		if (!TextUtils.isEmpty(mid)) {
			initData();
		}
	}
	
	private void initData() {
		trip = td.queryOne(mid);
		
		switch (Integer.parseInt(trip.getTypes())) {
			case 0:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type0);
				break;
			case 1:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type1);
				break;
			case 2:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type2);
				break;
			case 3:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type3);
				break;
			case 4:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type4);
				break;
			case 5:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type5);
				break;
			case 6:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type6);
				break;
			case 7:
				iv_view_trip_detail_type.setBackgroundResource(R.drawable.trip_type7);
				break;
		}
		
		tv_view_trip_detail_title.setText(trip.getTitle());
		
		if (Boolean.valueOf(trip.getIslock())) {
			iv_view_trip_detail_lock.setVisibility(View.VISIBLE);
		}
		if (Boolean.valueOf(trip.getIsopen())) {
			iv_view_trip_detail_open.setVisibility(View.VISIBLE);
		}
		if (Boolean.valueOf(trip.getPrivacy())) {
			iv_view_trip_detail_pri.setVisibility(View.VISIBLE);
		}
		
		tv_view_trip_detail_starttime.setText(trip.getStarttime());
		tv_view_trip_detail_endtime.setText(trip.getEndtime());
		
		if (!TextUtils.isEmpty(trip.getRemindtype())) {
			switch (Integer.parseInt(trip.getRemindtype())) {
				case 0://无
					tv_view_trip_detail_remindtime.setText("无");
					tv_view_trip_detail_remindtime.setTag("1,0");
					break;
				case 1://事件发生当天（9:00）
					tv_view_trip_detail_remindtime.setText("事件发生当天（9:00）");
					tv_view_trip_detail_remindtime.setTag("1,1");
					break;
				case 2://1天前（9:00）
					tv_view_trip_detail_remindtime.setText("1天前（9:00）");
					tv_view_trip_detail_remindtime.setTag("1,2");
					break;
				case 3://运动
					tv_view_trip_detail_remindtime.setText("2天前（9:00）");
					tv_view_trip_detail_remindtime.setTag("1,3");
					break;
				case 4://用餐
					tv_view_trip_detail_remindtime.setText("1周前");
					tv_view_trip_detail_remindtime.setTag("1,4");
					break;
				case 5://下午茶
					tv_view_trip_detail_remindtime.setText("无");
					tv_view_trip_detail_remindtime.setTag("2,5");
					break;
				case 6://聚会
					tv_view_trip_detail_remindtime.setText("事件发生时");
					tv_view_trip_detail_remindtime.setTag("2,6");
					break;
				case 7://其他
					tv_view_trip_detail_remindtime.setText("5分钟前");
					tv_view_trip_detail_remindtime.setTag("2,7");
					break;
				case 8://其他
					tv_view_trip_detail_remindtime.setText("15分钟前");
					tv_view_trip_detail_remindtime.setTag("2,8");
					break;
				case 9://其他
					tv_view_trip_detail_remindtime.setText("30分钟前");
					tv_view_trip_detail_remindtime.setTag("2,9");
					break;
				case 10://其他
					tv_view_trip_detail_remindtime.setText("1小时前");
					tv_view_trip_detail_remindtime.setTag("2,10");
					break;
				case 11://其他
					tv_view_trip_detail_remindtime.setText("2小时前");
					tv_view_trip_detail_remindtime.setTag("2,11");
					break;
				case 12://其他
					tv_view_trip_detail_remindtime.setText("1天前");
					tv_view_trip_detail_remindtime.setTag("2,12");
					break;
				case 13://其他
					tv_view_trip_detail_remindtime.setText("2天前");
					tv_view_trip_detail_remindtime.setTag("2,13");
					break;
				case 14://其他
					tv_view_trip_detail_remindtime.setText("自定义");
					tv_view_trip_detail_remindtime.setTag("2,14");
					break;
			}				
		}	
		
		tv_view_trip_detail_content.setText(trip.getContent());
	}

	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		tv_titlebar_right.setOnClickListener(this);	
		rl_view_trip_detail_temp2 = (RelativeLayout) findViewById(R.id.rl_view_trip_detail_temp2);
		rl_view_trip_detail_temp2.setOnClickListener(this);
		iv_view_trip_detail_type = (ImageView) findViewById(R.id.iv_view_trip_detail_type);
		iv_view_trip_detail_lock = (ImageView) findViewById(R.id.iv_view_trip_detail_lock);
		iv_view_trip_detail_open = (ImageView) findViewById(R.id.iv_view_trip_detail_open);
		iv_view_trip_detail_pri = (ImageView) findViewById(R.id.iv_view_trip_detail_pri);
		tv_view_trip_detail_title = (TextView) findViewById(R.id.tv_view_trip_detail_title);
		tv_view_trip_detail_starttime = (TextView) findViewById(R.id.tv_view_trip_detail_starttime);
		tv_view_trip_detail_endtime = (TextView) findViewById(R.id.tv_view_trip_detail_endtime);
		tv_view_trip_detail_remindtime = (TextView) findViewById(R.id.tv_view_trip_detail_remindtime);
		tv_view_trip_detail_content = (TextView) findViewById(R.id.tv_view_trip_detail_content);
		btn_view_trip_detail_sc = (Button) findViewById(R.id.btn_view_trip_detail_sc);
		btn_view_trip_detail_sc.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left :
				finish();
				break;
			case R.id.tv_titlebar_right:
				Intent intent = new Intent(TripDetail.this, TripEdit.class);
				intent.putExtra("mid", mid);
				startActivityForResult(intent, 1);
				break;		
			case R.id.btn_view_trip_detail_sc:
				CommonUtil.getInstance().delete_trip(trip);
				Intent intent2 = new Intent();
				intent2.putExtra("mid", mid);
				setResult(2, intent2);
				finish();
				break;		
			case R.id.rl_view_trip_detail_temp2:
				Intent intent3 = new Intent(TripDetail.this, Contacts.class);
				startActivityForResult(intent3, 1);
				break;		
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		initData();
	}

}
