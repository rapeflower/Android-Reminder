package com.sc.reminder.view.remind;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.RemindDetailDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.remind.Users;
import com.sc.reminder.utils.CommonUtil;

public class Detail extends Activity implements OnClickListener{

	ImageButton btn_titlebar_left;
	TextView tv_titlebar_right;
	TextView tv_view_detail_title;
	ImageView iv_view_detail_states;
	TextView tv_view_detail_states;
	TextView tv_view_detail_time_value;
	TextView tv_view_detail_cf_value;
	TextView tv_view_detail_jscf_value;
	TextView tv_view_detail_bz_value;
	RelativeLayout rl_view_detail_jblb;
	Button btn_view_detail_wywc;
	Button btn_view_detail_sc;
	RelativeLayout rl_view_detail_item3;
	RelativeLayout rl_view_detail_status1;
	RelativeLayout rl_view_detail_status2;
	RelativeLayout rl_view_detail_status3;
	TextView tv_view_detail_status_num1;
	TextView tv_view_detail_status_num2;
	TextView tv_view_detail_status_num3;
	LinearLayout ll_view_detail_to_status;
	RemindDetailDao rdd;
	RemindDetail rd = null;
	String mid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail_remind);
		
		initView();
		
		mid = getIntent().getStringExtra("mid");
		
		rdd = new RemindDetailDao(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initData();
	}

	private void initData() {
		tv_view_detail_status_num1.setText("0");
		tv_view_detail_status_num2.setText("0");
		tv_view_detail_status_num3.setText("0");
		rl_view_detail_status1.setVisibility(View.GONE);
		rl_view_detail_status2.setVisibility(View.GONE);
		rl_view_detail_status3.setVisibility(View.GONE);
		
		rd = rdd.queryOne(mid);
		if (null == rd) {
			return;
		}
		LogUtils.e("rd.getTitle() = " + rd.getTitle());
		tv_view_detail_title.setText(rd.getTitle());
		if (!TextUtils.isEmpty(rd.getStates())) {
			LogUtils.e("获取 rd.getStates() = " + rd.getStates());
			switch (Integer.parseInt(rd.getStates())) {
				case 0://灰色
					iv_view_detail_states.setBackgroundResource(R.drawable.grey);
					tv_view_detail_states.setText("已撤销");
					break;
				case 1://红色
					iv_view_detail_states.setBackgroundResource(R.drawable.red);
					tv_view_detail_states.setText("已拒绝");
					break;
				case 2://黄色
					iv_view_detail_states.setBackgroundResource(R.drawable.yellow);
					tv_view_detail_states.setText("进行中");
					break;
				case 3://绿色
					iv_view_detail_states.setBackgroundResource(R.drawable.green);
					tv_view_detail_states.setText("已完成");
					
					btn_view_detail_wywc.setVisibility(View.GONE);
					break;
			}				
		}
		tv_view_detail_time_value.setText(rd.getReminderTime());
		if (!TextUtils.isEmpty(rd.getRepeat())) {
			switch (Integer.parseInt(rd.getRepeat())) {
				case 0://永不
					tv_view_detail_cf_value.setText("永不");
					break;
				case 1://每天
					tv_view_detail_cf_value.setText("每天");
					break;
				case 2://每周
					tv_view_detail_cf_value.setText("每周");
					break;
				case 3://每月
					tv_view_detail_cf_value.setText("每月");
					break;
				case 4://每年
					tv_view_detail_cf_value.setText("每年");
					break;
			}				
		}
		if (!TextUtils.isEmpty(rd.getRepeatEnd())) {
			switch (Integer.parseInt(rd.getRepeatEnd())) {
				case 0://事项结束
					tv_view_detail_jscf_value.setText("事项结束");
					rl_view_detail_item3.setVisibility(View.VISIBLE);
					break;
				case 1://永不
					tv_view_detail_jscf_value.setText("永不");
					rl_view_detail_item3.setVisibility(View.VISIBLE);
					break;
				case -1://不显示
					tv_view_detail_jscf_value.setText("事项结束");
					rl_view_detail_item3.setVisibility(View.GONE);
					break;
			}						
		}
		
		tv_view_detail_bz_value.setText(rd.getContent());
		
		List<Users> users = rd.getUers();
		if (null != users && users.size() > 0) {
			ll_view_detail_to_status.setVisibility(View.VISIBLE);
			
			for (Users user : users) {
				if (!TextUtils.isEmpty(user.getStatus())) {
					switch (Integer.parseInt(user.getStatus())) {
						case 0://拒绝
							rl_view_detail_status1.setVisibility(View.VISIBLE);
							int num1 = Integer.parseInt(tv_view_detail_status_num1.getText().toString());
							tv_view_detail_status_num1.setText(String.valueOf(++num1));
							break;
						case 1://进行中
							rl_view_detail_status2.setVisibility(View.VISIBLE);
							int num2 = Integer.parseInt(tv_view_detail_status_num2.getText().toString());
							tv_view_detail_status_num2.setText(String.valueOf(++num2));
							break;
						case 2://已完成
							rl_view_detail_status3.setVisibility(View.VISIBLE);
							int num3 = Integer.parseInt(tv_view_detail_status_num3.getText().toString());
							tv_view_detail_status_num3.setText(String.valueOf(++num3));
							break;
					}
				}
			}
		}		
	}

	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		tv_titlebar_right.setOnClickListener(this);
		rl_view_detail_item3 = (RelativeLayout) findViewById(R.id.rl_view_detail_item3);
		rl_view_detail_status1 = (RelativeLayout) findViewById(R.id.rl_view_detail_status1);
		rl_view_detail_status2 = (RelativeLayout) findViewById(R.id.rl_view_detail_status2);
		rl_view_detail_status3 = (RelativeLayout) findViewById(R.id.rl_view_detail_status3);
		tv_view_detail_status_num1 = (TextView) findViewById(R.id.tv_view_detail_status_num1);
		tv_view_detail_status_num2 = (TextView) findViewById(R.id.tv_view_detail_status_num2);
		tv_view_detail_status_num3 = (TextView) findViewById(R.id.tv_view_detail_status_num3);
		tv_view_detail_title = (TextView) findViewById(R.id.tv_view_detail_title);
		iv_view_detail_states = (ImageView) findViewById(R.id.iv_view_detail_states);
		tv_view_detail_states = (TextView) findViewById(R.id.tv_view_detail_states);
		tv_view_detail_time_value = (TextView) findViewById(R.id.tv_view_detail_time_value);
		tv_view_detail_cf_value = (TextView) findViewById(R.id.tv_view_detail_cf_value);
		tv_view_detail_jscf_value = (TextView) findViewById(R.id.tv_view_detail_jscf_value);
		tv_view_detail_bz_value = (TextView) findViewById(R.id.tv_view_detail_bz_value);
		ll_view_detail_to_status = (LinearLayout) findViewById(R.id.ll_view_detail_to_status);
		rl_view_detail_jblb = (RelativeLayout) findViewById(R.id.rl_view_detail_jblb);
		rl_view_detail_jblb.setOnClickListener(this);
		btn_view_detail_wywc = (Button) findViewById(R.id.btn_view_detail_wywc);
		btn_view_detail_wywc.setOnClickListener(this);
		btn_view_detail_sc = (Button) findViewById(R.id.btn_view_detail_sc);
		btn_view_detail_sc.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left:
				finish();
				break;
			case R.id.tv_titlebar_right:
				Intent intent = new Intent(Detail.this, DetailEdit.class);
				intent.putExtra("mid", mid);
				intent.putExtra("classId", rd.getClassId());
				intent.putExtra("mclassId", rd.getMclassId());
				startActivityForResult(intent, 1);
				break;
			case R.id.rl_view_detail_jblb:
				Intent intent2 = new Intent(Detail.this, AssignList.class);
				intent2.putExtra("mid", mid);
				startActivityForResult(intent2, 1);
				break;
			case R.id.btn_view_detail_wywc:
				rd.setStates("3");
				CommonUtil.getInstance().update_remind_detail(rd, false);
				finish();
				break;
			case R.id.btn_view_detail_sc:
				CommonUtil.getInstance().delete_remind_detail(rd);
				finish();
				break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == 1) {
			initData();
		}
	}
}
