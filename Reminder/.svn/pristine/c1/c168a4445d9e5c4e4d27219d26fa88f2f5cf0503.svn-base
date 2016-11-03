package com.sc.reminder.view.memo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.common.base.BaseActivity;
import com.sc.reminder.common.base.TaskCoreManager;
import com.sc.reminder.common.configure.ActionListener.ActionType;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.Item;
import com.sc.reminder.model.memo.TimeItem;
import com.sc.reminder.task.home.EventInfo;
import com.sc.reminder.task.home.EventTask;
import com.sc.reminder.task.home.GetAllIDInfo;
import com.sc.reminder.task.home.GetAllIDTask;
import com.sc.reminder.ui.StickyScrollView;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.UIUtil;

public class Home extends BaseActivity{

	TextView tv_main_txsx;
	TextView tv_main_bwl;
	
	StickyScrollView ssv_home_data;
	StickyScrollView ssv_home_data_line;
	LinearLayout ssv_home_data_list;
	LinearLayout ssv_home_data_line_list;
	HashMap<String, List<View>> table_views = new HashMap<String, List<View>>();
	HashMap<String, List<View>> list_views = new HashMap<String, List<View>>();
	ArrayAdapter<Item> adapter;
	CheckBox btn_titlebar_left;
	ImageButton btn_titlebar_right;
	int type = 1;//1 - table 2 - list
	
	private float downX;
	private float upX;
	
	private float moveX;
	private float moveY;
	
	private float lastMoveX = -1;
	private float lastMoveY = -1;
	
	private float offsetMoveX = 0;
	
	boolean isBoom = false;
	
	TranslateAnimation ta_show = null;
	TranslateAnimation ta_hide = null;
	
	View animateView = null;
	boolean isIgnoreOnce = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		initView();
		ScApplication.getInstance().showLoadingDialog();
		//初始化CommonUtil
		CommonUtil.getInstance();
		GetAllIDInfo taskinfo = new GetAllIDInfo(Home.this, ActionType.SEND_MESSAGE, handlerID);
		TaskCoreManager.getInstance().execute(new GetAllIDTask(taskinfo));
		
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
	
	private void initView() {
		ssv_home_data = (StickyScrollView) findViewById(R.id.ssv_home_data);
		ssv_home_data_line = (StickyScrollView) findViewById(R.id.ssv_home_data_line);
		ssv_home_data_list = (LinearLayout) ssv_home_data.getChildAt(0);
		ssv_home_data_line_list = (LinearLayout) ssv_home_data_line.getChildAt(0);
		btn_titlebar_left = (CheckBox) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		btn_titlebar_right = (ImageButton) findViewById(R.id.btn_titlebar_right);
		btn_titlebar_right.setOnClickListener(this);
		tv_main_txsx = (TextView) findViewById(R.id.tv_main_txsx);
		tv_main_txsx.setOnClickListener(this);
		tv_main_bwl = (TextView) findViewById(R.id.tv_main_bwl);
		tv_main_bwl.setOnClickListener(this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case ActionType.SEND_MESSAGE:
				Messages messages = (Messages) msg.obj;
				switch (messages.id) {
					case 1:
//						EventInfo taskinfo = new EventInfo(Home.this, ActionType.SEND_MESSAGE, handlerID, false);
//						TaskCoreManager.getInstance().execute(new EventTask(taskinfo));
						break;
					case 2 :
						//得到分组集合和数据集合，然后执行initList
						refresh();
						break;
				}
				break;
			case ActionType.RECEIVER_MESSAGE:
				break;
		}
		return false;
	}

	private void initList(int type) throws ParseException {
		
		HashMap<String, TimeItem> timeItems = ScApplication.getInstance().getTimeItems();
		final HashMap<String, EventItem> resultList = ScApplication.getInstance().getResultList();
		Set<Entry<String, TimeItem>> set = timeItems.entrySet();
		Iterator<Entry<String, TimeItem>> it = set.iterator();
		
		int isStartClose = 0;
		
		switch (type) {
			case 1:
				
				ssv_home_data_list.removeAllViews();
				table_views.clear();
				ssv_home_data.notifyStickyAttributeChanged();
				while (it.hasNext()) {
					Entry<String, TimeItem> entry = it.next();
					TimeItem ti = entry.getValue();
					
					LinearLayout ssv_item = (LinearLayout) View.inflate(this, R.layout.lv_home_table_item_sec, null);
					TextView tv = ((TextView) ssv_item.getChildAt(0));
					tv.setText(ti.getTime());
					tv.setTag("s");
					tv.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View view) {
							String tag = (String) view.getTag();
							List<View> views = table_views.get(((TextView) view).getText().toString());
							if (tag.equals("s")) {
								for (View v : views) {
									v.setVisibility(View.GONE);
									view.setTag("h");
								}
							}
							else {
								for (View v : views) {
									v.setVisibility(View.VISIBLE);
									view.setTag("s");
								}
							}
						}
					});
					ssv_home_data_list.addView(ssv_item);
					
