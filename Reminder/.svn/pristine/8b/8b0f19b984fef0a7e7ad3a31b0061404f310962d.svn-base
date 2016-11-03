package com.sc.reminder.view.remind;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.sc.reminder.R;
import com.sc.reminder.database.dao.RemindDetailDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.net.RequestUtil;

public class DetailEdit extends Activity implements OnClickListener{

	ImageButton btn_titlebar_left;
	TextView tv_titlebar_right;
	TextView tv_view_detail_time_value;
	EditText et_view_detail_title;
	RelativeLayout rl_view_detail_item2;//重复
	RelativeLayout rl_view_detail_item3;//结束重复
	TextView tv_view_detail_cf_value;
	TextView tv_view_detail_jscf_value;
	EditText tv_view_detail_bz_value;
	RemindDetailDao rdd;
	RemindDetail rd = null;	
	String mid;
	String classId;
	String mclassId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail_remind_edit);
		
		initView();
		
		mid = getIntent().getStringExtra("mid");
		classId = getIntent().getStringExtra("classId");
		mclassId = getIntent().getStringExtra("mclassId");
		
		LogUtils.e("本地的 classId = " + mclassId);
		
		rdd = new RemindDetailDao(this);
		
		if (!TextUtils.isEmpty(mid)) {
			initData();
		}
	}

	private void initData() {
		
		rd = rdd.queryOne(mid);
		
		et_view_detail_title.setText(rd.getTitle());
		tv_view_detail_time_value.setText(rd.getReminderTime());
		tv_view_detail_bz_value.setText(rd.getContent());
		
		if (!TextUtils.isEmpty(rd.getRepeat())) {
			switch (Integer.parseInt(rd.getRepeat())) {
				case 0://永不
					tv_view_detail_cf_value.setText("永不");
					rl_view_detail_item2.setTag("0");
					break;
				case 1://每天
					tv_view_detail_cf_value.setText("每天");
					rl_view_detail_item2.setTag("1");
					break;
				case 2://每周
					tv_view_detail_cf_value.setText("每周");
					rl_view_detail_item2.setTag("2");
					break;
				case 3://每月
					tv_view_detail_cf_value.setText("每月");
					rl_view_detail_item2.setTag("3");
					break;
				case 4://每年
					tv_view_detail_cf_value.setText("每年");
					rl_view_detail_item2.setTag("4");
					break;
			}				
		}
		if (!TextUtils.isEmpty(rd.getRepeatEnd())) {
			switch (Integer.parseInt(rd.getRepeatEnd())) {
				case 0://事项结束
					tv_view_detail_jscf_value.setText("事项结束");
					rl_view_detail_item3.setVisibility(View.VISIBLE);
					rl_view_detail_item3.setTag("0");
					break;
				case 1://永不
					tv_view_detail_jscf_value.setText("永不");
					rl_view_detail_item3.setVisibility(View.VISIBLE);
					rl_view_detail_item3.setTag("1");
					break;
				case -1://不显示
					tv_view_detail_jscf_value.setText("事项结束");
					rl_view_detail_item3.setVisibility(View.GONE);
					rl_view_detail_item3.setTag("-1");
					break;
			}						
		}
	}

	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		tv_titlebar_right.setOnClickListener(this);
		et_view_detail_title = (EditText) findViewById(R.id.et_view_detail_title);
		tv_view_detail_bz_value = (EditText) findViewById(R.id.tv_view_detail_bz_value);
		tv_view_detail_time_value = (TextView) findViewById(R.id.tv_view_detail_time_value);
		tv_view_detail_cf_value = (TextView) findViewById(R.id.tv_view_detail_cf_value);
		tv_view_detail_jscf_value = (TextView) findViewById(R.id.tv_view_detail_jscf_value);
		rl_view_detail_item2 = (RelativeLayout) findViewById(R.id.rl_view_detail_item2);
		rl_view_detail_item2.setOnClickListener(this);
		rl_view_detail_item2.setTag("0");
		rl_view_detail_item3 = (RelativeLayout) findViewById(R.id.rl_view_detail_item3);
		rl_view_detail_item3.setOnClickListener(this);
		rl_view_detail_item3.setTag("-1");
		
		tv_view_detail_time_value.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left :
				finish();
				break;
			case R.id.tv_titlebar_right:
				
				if (TextUtils.isEmpty(et_view_detail_title.getText().toString())) {
					ScApplication.getInstance().showToast("提醒事项名称必填");
					return;
				}
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				String mid = "";
				RemindDetail rd = new RemindDetail();
				rd.setClassId(classId);
				rd.setMclassId(mclassId);
				rd.setCreateId(ScApplication.getInstance().getAid());
				rd.setReminderTime(time);
				rd.setTitle(et_view_detail_title.getText().toString());
				rd.setContent(tv_view_detail_bz_value.getText().toString());
				rd.setRepeat((String) rl_view_detail_item2.getTag());
				rd.setRepeatEnd((String) rl_view_detail_item3.getTag());
				rd.setStates("2");
				rd.setIsdel("0");
				rd.setUpdatetime(time);
				rd.setIssubmit("false");
				if (!TextUtils.isEmpty(this.mid)) {
					//更新
					rd.setMid(this.mid);
					rd.setId(this.rd.getId());
					rd.setSourceId(this.rd.getSourceId());
					rd.setCreateTime(this.rd.getCreateTime());
					rd.setSubmittype(RemindDetail.UPDATE);
					CommonUtil.getInstance().update_remind_detail(rd, false);
					mid = this.mid;
				}
				else {
					//新建
					rd.setId("");
					rd.setSourceId(ScApplication.getInstance().getAid());
					rd.setCreateTime(time);
					rd.setSubmittype(RemindDetail.ADD);
					mid = CommonUtil.getInstance().add_remind_detail(rd);
				}
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
				imm.hideSoftInputFromWindow(et_view_detail_title.getWindowToken(), 0);  
				imm.hideSoftInputFromWindow(tv_view_detail_bz_value.getWindowToken(), 0); 
				Intent intent = new Intent();
				intent.putExtra("mid", mid);
				setResult(1, intent);
				finish();
				break;
			case R.id.rl_view_detail_item2:
				Intent intent2 = new Intent(DetailEdit.this, DetailEditRepeat.class);
				intent2.putExtra("id", (String) rl_view_detail_item2.getTag());
				startActivityForResult(intent2, 1);
				break;
			case R.id.rl_view_detail_item3:
				Intent intent3 = new Intent(DetailEdit.this, DetailEditRepeatCancel.class);
				intent3.putExtra("id", (String) rl_view_detail_item3.getTag());
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
				case 1://选择重复
					setRepeat(id, name);
					break;
				case 2 ://选择取消重复
					setRepeatCancel(id, name);
					break;
			}			
		}
	}
	
	private void setRepeatCancel(String id, String name) {
		rl_view_detail_item3.setTag(id);
		tv_view_detail_jscf_value.setText(name);
	}

	private void setRepeat(String id, String name) {
		rl_view_detail_item2.setTag(id);
		tv_view_detail_cf_value.setText(name);
		if (!id.equals("0")) {
			rl_view_detail_item3.setVisibility(View.VISIBLE);
			rl_view_detail_item3.setTag("0");
			tv_view_detail_jscf_value.setText("事项结束");
		}
		else {
			rl_view_detail_item3.setVisibility(View.GONE);
			rl_view_detail_item3.setTag("-1");
		}
	}
	
}
