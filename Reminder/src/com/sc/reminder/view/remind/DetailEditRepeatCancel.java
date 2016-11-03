package com.sc.reminder.view.remind;

import java.util.ArrayList;
import java.util.List;

import com.sc.reminder.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailEditRepeatCancel extends Activity implements OnClickListener{

	ImageButton btn_titlebar_left;
	TextView tv_titlebar_right;
	RelativeLayout rl_view_detail_item1;
	RelativeLayout rl_view_detail_item2;
	
	List<RelativeLayout> views = new ArrayList<RelativeLayout>();
	
	String checked = "0";
	String checkedName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail_remind_edit_repeat_cancel);
		
		initView();
		
		String id = getIntent().getStringExtra("id");
		
		setCheck(id);
	}

	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		tv_titlebar_right.setOnClickListener(this);
		rl_view_detail_item1 = (RelativeLayout) findViewById(R.id.rl_view_detail_item1);
		rl_view_detail_item1.setOnClickListener(this);
		rl_view_detail_item2 = (RelativeLayout) findViewById(R.id.rl_view_detail_item2);
		rl_view_detail_item2.setOnClickListener(this);
		views.add(rl_view_detail_item1);
		views.add(rl_view_detail_item2);
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
				setCheck("0");
				break;
			case R.id.rl_view_detail_item2:
				setCheck("1");
				break;
				
		}
	}
	
	private void setCheck(String id) {
		checked = id;
		for (RelativeLayout rl : views) {
			ImageView iv = (ImageView) rl.findViewById(R.id.iv_view_detail_item_confirm);
			iv.setVisibility(View.INVISIBLE);
		}
		
		RelativeLayout view = null;
		switch (Integer.parseInt(id)) {
			case 0:
				view = rl_view_detail_item1;
				break;
			case 1:
				view = rl_view_detail_item2;
				break;
		}
		
		ImageView iv = (ImageView) view.findViewById(R.id.iv_view_detail_item_confirm);
		iv.setVisibility(View.VISIBLE);
		TextView tv = (TextView) view.findViewById(R.id.tv_view_detail_title);
		checkedName = tv.getText().toString();
	}	
}