					List<String> items = ti.getItems();
					int count = 0;
					LinearLayout tb_view_row = null;
					RelativeLayout item_view = null;
					
					if (null != items && items.size() != 0) {
						for (String id : items) {
							final EventItem ei = resultList.get(id);
							if (null == ei) {
								continue;
							}
							if (count == 0) {
								tb_view_row = (LinearLayout) View.inflate(this, R.layout.event_view_row, null);
								item_view = (RelativeLayout) tb_view_row.getChildAt(0);
								ssv_home_data_list.addView(tb_view_row);
								if (table_views.containsKey(ti.getTime())) {
									List<View> views = table_views.get(ti.getTime());
									views.add(tb_view_row);
								}
								else {
									List<View> views = new ArrayList<View>();
									views.add(tb_view_row);
									table_views.put(ti.getTime(), views);
								}
								count++;
							}
							else {
								item_view = (RelativeLayout) tb_view_row.getChildAt(1);
								count = 0;
							}
							item_view.setVisibility(View.VISIBLE);
							item_view.setTag(ei);
							if (null != resultList && resultList.containsKey(id)) {
								TextView tv_view_table_event_title = (TextView) item_view.findViewById(R.id.tv_view_table_event_title);
								TextView tv_view_table_event_content = (TextView) item_view.findViewById(R.id.tv_view_table_event_content);
								TextView tv_view_table_event_time_clock = (TextView) item_view.findViewById(R.id.tv_view_table_event_time_clock);
								ImageView iv_view_table_event_sound = (ImageView) item_view.findViewById(R.id.iv_view_table_event_sound);
								
								tv_view_table_event_title.setText(ei.getTitle());
								tv_view_table_event_content.setText(ei.getContent());
								
								java.text.DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = format1.parse(ei.getCreatetime());
								
								tv_view_table_event_time_clock.setText(DateFormat.getDateFormat(this).format(date) + " " + DateFormat.getTimeFormat(this).format(date));
								if ("1".equals(ei.getSound())) {
									iv_view_table_event_sound.setVisibility(View.VISIBLE);
								}
							}
							
							item_view.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									
									if (offsetMoveX == 0 || (offsetMoveX < 35 && offsetMoveX > 0) || (offsetMoveX > -35 && offsetMoveX < 0)) {
										Intent intent = new Intent(Home.this, Establish.class);
										intent.putExtra("mid", ei.getMid());
										Home.this.startActivityForResult(intent, 1);
									}
								}
							});
							
							item_view.setOnTouchListener(new OnTouchListener() {
								
								@Override
								public boolean onTouch(View v, MotionEvent event) {
									EventItem _ei = (EventItem) v.getTag();
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
											return true;
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
											ll = new LinearLayout(Home.this);
											ll.setId(R.string.ll_table_item_slip_out);
											ll.setOrientation(LinearLayout.VERTICAL);
//											ll.setPadding(UIUtil.getInstance(Home.this).convertDIP2PX(15), 0, UIUtil.getInstance(Home.this).convertDIP2PX(15), 0);
											RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, v.findViewById(R.id.rl_view_table_event_wrapper).getHeight());
											rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
											ll.setLayoutParams(rl);
											ll.setBackgroundColor(Home.this.getResources().getColor(android.R.color.black));
											final ImageView iv1 = new ImageView(Home.this);
											LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
											ll1.gravity = Gravity.CENTER;
											iv1.setLayoutParams(ll1);
											iv1.setPadding(UIUtil.getInstance(Home.this).convertDIP2PX(15), 0, UIUtil.getInstance(Home.this).convertDIP2PX(15), 0);
											if (_ei.getStar().equals("1")) {
												iv1.setImageResource(R.drawable.star_sel);
											}
											else {
												iv1.setImageResource(R.drawable.star);
											}
											iv1.setTag(_ei.getMid());
											iv1.setClickable(true);
											iv1.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													String id = (String) v.getTag();
													
													EventItem ei = resultList.get(id);
													if (ei.getStar().equals("1")) {
														ei.setStar("0");
													}
													else {
														ei.setStar("1");
													}
													resultList.remove(id);
													resultList.put(id, ei);
													ScApplication.getInstance().setTimeItems(CommonUtil.getInstance().convert(resultList));
													ScApplication.getInstance().setResultList(resultList);
													refresh();
													
													CommonUtil.getInstance().update(ei, null, null);
												}
											});
											ImageView iv2 = new ImageView(Home.this);
											LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
											ll2.gravity = Gravity.CENTER;
											iv2.setLayoutParams(ll2);
											iv2.setPadding(UIUtil.getInstance(Home.this).convertDIP2PX(15), 0, UIUtil.getInstance(Home.this).convertDIP2PX(15), 0);
											iv2.setImageResource(R.drawable.delete);
											iv2.setTag(_ei.getMid());
											iv2.setClickable(true);
											iv2.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													String id = (String) v.getTag();
													EventItem ei = resultList.get(id);
													CommonUtil.getInstance().delete(ei);
													
													resultList.remove(ei.getMid());
													ScApplication.getInstance().setResultList(resultList);
													HashMap<String, TimeItem>  map = CommonUtil.getInstance().convert(resultList);
													ScApplication.getInstance().setTimeItems(map);
													refresh();
												}
											});
											ll.addView(iv1);
											ll.addView(iv2);
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
						}
					}
					
					if (isStartClose < 2) {
						isStartClose++;
					}
					else {
						tv.performClick();
					}
				}
				
				break;
			case 2:
				
				ssv_home_data_line_list.removeAllViews();
				list_views.clear();
				ssv_home_data_line.notifyStickyAttributeChanged();
				while (it.hasNext()) {
					Entry<String, TimeItem> entry = it.next();
					TimeItem ti = entry.getValue();
					
					LinearLayout ssv_item = (LinearLayout) View.inflate(this, R.layout.lv_home_list_item_sec, null);
					TextView tv = ((TextView) ssv_item.getChildAt(0));
					tv.setText(ti.getTime());
					tv.setTag("s");
					tv.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View view) {
							String tag = (String) view.getTag();
							List<View> views = list_views.get(((TextView) view).getText().toString());
							if (tag.equals("s")) {
								for (View v : views) {
									v.setVisibility(View.GONE);
									view.setTag("h");
								}
							}
							else {
								for (View v : views) {
									v.setVisibility(View.VISIBLE);
									view.setTag("s");
								}
							}
						}
					});
					
					ssv_home_data_line_list.addView(ssv_item);
					
					List<String> items = ti.getItems();
					RelativeLayout item_view = null;
					
					if (null != items && items.size() != 0) {
						for (String id : items) {
							item_view = (RelativeLayout) View.inflate(this, R.layout.event_view_list_item, null);
							ssv_home_data_line_list.addView(item_view);	
							if (list_views.containsKey(ti.getTime())) {
								List<View> views = list_views.get(ti.getTime());
								views.add(item_view);
							}
							else {
								List<View> views = new ArrayList<View>();
								views.add(item_view);
								list_views.put(ti.getTime(), views);
							}
							if (null != resultList && resultList.containsKey(id)) {
								final EventItem ei = resultList.get(id);
								item_view.setTag(ei);
								TextView tv_event_list_content = (TextView) item_view.findViewById(R.id.tv_event_list_content);
								ImageView iv_view_list_event_sound = (ImageView) item_view.findViewById(R.id.iv_view_list_event_sound);
								
								tv_event_list_content.setText(ei.getTitle());
								if ("1".equals(ei.getSound())) {
									iv_view_list_event_sound.setVisibility(View.VISIBLE);
								}
								
								item_view.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View arg0) {
										if (offsetMoveX == 0 || (offsetMoveX < 35 && offsetMoveX > 0) || (offsetMoveX > -35 && offsetMoveX < 0)) {
											Intent intent = new Intent(Home.this, Establish.class);
											intent.putExtra("mid", ei.getMid());
											Home.this.startActivityForResult(intent, 1);
										}
									}
								});
								
								item_view.setOnTouchListener(new OnTouchListener() {
									
									@Override
									public boolean onTouch(View v, MotionEvent event) {
										EventItem _ei = (EventItem) v.getTag();
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
												ll = new LinearLayout(Home.this);
												ll.setId(R.string.ll_table_item_slip_out);
												ll.setOrientation(LinearLayout.HORIZONTAL);
//												ll.setPadding(UIUtil.getInstance(context).convertDIP2PX(15), 0, UIUtil.getInstance(context).convertDIP2PX(15), 0);
												RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, v.getHeight());
												rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
												ll.setLayoutParams(rl);
												ll.setBackgroundColor(Home.this.getResources().getColor(android.R.color.black));
												ImageView iv1 = new ImageView(Home.this);
												LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
												ll1.gravity = Gravity.CENTER;
												iv1.setLayoutParams(ll1);
												iv1.setPadding(UIUtil.getInstance(Home.this).convertDIP2PX(15), 0, UIUtil.getInstance(Home.this).convertDIP2PX(10), 0);
												if (_ei.getStar().equals("1")) {
													iv1.setImageResource(R.drawable.star_sel);
												}
												else {
													iv1.setImageResource(R.drawable.star);
												}
												iv1.setTag(_ei.getMid());
												iv1.setClickable(true);
												iv1.setOnClickListener(new OnClickListener() {
													
													@Override
													public void onClick(View v) {
														String id = (String) v.getTag();
														
														EventItem ei = resultList.get(id);
														if (ei.getStar().equals("1")) {
															ei.setStar("0");
														}
														else {
															ei.setStar("1");
														}
														resultList.remove(id);
														resultList.put(id, ei);
														ScApplication.getInstance().setTimeItems(CommonUtil.getInstance().convert(resultList));
														ScApplication.getInstance().setResultList(resultList);
														refresh();
														
														CommonUtil.getInstance().update(ei, null, null);
													}
												});
												ImageView iv2 = new ImageView(Home.this);
												LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
												ll2.gravity = Gravity.CENTER;
												iv2.setLayoutParams(ll2);
												iv2.setPadding(UIUtil.getInstance(Home.this).convertDIP2PX(10), 0, UIUtil.getInstance(Home.this).convertDIP2PX(15), 0);
												iv2.setImageResource(R.drawable.delete);
												iv2.setTag(_ei.getMid());
												iv2.setClickable(true);
												iv2.setOnClickListener(new OnClickListener() {
													
													@Override
													public void onClick(View v) {
														String id = (String) v.getTag();
														EventItem ei = resultList.get(id);
														CommonUtil.getInstance().delete(ei);
														
														resultList.remove(ei.getMid());
														ScApplication.getInstance().setResultList(resultList);
														HashMap<String, TimeItem>  map = CommonUtil.getInstance().convert(resultList);
														ScApplication.getInstance().setTimeItems(map);
														refresh();
													}
												});
												ll.addView(iv1);
												ll.addView(iv2);
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
							}			
						}
					}
					
					if (isStartClose < 2) {
						isStartClose++;
					}
					else {
						tv.performClick();
					}
				}
				break;
		}
		ScApplication.getInstance().dismissLoadingDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_main_txsx:
				startActivity(new Intent(Home.this, com.sc.reminder.view.remind.Home.class));
				break;
			case R.id.tv_main_bwl:
				break;		
			case R.id.btn_titlebar_left:
				switchLayout();
				break;
			case R.id.btn_titlebar_right:
				Intent intent = new Intent(Home.this, Establish.class);
				startActivityForResult(intent, 1);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			EventInfo taskinfo = new EventInfo(Home.this, ActionType.SEND_MESSAGE, handlerID, false);
			TaskCoreManager.getInstance().execute(new EventTask(taskinfo));
//			Message msg = new Message();
//			msg.what = ActionType.SEND_MESSAGE;
//			msg.obj = new Messages(2);
//			getCurrentHandler().sendMessage(msg);
		}
	}
	
	private void switchLayout() {
		switch (type) {
			case 1:
				ssv_home_data.setVisibility(View.GONE);
				ssv_home_data_line.setVisibility(View.VISIBLE);
				type = 2;
				break;
			case 2:
				ssv_home_data.setVisibility(View.VISIBLE);
				ssv_home_data_line.setVisibility(View.GONE);
				type = 1;
				break;
		}		
	}
	
	public void refresh() {
		LogUtils.e("刷新数据");
		try {
			initList(1);
			initList(2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}