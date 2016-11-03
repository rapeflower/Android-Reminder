package com.sc.reminder.view.trip;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.log.LogUtils;

public class TripEditRemindTime extends Activity implements OnClickListener{
	ImageButton btn_titlebar_left;
	TextView tv_titlebar_right;
	RelativeLayout rl_view_detail_item1;
	RelativeLayout rl_view_detail_item2;
	RelativeLayout rl_view_detail_item3;
	RelativeLayout rl_view_detail_item4;
	RelativeLayout rl_view_detail_item5;
	RelativeLayout rl_view_detail_item6;
	RelativeLayout rl_view_detail_item7;
	RelativeLayout rl_view_detail_item8;
	RelativeLayout rl_view_detail_item9;
	RelativeLayout rl_view_detail_item10;
	RelativeLayout rl_view_detail_item11;
	RelativeLayout rl_view_detail_item12;
	RelativeLayout rl_view_detail_item13;
	RelativeLayout rl_view_detail_item14;
	RelativeLayout rl_view_detail_item15;
	LinearLayout ll_view_detail_item_wrapper;
	LinearLayout ll_view_detail_item_wrapper1;
	List<RelativeLayout> views = new ArrayList<RelativeLayout>();
	
	String checked = "0";
	String checkedName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.trip_edit_remindtime);
		
		initView();
		
		String id = getIntent().getStringExtra("id");
		
		setCheck(id);
	}
	private void initView() {
		ll_view_detail_item_wrapper = (LinearLayout) findViewById(R.id.ll_view_detail_item_wrapper);
		ll_view_detail_item_wrapper1 = (LinearLayout) findViewById(R.id.ll_view_detail_item_wrapper1);
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		tv_titlebar_right.setOnClickListener(this);
		rl_view_detail_item1 = (RelativeLayout) findViewById(R.id.rl_view_detail_item1);
		rl_view_detail_item1.setOnClickListener(this);
		rl_view_detail_item2 = (RelativeLayout) findViewById(R.id.rl_view_detail_item2);
		rl_view_detail_item2.setOnClickListener(this);
		rl_view_detail_item3 = (RelativeLayout) findViewById(R.id.rl_view_detail_item3);
		rl_view_detail_item3.setOnClickListener(this);
		rl_view_detail_item4 = (RelativeLayout) findViewById(R.id.rl_view_detail_item4);
		rl_view_detail_item4.setOnClickListener(this);
		rl_view_detail_item5 = (RelativeLayout) findViewById(R.id.rl_view_detail_item5);
		rl_view_detail_item5.setOnClickListener(this);
		rl_view_detail_item6 = (RelativeLayout) findViewById(R.id.rl_view_detail_item6);
		rl_view_detail_item6.setOnClickListener(this);
		rl_view_detail_item7 = (RelativeLayout) findViewById(R.id.rl_view_detail_item7);
		rl_view_detail_item7.setOnClickListener(this);
		rl_view_detail_item8 = (RelativeLayout) findViewById(R.id.rl_view_detail_item8);
		rl_view_detail_item8.setOnClickListener(this);
		rl_view_detail_item9 = (RelativeLayout) findViewById(R.id.rl_view_detail_item9);
		rl_view_detail_item9.setOnClickListener(this);
		rl_view_detail_item10 = (RelativeLayout) findViewById(R.id.rl_view_detail_item10);
		rl_view_detail_item10.setOnClickListener(this);
		rl_view_detail_item11 = (RelativeLayout) findViewById(R.id.rl_view_detail_item11);
		rl_view_detail_item11.setOnClickListener(this);
		rl_view_detail_item12 = (RelativeLayout) findViewById(R.id.rl_view_detail_item12);
		rl_view_detail_item12.setOnClickListener(this);
		rl_view_detail_item13 = (RelativeLayout) findViewById(R.id.rl_view_detail_item13);
		rl_view_detail_item13.setOnClickListener(this);
		rl_view_detail_item14 = (RelativeLayout) findViewById(R.id.rl_view_detail_item14);
		rl_view_detail_item14.setOnClickListener(this);
		rl_view_detail_item15 = (RelativeLayout) findViewById(R.id.rl_view_detail_item15);
		rl_view_detail_item15.setOnClickListener(this);
		views.add(rl_view_detail_item1);
		views.add(rl_view_detail_item2);
		views.add(rl_view_detail_item3);
		views.add(rl_view_detail_item4);
		views.add(rl_view_detail_item5);
		views.add(rl_view_detail_item6);
		views.add(rl_view_detail_item7);
		views.add(rl_view_detail_item8);
		views.add(rl_view_detail_item9);
		views.add(rl_view_detail_item10);
		views.add(rl_view_detail_item11);
		views.add(rl_view_detail_item12);
		views.add(rl_view_detail_item13);
		views.add(rl_view_detail_item14);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left:
				finish();
				break;
			case R.id.tv_titlebar_right:
				Intent intent = new Intent();
				intent.putExtra("id", checked);
				intent.putExtra("name", checkedName);
				setResult(2, intent);
				finish();
				break;
			case R.id.rl_view_detail_item1:
				setCheck("1,0");
				break;
			case R.id.rl_view_detail_item2:
				setCheck("1,1");
				break;
			case R.id.rl_view_detail_item3:
				setCheck("1,2");
				break;
			case R.id.rl_view_detail_item4:
				setCheck("1,3");
				break;
			case R.id.rl_view_detail_item5:
				setCheck("1,4");
				break;
			case R.id.rl_view_detail_item6:
				setCheck("2,5");
				break;
			case R.id.rl_view_detail_item7:
				setCheck("2,6");
				break;
			case R.id.rl_view_detail_item8:
				setCheck("2,7");
				break;
			case R.id.rl_view_detail_item9:
				setCheck("2,8");
				break;
			case R.id.rl_view_detail_item10:
				setCheck("2,9");
				break;
			case R.id.rl_view_detail_item11:
				setCheck("2,10");
				break;
			case R.id.rl_view_detail_item12:
				setCheck("2,11");
				break;
			case R.id.rl_view_detail_item13:
				setCheck("2,12");
				break;
			case R.id.rl_view_detail_item14:
				setCheck("2,13");
				break;
			case R.id.rl_view_detail_item15:
				setCheck("3,14");
				break;
		}
	}

	private void setCheck(String id) {
		LogUtils.e("id = " + id);
		String[] check = id.split(",");
		
		checked = id;
		for (RelativeLayout rl : views) {
			ImageButton ib = (ImageButton) rl.findViewById(R.id.ib_home_list_item_right);
			ib.setVisibility(View.INVISIBLE);
		}
		
		RelativeLayout view = null;
		switch (Integer.parseInt(check[1])) {
			case 0:
				view = rl_view_detail_item1;
				break;
			case 1:
				view = rl_view_detail_item2;
				break;
			case 2:
				view = rl_view_detail_item3;
				break;
			case 3:
				view = rl_view_detail_item4;
				break;
			case 4:
				view = rl_view_detail_item5;
				break;
			case 5:
				view = rl_view_detail_item6;
				break;
			case 6:
				view = rl_view_detail_item7;
				break;
			case 7:
				view = rl_view_detail_item8;
				break;
			case 8:
				view = rl_view_detail_item9;
				break;
			case 9:
				view = rl_view_detail_item10;
				break;
			case 10:
				view = rl_view_detail_item11;
				break;
			case 11:
				view = rl_view_detail_item12;
				break;
			case 12:
				view = rl_view_detail_item13;
				break;
			case 13:
				view = rl_view_detail_item14;
				break;
			case 14:
				view = rl_view_detail_item15;
				break;
		}
		
		switch (Integer.parseInt(check[0])) {
			case 1:
				ll_view_detail_item_wrapper.setVisibility(View.VISIBLE);
				ll_view_detail_item_wrapper1.setVisibility(View.GONE);
				ImageButton ib = (ImageButton) view.findViewById(R.id.ib_home_list_item_right);
				ib.setVisibility(View.VISIBLE);
				break;
			case 2:
				ll_view_detail_item_wrapper.setVisibility(View.GONE);
				ll_view_detail_item_wrapper1.setVisibility(View.VISIBLE);
				ImageButton ib2 = (ImageButton) view.findViewById(R.id.ib_home_list_item_right);
				ib2.setVisibility(View.VISIBLE);
				break;
			case 3:
				
				break;
		}
		TextView tv = (TextView) view.findViewById(R.id.tv_view_detail_cf);
		checkedName = tv.getText().toString();
	}	
}
