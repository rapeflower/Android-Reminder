package com.sc.reminder.view.trip;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;

public class TripEditType extends Activity implements OnClickListener{
	ImageButton btn_titlebar_left;
	RelativeLayout rl_view_detail_item1;
	RelativeLayout rl_view_detail_item2;
	RelativeLayout rl_view_detail_item3;
	RelativeLayout rl_view_detail_item4;
	RelativeLayout rl_view_detail_item5;
	RelativeLayout rl_view_detail_item6;
	RelativeLayout rl_view_detail_item7;
	RelativeLayout rl_view_detail_item8;
	
	List<RelativeLayout> views = new ArrayList<RelativeLayout>();
	
	String checked = "0";
	String checkedName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.trip_edit_type);
		
		initView();
		
		String id = getIntent().getStringExtra("id");
		
		setCheck(id, false);
	}
	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
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
		views.add(rl_view_detail_item1);
		views.add(rl_view_detail_item2);
		views.add(rl_view_detail_item3);
		views.add(rl_view_detail_item4);
		views.add(rl_view_detail_item5);
		views.add(rl_view_detail_item6);
		views.add(rl_view_detail_item7);
		views.add(rl_view_detail_item8);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left:
				finish();
				break;
			case R.id.rl_view_detail_item1:
				setCheck("0", true);
				break;
			case R.id.rl_view_detail_item2:
				setCheck("1", true);
				break;
			case R.id.rl_view_detail_item3:
				setCheck("2", true);
				break;
			case R.id.rl_view_detail_item4:
				setCheck("3", true);
				break;
			case R.id.rl_view_detail_item5:
				setCheck("4", true);
				break;
			case R.id.rl_view_detail_item6:
				setCheck("5", true);
				break;
			case R.id.rl_view_detail_item7:
				setCheck("6", true);
				break;
			case R.id.rl_view_detail_item8:
				setCheck("7", true);
				break;
		}
	}

	private void setCheck(String id, boolean isFinish) {
		checked = id;
		for (RelativeLayout rl : views) {
			ImageButton ib = (ImageButton) rl.findViewById(R.id.ib_home_list_item_right);
			ib.setVisibility(View.INVISIBLE);
		}
		
		RelativeLayout view = null;
		switch (Integer.parseInt(id)) {
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
		}
		
		ImageButton ib = (ImageButton) view.findViewById(R.id.ib_home_list_item_right);
		ib.setVisibility(View.VISIBLE);
		TextView tv = (TextView) view.findViewById(R.id.tv_view_detail_cf);
		checkedName = tv.getText().toString();
		
		if (isFinish) {
			Intent intent = new Intent();
			intent.putExtra("id", checked);
			intent.putExtra("name", checkedName);
			setResult(1, intent);
			finish();
		}
	}	
}
