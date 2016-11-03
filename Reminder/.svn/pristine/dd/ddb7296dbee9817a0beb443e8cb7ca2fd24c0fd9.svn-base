package com.sc.reminder.view.trip;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.TripDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.ui.MySwitch;
import com.sc.reminder.ui.SwitchButton;
import com.sc.reminder.ui.SwitchButton.OnCheckedChange;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.ScApplication;

public class TripEdit extends Activity implements OnClickListener{
	
	ImageButton btn_titlebar_left;
	TextView tv_titlebar_right;
	MySwitch switch_trip1;
	SwitchButton switch_trip2;
	SwitchButton switch_trip3;
	SwitchButton switch_trip4;
	
	RelativeLayout rl_view_detail_item2;
	RelativeLayout rl_view_trip_remind;
	TextView tv_view_detail_cf_value;
	TextView tv_view_trip_remindtime;
	
	TextView tv_view_trip_starttime;
	TextView tv_view_trip_endtime;
	
	EditText tv_view_trip_title;
	EditText tv_view_trip_content;
	
	TripDao td = null;
	Trip trip = null;
	String mid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.trip_edit);
		
		initView();
		
		mid = getIntent().getStringExtra("mid");
		
		td = new TripDao(this);
		
		if (!TextUtils.isEmpty(mid)) {
			initData();
		}
		
		switch_trip1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				LogUtils.e("isChecked = " + isChecked);
				
				switch_trip2.setChecked(!isChecked);
			}
		});
		switch_trip4.setOnCheckedChange(new OnCheckedChange() {
			
			@Override
			public void onChange(CompoundButton buttonView, boolean isChecked) {
				LogUtils.e("isChecked = " + isChecked);
				tv_view_trip_remindtime.setText("无");
				if (isChecked) {
					rl_view_trip_remind.setTag("1,0");
				}
				else {
					rl_view_trip_remind.setTag("2,5");
				}
			}
		});
		
	}
	
	private void initData() {
		trip = td.queryOne(mid);
		
		tv_view_trip_title.setText(trip.getTitle());
		if (!TextUtils.isEmpty(trip.getTypes())) {
			switch (Integer.parseInt(trip.getTypes())) {
				case 0://会议
					tv_view_detail_cf_value.setText("会议");
					rl_view_detail_item2.setTag("0");
					break;
				case 1://会晤
					tv_view_detail_cf_value.setText("会晤");
					rl_view_detail_item2.setTag("1");
					break;
				case 2://差旅
					tv_view_detail_cf_value.setText("差旅");
					rl_view_detail_item2.setTag("2");
					break;
				case 3://运动
					tv_view_detail_cf_value.setText("运动");
					rl_view_detail_item2.setTag("3");
					break;
				case 4://用餐
					tv_view_detail_cf_value.setText("用餐");
					rl_view_detail_item2.setTag("4");
					break;
				case 5://下午茶
					tv_view_detail_cf_value.setText("下午茶");
					rl_view_detail_item2.setTag("5");
					break;
				case 6://聚会
					tv_view_detail_cf_value.setText("聚会");
					rl_view_detail_item2.setTag("6");
					break;
				case 7://其他
					tv_view_detail_cf_value.setText("其他");
					rl_view_detail_item2.setTag("7");
					break;
			}				
		}
		switch_trip1.setChecked(!Boolean.valueOf(trip.getPrivacy()));
		switch_trip2.setChecked(!Boolean.valueOf(trip.getIsopen()));
		switch_trip3.setChecked(!Boolean.valueOf(trip.getIslock()));
		switch_trip4.setChecked(!Boolean.valueOf(trip.getAllday()));
		tv_view_trip_starttime.setText(trip.getStarttime());
		tv_view_trip_endtime.setText(trip.getEndtime());
		
		if (!TextUtils.isEmpty(trip.getRemindtype())) {
			switch (Integer.parseInt(trip.getRemindtype())) {
				case 0://无
					tv_view_trip_remindtime.setText("无");
					rl_view_trip_remind.setTag("1,0");
					break;
				case 1://事件发生当天（9:00）
					tv_view_trip_remindtime.setText("事件发生当天（9:00）");
					rl_view_trip_remind.setTag("1,1");
					break;
				case 2://1天前（9:00）
					tv_view_trip_remindtime.setText("1天前（9:00）");
					rl_view_trip_remind.setTag("1,2");
					break;
				case 3://运动
					tv_view_trip_remindtime.setText("2天前（9:00）");
					rl_view_trip_remind.setTag("1,3");
					break;
				case 4://用餐
					tv_view_trip_remindtime.setText("1周前");
					rl_view_trip_remind.setTag("1,4");
					break;
				case 5://下午茶
					tv_view_trip_remindtime.setText("无");
					rl_view_trip_remind.setTag("2,5");
					break;
				case 6://聚会
					tv_view_trip_remindtime.setText("事件发生时");
					rl_view_trip_remind.setTag("2,6");
					break;
				case 7://其他
					tv_view_trip_remindtime.setText("5分钟前");
					rl_view_trip_remind.setTag("2,7");
					break;
				case 8://其他
					tv_view_trip_remindtime.setText("15分钟前");
					rl_view_trip_remind.setTag("2,8");
					break;
				case 9://其他
					tv_view_trip_remindtime.setText("30分钟前");
					rl_view_trip_remind.setTag("2,9");
					break;
				case 10://其他
					tv_view_trip_remindtime.setText("1小时前");
					rl_view_trip_remind.setTag("2,10");
					break;
				case 11://其他
					tv_view_trip_remindtime.setText("2小时前");
					rl_view_trip_remind.setTag("2,11");
					break;
				case 12://其他
					tv_view_trip_remindtime.setText("1天前");
					rl_view_trip_remind.setTag("2,12");
					break;
				case 13://其他
					tv_view_trip_remindtime.setText("2天前");
					rl_view_trip_remind.setTag("2,13");
					break;
				case 14://其他
					tv_view_trip_remindtime.setText("自定义");
					rl_view_trip_remind.setTag("2,14");
					break;
			}				
		}		
		
		tv_view_trip_content.setText(trip.getContent());
	}
	
	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		tv_titlebar_right.setOnClickListener(this);
		switch_trip1 = (MySwitch) findViewById(R.id.switch_trip1);
		switch_trip2 = (SwitchButton) findViewById(R.id.switch_trip2);
		switch_trip3 = (SwitchButton) findViewById(R.id.switch_trip3);
		switch_trip4 = (SwitchButton) findViewById(R.id.switch_trip4);
		tv_view_trip_title = (EditText) findViewById(R.id.tv_view_trip_title);
		tv_view_trip_content = (EditText) findViewById(R.id.tv_view_trip_content);
		String time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date());
		tv_view_trip_starttime = (TextView) findViewById(R.id.tv_view_trip_starttime);
		tv_view_trip_starttime.setText(time);
		tv_view_trip_endtime = (TextView) findViewById(R.id.tv_view_trip_endtime);
		tv_view_trip_endtime.setText(time);
		tv_view_detail_cf_value = (TextView) findViewById(R.id.tv_view_detail_cf_value);
		tv_view_trip_remindtime = (TextView) findViewById(R.id.tv_view_trip_remindtime);
		rl_view_detail_item2 = (RelativeLayout) findViewById(R.id.rl_view_detail_item2);
		rl_view_detail_item2.setOnClickListener(this);
		rl_view_detail_item2.setTag("7");
		rl_view_trip_remind = (RelativeLayout) findViewById(R.id.rl_view_trip_remind);
		rl_view_trip_remind.setOnClickListener(this);
		rl_view_trip_remind.setTag("2,5");
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left :
				finish();
				break;
			case R.id.tv_titlebar_right:
				if (TextUtils.isEmpty(tv_view_trip_title.getText().toString())) {
					ScApplication.getInstance().showToast("行程名称必填");
					return;
				}
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				String mid = "";
				Trip trip = new Trip();
				trip.setCreateId(ScApplication.getInstance().getAid());
				String type = (String) rl_view_trip_remind.getTag();
				trip.setRemindtype(type.split(",")[1]);
				trip.setRemindtime("");
				trip.setTitle(tv_view_trip_title.getText().toString());
				trip.setContent(tv_view_trip_content.getText().toString());
				trip.setAllday(String.valueOf(!switch_trip4.isChecked()));
				trip.setAlldate("");
				trip.setPrivacy(String.valueOf(!switch_trip1.isChecked()));
				trip.setIsopen(String.valueOf(!switch_trip2.isChecked()));
				trip.setIslock(String.valueOf(!switch_trip3.isChecked()));
				trip.setTypes((String) rl_view_detail_item2.getTag());
				trip.setStarttime(time);
				trip.setEndtime(time);
				trip.setIssubmit("false");
				if (!TextUtils.isEmpty(this.mid)) {
					//更新
					trip.setMid(this.mid);
					trip.setId(this.trip.getId());
					trip.setSubmittype(RemindDetail.UPDATE);
					CommonUtil.getInstance().update_trip(trip);
					mid = this.mid;
				}
				else {
					//新建
					trip.setId("");
					trip.setSubmittype(RemindDetail.ADD);
					mid = CommonUtil.getInstance().add_trip(trip);
				}
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
				imm.hideSoftInputFromWindow(tv_view_trip_title.getWindowToken(), 0);  
				imm.hideSoftInputFromWindow(tv_view_trip_content.getWindowToken(), 0); 
				Intent intent = new Intent();
				intent.putExtra("mid", mid);
				setResult(1, intent);
				finish();
				break;
			case R.id.rl_view_detail_item2:
				Intent intent2 = new Intent(TripEdit.this, TripEditType.class);
				intent2.putExtra("id", (String) rl_view_detail_item2.getTag());
				startActivityForResult(intent2, 1);
				break;
			case R.id.rl_view_trip_remind:
				Intent intent3 = new Intent(TripEdit.this, TripEditRemindTime.class);
				intent3.putExtra("id", (String) rl_view_trip_remind.getTag());
				startActivityForResult(intent3, 1);
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (null != data) {
			String id = data.getStringExtra("id");
			String name = data.getStringExtra("name");
			switch (resultCode) {
				case 1://选择行程分类
					setType(id, name);
					break;
				case 2://选择提醒时间
					setRemindTime(id, name);
					break;
			}			
		}		
	}

	private void setType(String id, String name) {
		rl_view_detail_item2.setTag(id);
		tv_view_detail_cf_value.setText(name);
	}	
	
	private void setRemindTime(String id, String name) {
		rl_view_trip_remind.setTag(id);
		tv_view_trip_remindtime.setText(name);
	}	
}
