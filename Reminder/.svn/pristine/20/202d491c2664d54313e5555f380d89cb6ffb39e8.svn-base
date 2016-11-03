package com.sc.reminder.view.remind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.sc.reminder.R;
import com.sc.reminder.database.dao.RemindDetailDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.remind.Users;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.UIUtil;
import com.sc.reminder.utils.net.RequestUtil;

public class DetailList extends Activity implements OnClickListener{

	ImageButton btn_titlebar_left;
	ImageButton btn_titlebar_right;
	LinearLayout ll_home_list_finished;
	LinearLayout ll_home_list_unfinished;
	TextView tv_titlebar_title;
	TextView tv_view_show_finished;
	
	private float downX;
	private float upX;
	
	private float moveX;
	private float moveY;
	
	private float lastMoveX = -1;
	private float lastMoveY = -1;
	
	private float offsetMoveX = 0;
	
	boolean isBoom = false;
	boolean isIgnoreOnce = false;
	
	TranslateAnimation ta_show = null;
	TranslateAnimation ta_hide = null;
	
	View animateView = null;
	
	RemindDetail rd = null;
	
	RemindDetailDao rdd = null;
	
	String mid = "";
	String id = "";
	
	RequestUtil ru = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detaillist_remind);
		
		initView();
		
		rdd = new RemindDetailDao(this);
		
		String title = getIntent().getStringExtra("title");
		tv_titlebar_title.setText(title);
		
		mid = getIntent().getStringExtra("mid");
		id = getIntent().getStringExtra("id");
		
		ru = new RequestUtil();
		
		if (!TextUtils.isEmpty(id)) {
			ScApplication.getInstance().showLoadingDialog();
			HashMap<String, String> params = new HashMap<String, String>();
			LogUtils.e("获取class id = " + id);
			params.put("json", "{\"id\":\""+id+"\"}");
			ru.getRemindListDetail(params, new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					LogUtils.e("getRemindListDetail = " + arg0);
					try {
						//清空数据
						rdd.clearDataByCid(id);
						//添加新数据
						List<RemindDetail> rds = JSON.parseArray(arg0, RemindDetail.class);
						List<RemindDetail> _rds = rdd.queryHasNoIDs(rds, mid, id);
						rdd.addDatas(_rds);
//						//删除服务器上不存在的数据
//						ArrayList<RemindDetail> reminds_all = rdd.queryAllByCid(mid);
//						Iterator<RemindDetail> it = reminds_all.iterator();
//						while (it.hasNext()) {
//							boolean isMatch = false;
//							RemindDetail rm = it.next();
//							
//							for (RemindDetail rm_server : rds) {
//								if (rm_server.getId().equals(rm.getId())) {
//									isMatch = true;
//									break;
//								}
//							}
//							
//							if (!isMatch) {
//								// items.remove(entry.getKey());
//								rdd.deleteData(rm.getMid());
//							}
//						}						
					}catch (Exception e) {
						e.printStackTrace();
					}
					ScApplication.getInstance().dismissLoadingDialog();
					initData();
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					arg0.printStackTrace();
					ScApplication.getInstance().dismissLoadingDialog();
					initData();
				}
			});			
		}
		ta_show = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		ta_show.setFillAfter(true);
		ta_show.setDuration(150);
		ta_show.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				animateView.clearAnimation();
			}
		});
		
		ta_hide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		ta_hide.setFillAfter(true);
		ta_hide.setDuration(150);
		ta_hide.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				animateView.clearAnimation();
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initData();
	}

	private void initData() {
		
		ScApplication.getInstance().showLoadingDialog();
		
		ll_home_list_unfinished.removeAllViews();
		ll_home_list_finished.removeAllViews();
		
		ArrayList<RemindDetail> rds = rdd.queryAllByCid(mid);
		LogUtils.e("rds size = " + rds.size());
		
		for (final RemindDetail rd : rds) {
			
			LogUtils.e("rd = " + rd.getTitle() + "," + rd.getStates() + "," + rd.getReminderTime());
			
			RelativeLayout detail_remind_list_item = (RelativeLayout) View.inflate(this, R.layout.detail_remind_list_item, null);
			detail_remind_list_item.setTag(rd);
			detail_remind_list_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					if (offsetMoveX == 0 || (offsetMoveX < 35 && offsetMoveX > 0) || (offsetMoveX > -35 && offsetMoveX < 0)) {
						
						Intent intent = new Intent(DetailList.this, Detail.class);
						intent.putExtra("mid", ((RemindDetail) view.getTag()).getMid());
						startActivity(intent);
					}
				}
			});
			
			detail_remind_list_item.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					RemindDetail rd = (RemindDetail) v.getTag();
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						
						offsetMoveX = 0;
						isIgnoreOnce = false;
						
						if (isBoom) {
							isBoom = false;
							isIgnoreOnce = true;
							animateView.startAnimation(ta_hide);
							animateView.setTag("hide");
							animateView.setVisibility(View.GONE);
							return false;
						}
						
						downX = event.getX();
						lastMoveX = downX;
						Log.e("yz", "downX = " + downX);
						break;
					case MotionEvent.ACTION_UP:
						if (isIgnoreOnce) {
							return true;
						}
						upX = event.getX();
						Log.e("yz", "upX = " + upX);
						break;
					case MotionEvent.ACTION_MOVE:
						if (isIgnoreOnce) {
							return true;
						}
						moveX = event.getX();
						moveY = event.getY();
						
						float offsetX = (lastMoveX - moveX);
						offsetMoveX += offsetX;
						Log.e("yz", "offsetMoveX = " + offsetMoveX);
						float offsetY = (lastMoveY - moveY);
						
						Log.e("yz", "moveX = " + moveX + ",moveY = " + moveY);
						LinearLayout ll = (LinearLayout) ((RelativeLayout)v).findViewById(R.string.ll_table_item_slip_out);
						if (null == ll) {
							ll = new LinearLayout(DetailList.this);
							ll.setId(R.string.ll_table_item_slip_out);
							ll.setOrientation(LinearLayout.HORIZONTAL);
//							ll.setPadding(UIUtil.getInstance(context).convertDIP2PX(15), 0, UIUtil.getInstance(context).convertDIP2PX(15), 0);
							RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, v.getHeight());
							rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
							ll.setLayoutParams(rl);
							TextView tv = new TextView(DetailList.this);
							tv.setTextColor(Color.parseColor("#ffffff"));
							tv.setText("删除");
							tv.setBackgroundColor(Color.parseColor("#FE0000"));
							LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
							ll1.gravity = Gravity.CENTER;
							tv.setLayoutParams(ll1);
							tv.setPadding(UIUtil.getInstance(DetailList.this).convertDIP2PX(15), 0, UIUtil.getInstance(DetailList.this).convertDIP2PX(10), 0);
							tv.setTag(rd);
							tv.setClickable(true);
							tv.setGravity(Gravity.CENTER);
							tv.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View view) {
									
									CommonUtil.getInstance().delete_remind_detail((RemindDetail) view.getTag());
									
									initData();
								}
							});
							ll.addView(tv);
							ll.setTag("hide");
							ll.setVisibility(View.GONE);
							((RelativeLayout)v).addView(ll);
						}
						String ll_type = (String) ll.getTag();
						if (offsetMoveX > 35 && !isBoom && ll_type.equals("hide")) {
							ll.startAnimation(ta_show);
							ll.setTag("show");
							ll.setVisibility(View.VISIBLE);
							animateView = ll;
							isBoom = true;
						}
						else if (offsetMoveX < 0 && offsetMoveX < -35 && offsetMoveX != 0 && !isBoom && ll_type.equals("show")) {
							if (null != ll) {
								ll.startAnimation(ta_hide);
								ll.setTag("hide");
								ll.setVisibility(View.GONE);
								animateView = ll;
								isBoom = true;
							}
						}
						lastMoveX = moveX;
						lastMoveY = moveY;
						break;
					case MotionEvent.ACTION_CANCEL:
						break;
				}
					return false;
				}
			});
			
			ImageView iv_view_detail_state = (ImageView) detail_remind_list_item.findViewById(R.id.iv_view_detail_state);
			TextView tv_view_detail_title = (TextView) detail_remind_list_item.findViewById(R.id.tv_view_detail_title);
			TextView tv_view_detail_time = (TextView) detail_remind_list_item.findViewById(R.id.tv_view_detail_time);
			TextView tv_view_detail_from = (TextView) detail_remind_list_item.findViewById(R.id.tv_view_detail_from);
			TextView tv_view_detail_from_value = (TextView) detail_remind_list_item.findViewById(R.id.tv_view_detail_from_value);
			
			List<Users> users = rd.getUers();
			if (null != users && users.size() > 0) {
				LinearLayout ll_view_detail_to_status = (LinearLayout) detail_remind_list_item.findViewById(R.id.ll_view_detail_to_status);
				RelativeLayout rl_view_detail_status1 = (RelativeLayout) ll_view_detail_to_status.findViewById(R.id.rl_view_detail_status1);
				RelativeLayout rl_view_detail_status2 = (RelativeLayout) ll_view_detail_to_status.findViewById(R.id.rl_view_detail_status2);
				RelativeLayout rl_view_detail_status3 = (RelativeLayout) ll_view_detail_to_status.findViewById(R.id.rl_view_detail_status3);
				ll_view_detail_to_status.setVisibility(View.VISIBLE);
				
				for (Users user : users) {
					if (!TextUtils.isEmpty(user.getStatus())) {
						switch (Integer.parseInt(user.getStatus())) {
							case 0://拒绝
								rl_view_detail_status1.setVisibility(View.VISIBLE);
								TextView tv_view_detail_status_num1 = (TextView) rl_view_detail_status1.findViewById(R.id.tv_view_detail_status_num);
								int num1 = Integer.parseInt(tv_view_detail_status_num1.getText().toString());
								tv_view_detail_status_num1.setText(String.valueOf(++num1));
								break;
							case 1://进行中
								rl_view_detail_status2.setVisibility(View.VISIBLE);
								TextView tv_view_detail_status_num2 = (TextView) rl_view_detail_status2.findViewById(R.id.tv_view_detail_status_num);
								int num2 = Integer.parseInt(tv_view_detail_status_num2.getText().toString());
								tv_view_detail_status_num2.setText(String.valueOf(++num2));
								break;
							case 2://已完成
								rl_view_detail_status3.setVisibility(View.VISIBLE);
								TextView tv_view_detail_status_num3 = (TextView) rl_view_detail_status3.findViewById(R.id.tv_view_detail_status_num);
								int num3 = Integer.parseInt(tv_view_detail_status_num3.getText().toString());
								tv_view_detail_status_num3.setText(String.valueOf(++num3));
								break;
						}
					}
				}
			}
			
			if (!TextUtils.isEmpty(rd.getStates())) {
				switch (Integer.parseInt(rd.getStates())) {
					case 0://灰色
						iv_view_detail_state.setBackgroundResource(R.drawable.grey);
						break;
					case 1://红色
						iv_view_detail_state.setBackgroundResource(R.drawable.red);
						break;
					case 2://黄色
						iv_view_detail_state.setBackgroundResource(R.drawable.yellow);
						break;
					case 3://绿色
						iv_view_detail_state.setBackgroundResource(R.drawable.green);
						break;
				}				
			}
			
			tv_view_detail_title.setText(rd.getTitle());
			if (!TextUtils.isEmpty(rd.getSourceId())) {
				tv_view_detail_from.setVisibility(View.VISIBLE);
				tv_view_detail_from_value.setVisibility(View.VISIBLE);
				tv_view_detail_from_value.setText(rd.getSourceId());
			}
			if (rd.getStates().equals("2")) {
				//进行中
				tv_view_detail_time.setText(rd.getReminderTime());
				
				ll_home_list_unfinished.addView(detail_remind_list_item);
			}
			else {
				//已完成
				tv_view_detail_time.setText(rd.getUpdatetime());
				
				ll_home_list_finished.addView(detail_remind_list_item);
			}
			
		}
		
		ScApplication.getInstance().dismissLoadingDialog();
	}

	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		btn_titlebar_right = (ImageButton) findViewById(R.id.btn_titlebar_right);
		btn_titlebar_right.setOnClickListener(this);
		tv_view_show_finished = (TextView) findViewById(R.id.tv_view_show_finished);
		tv_view_show_finished.setOnClickListener(this);
		ll_home_list_finished = (LinearLayout) findViewById(R.id.ll_home_list_finished);
		ll_home_list_unfinished = (LinearLayout) findViewById(R.id.ll_home_list_unfinished);
		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left:
				finish();
				break;
			case R.id.btn_titlebar_right:
				Intent intent = new Intent(DetailList.this, DetailEdit.class);
				intent.putExtra("classId", id);
				intent.putExtra("mclassId", mid);
				startActivityForResult(intent, 1);
				break;
			case R.id.tv_view_show_finished:
				if (ll_home_list_finished.getVisibility() == View.VISIBLE) {
					ll_home_list_finished.setVisibility(View.GONE);
				}
				else {
					ll_home_list_finished.setVisibility(View.VISIBLE);
				}
				if (ll_home_list_unfinished.getVisibility() == View.VISIBLE) {
					ll_home_list_unfinished.setVisibility(View.GONE);
					
					tv_view_show_finished.setText("显示未完成项目");
				}
				else {
					ll_home_list_unfinished.setVisibility(View.VISIBLE);
					tv_view_show_finished.setText("显示已结束项目");
				}
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			initData();
			
			if (null != data) {
				Intent intent = new Intent(DetailList.this, Detail.class);
				intent.putExtra("mid", data.getStringExtra("mid"));
				startActivity(intent);	
			}
			
		}
	}
	
}
