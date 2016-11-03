package com.sc.reminder.view.remind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.sc.reminder.R;
import com.sc.reminder.common.base.BaseActivity;
import com.sc.reminder.database.dao.RemindListDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.Remind;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.CommonUtil.AfterProcess;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.net.RequestUtil;

public class Home extends BaseActivity implements OnClickListener{

	TextView tv_main_txsx;
	TextView tv_main_bwl;
	RelativeLayout rl_remind_item1;
	ScrollView sv_home_list_wrapper;
	LinearLayout ll_home_list_wrapper;
	Button btn_titlebar_left;
	Button btn_titlebar_right;
	
	HashMap<Remind, String> edit_list = new HashMap<Remind, String>();
	
	LinkedList<View> add_views = new LinkedList<View>();
	
	EditText nowFocus = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home_remind);
		
		ScApplication.getInstance().showLoadingDialog();
		
		initView();
		
		initData();
	}

	private void initData() {
		RequestUtil ru = new RequestUtil();
		ru.getRemindList(null, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				LogUtils.e("initData = " + arg0);
				
				try {
					//添加新数据
					List<Remind> reminds = JSON.parseArray(arg0, Remind.class);
					RemindListDao rd = new RemindListDao(Home.this);
					List<Remind> _reminds = rd.queryHasNoIDs(reminds);
					rd.addDatas(_reminds);
					//删除服务器上不存在的数据
					ArrayList<Remind> reminds_all = rd.queryAll();
					Iterator<Remind> it = reminds_all.iterator();
					while (it.hasNext()) {
						boolean isMatch = false;
						Remind rm = it.next();
						
						for (Remind rm_server : reminds) {
							if (rm_server.getId().equals(rm.getId())) {
								isMatch = true;
								break;
							}
						}
						
						if (!isMatch) {
							// items.remove(entry.getKey());
							rd.deleteData(rm.getMid());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				ScApplication.getInstance().showLoadingDialog();
				buildData();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				ScApplication.getInstance().showLoadingDialog();
				buildData();
			}
		});
	}

	private void initView() {
		sv_home_list_wrapper = (ScrollView) findViewById(R.id.sv_home_list_wrapper);
		ll_home_list_wrapper = (LinearLayout) findViewById(R.id.ll_home_list_wrapper);
		btn_titlebar_left = (Button) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		btn_titlebar_right = (Button) findViewById(R.id.btn_titlebar_right);
		btn_titlebar_right.setOnClickListener(this);
		tv_main_txsx = (TextView) findViewById(R.id.tv_main_txsx);
		tv_main_txsx.setOnClickListener(this);
		tv_main_bwl = (TextView) findViewById(R.id.tv_main_bwl);
		tv_main_bwl.setOnClickListener(this);
		rl_remind_item1 = (RelativeLayout) findViewById(R.id.rl_remind_item1);
		rl_remind_item1.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_main_txsx:
				
				break;
			case R.id.tv_main_bwl:
				startActivity(new Intent(Home.this, com.sc.reminder.view.memo.Home.class));
				break;
			case R.id.btn_titlebar_left:
				if (btn_titlebar_left.getText().toString().equals("取消")) {
//					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE); imm.toggleSoftInput( InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//					imm.toggleSoftInput( InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(Home.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					btn_titlebar_right.setText("编辑");
					btn_titlebar_left.setText("关系");
					for (View v : add_views) {
						ll_home_list_wrapper.removeView(v);
					}
//					
//					if (ll_home_list_wrapper.getChildCount() > 1) {
//						for (int i=1;i<ll_home_list_wrapper.getChildCount();i++) {
//							RelativeLayout rl = (RelativeLayout) ll_home_list_wrapper.getChildAt(i);
//							ImageButton ib_home_list_item_left = (ImageButton) rl.findViewById(R.id.ib_home_list_item_left);
//							ib_home_list_item_left.setVisibility(View.INVISIBLE);
//							EditText et_home_List_item_name = (EditText) rl.findViewById(R.id.et_home_List_item_name);
//							et_home_List_item_name.setEnabled(false);
//						}
//					}	
					
					buildData();
				}
				else if (btn_titlebar_left.getText().toString().equals("关系")){
					
				}
				break;
			case R.id.btn_titlebar_right:
				if (btn_titlebar_right.getText().toString().equals("编辑")) {
					edit_list.clear();
					add_views.clear();
					btn_titlebar_right.setText("完成");
					btn_titlebar_left.setText("取消");
					if (ll_home_list_wrapper.getChildCount() > 1) {
						for (int i=1;i<ll_home_list_wrapper.getChildCount();i++) {
							RelativeLayout rl = (RelativeLayout) ll_home_list_wrapper.getChildAt(i);
							ImageView ib_home_list_item_left = (ImageView) rl.findViewById(R.id.ib_home_list_item_left);
							ib_home_list_item_left.setVisibility(View.VISIBLE);
							TextView tv_home_List_item_name = (TextView) rl.findViewById(R.id.tv_home_List_item_name);
							tv_home_List_item_name.setVisibility(View.GONE);
							EditText et_home_List_item_name = (EditText) rl.findViewById(R.id.et_home_List_item_name);
							et_home_List_item_name.setEnabled(true);
							et_home_List_item_name.setVisibility(View.VISIBLE);
						}
					}	

					addNewItem(null);
				}
				else if (btn_titlebar_right.getText().toString().equals("完成")){
//					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE); imm.toggleSoftInput( InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//					imm.toggleSoftInput( InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(Home.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//					for (View v : add_views) {
//						ll_home_list_wrapper.removeView(v);
//					}
					
					btn_titlebar_right.setText("编辑");
					btn_titlebar_left.setText("关系");
					
					//添加
					for (int i=0;i<add_views.size() - 1;i++) {
						View v = add_views.get(i);
						EditText et_home_List_item_name = (EditText) v.findViewById(R.id.et_home_List_item_name);
						
						Remind rm = new Remind();
						rm.setAccountId(ScApplication.getInstance().getAid());
						rm.setTitle(et_home_List_item_name.getText().toString());
						rm.setId("");
						rm.setIssubmit("false");
						rm.setSubmittype(Remind.ADD);
						CommonUtil.getInstance().add_remind(rm, new AfterProcess() {
							
							@Override
							public void onDone() {
								buildData();
							}
						});
					}
					//删除、修改
					Set<Entry<Remind, String>> set = edit_list.entrySet();
					Iterator<Entry<Remind, String>> it = set.iterator();
					while (it.hasNext()) {
						Entry<Remind, String> entry = it.next();
						String way = entry.getValue();
						if (way.equals(Remind.DELETE)) {
							CommonUtil.getInstance().delete_remind(entry.getKey(), new AfterProcess() {
								
								@Override
								public void onDone() {
									buildData();
								}
							});
						}
						else if (way.equals(Remind.UPDATE)) {
							CommonUtil.getInstance().update_remind(entry.getKey(), new AfterProcess() {
								
								@Override
								public void onDone() {
									buildData();
								}
							});
						}
					}
					buildData();
				}
				break;
			case R.id.rl_remind_item1 : 
				Intent intent = new Intent(Home.this, DetailList.class);
				intent.putExtra("title", "提醒事项");
				intent.putExtra("mid", "0");
				intent.putExtra("id", "0");
				startActivity(intent);
				break;
		}
	}

	private void buildData() {
		
//		edit_list.clear();
		add_views.clear();
		
//		LogUtils.e("ll_home_list_wrapper size = " + ll_home_list_wrapper.getChildCount());
		
		while (ll_home_list_wrapper.getChildCount() > 1) {
			ll_home_list_wrapper.removeViewAt(ll_home_list_wrapper.getChildCount() - 1);
		}
		
		ScApplication.getInstance().showLoadingDialog();
		
		RemindListDao rld = new RemindListDao(this);
		ArrayList<Remind> reminds = rld.queryAll();
		
		LogUtils.e("reminds size = " + reminds.size());
		
		for (final Remind rm : reminds) { 
			LogUtils.e("rm id = " + rm.getId());
			LogUtils.e("rm mid = " + rm.getMid());
			RelativeLayout home_remind_list_item = (RelativeLayout) View.inflate(this, R.layout.home_remind_list_item, null);
			home_remind_list_item.setTag(rm);
			final EditText et_home_List_item_name = (EditText) home_remind_list_item.findViewById(R.id.et_home_List_item_name);
			final TextView tv_home_List_item_name = (TextView) home_remind_list_item.findViewById(R.id.tv_home_List_item_name);
			final ImageView ib_home_list_item_left = (ImageView) home_remind_list_item.findViewById(R.id.ib_home_list_item_left);
			ib_home_list_item_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					View vp = (View) view.getParent();
					Remind rm = (Remind) vp.getTag();
					LogUtils.e("delete is = " + rm.getMid());
					edit_list.put(rm, Remind.DELETE);
//					buildData();
					
					ll_home_list_wrapper.removeView(vp);
				}
			});
			tv_home_List_item_name.setText(rm.getTitle());
			et_home_List_item_name.setText(rm.getTitle());
			et_home_List_item_name.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
					LogUtils.e("onTextChanged = " + cs);
					View vp = (View) et_home_List_item_name.getParent();
					Remind rm = (Remind) vp.getTag();
					rm.setTitle(cs.toString());
					edit_list.put(rm, Remind.UPDATE);
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					
				}
			});
			et_home_List_item_name.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						nowFocus = et_home_List_item_name;
					}
				}
			});
			home_remind_list_item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(Home.this, DetailList.class);
					intent.putExtra("title", rm.getTitle());
					intent.putExtra("mid", rm.getMid());
					intent.putExtra("id", rm.getId());
					startActivity(intent);
				}
			});
			ll_home_list_wrapper.addView(home_remind_list_item);
		}
		
		ScApplication.getInstance().dismissLoadingDialog();
	}
	
	
	private void addNewItem(View lastView) {
		if (null != lastView) {
			Object tag = lastView.getTag();
			if (null != tag && (Integer)tag == 1) {
				return;
			}
			else {
				lastView.setTag(1);
			}
		}
		
		RelativeLayout home_remind_list_new = (RelativeLayout) View.inflate(Home.this, R.layout.home_remind_list_new, null);
		final EditText et_home_List_item_name = (EditText) home_remind_list_new.findViewById(R.id.et_home_List_item_name);
		et_home_List_item_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 0) {
					addNewItem(et_home_List_item_name);
				}
				else {
					et_home_List_item_name.setTag(0);
					ll_home_list_wrapper.removeView(add_views.getLast());
					add_views.removeLast();
				}
			}
		});
		et_home_List_item_name.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					nowFocus = et_home_List_item_name;
				}
			}
		});
		ll_home_list_wrapper.addView(home_remind_list_new);
		add_views.add(home_remind_list_new);
		new Handler().post(new Runnable() {
		    @Override
		    public void run() {
		    	int offset = ll_home_list_wrapper.getMeasuredHeight() - sv_home_list_wrapper.getHeight();
		    	if (offset < 0) {
		    		offset = 0;
		    	}
		    	sv_home_list_wrapper.smoothScrollTo(0, offset);
		    }
		});
	}

	public void toDetail(View view) {
		LogUtils.e("toDetail");
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		
		return false;
	}
	
}
