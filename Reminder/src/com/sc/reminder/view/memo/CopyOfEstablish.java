//package com.sc.reminder.view.memo;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Message;
//import android.text.TextUtils;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnLongClickListener;
//import android.view.View.OnTouchListener;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.buihha.audiorecorder.Mp3Recorder;
//import com.buihha.audiorecorder.Mp3Recorder.RecordListener;
//import com.sc.reminder.R;
//import com.sc.reminder.common.base.BaseActivity;
//import com.sc.reminder.common.configure.ActionListener.ActionType;
//import com.sc.reminder.common.configure.Messages;
//import com.sc.reminder.database.dao.ReminderDao;
//import com.sc.reminder.model.memo.EventItem;
//import com.sc.reminder.model.memo.NoteFile;
//import com.sc.reminder.utils.CommonUtil;
//import com.sc.reminder.utils.PlayStone;
//import com.sc.reminder.utils.ScApplication;
//import com.sc.reminder.utils.YzLog;
//
//public class CopyOfEstablish extends BaseActivity{
//	
//	ImageButton btn_titlebar_left;
//	ImageView iv_detail_record_bg;
//	TextView tv_titlebar_title;
//	LinearLayout ll_detail_recordlist;
//	RelativeLayout rl_detail_record_pop;
//	RelativeLayout rl_detail_event_recorder;
//	TextView tv_detail_record_finish;
//	TextView tv_titlebar_right;
//	TextView tv_detail_title;
//	TextView tv_detail_content;
//	Mp3Recorder mp3Recorder;
//	String sdDir = Environment.getExternalStorageDirectory().getPath()
//			+ "/reminder/record/";
//	SimpleDateFormat mDateFormat = new SimpleDateFormat("HHmmss");
//	String processType = "";
//	ArrayList<String> recordFiles = new ArrayList<String>();
//	ArrayList<String> deleteFiles = new ArrayList<String>();
//	ArrayList<ImageView> iv_deletes = new ArrayList<ImageView>();
//	
//	EventItem mei;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.establish);
//		initView();
//		initRecorder();
//		
//		Intent intent = getIntent();	
//		String mid = intent.getStringExtra("mid");
//		
//		if (!TextUtils.isEmpty(mid)) {
//			processType = "bj";
//			tv_titlebar_title.setText("备忘录详情");
//			tv_titlebar_right.setText("编辑");
//			tv_titlebar_right.setTag("bj");
//			tv_detail_title.setEnabled(false);
//			tv_detail_content.setEnabled(false);
//			rl_detail_event_recorder.setVisibility(View.INVISIBLE);
//			
//			ReminderDao rd = new ReminderDao(CopyOfEstablish.this);
//			mei = rd.queryOne(mid);
//			
//			if (null == mei) {
//				return;
//			}
//			
//			tv_detail_title.setText(mei.getTitle());
//			tv_detail_content.setText(mei.getContent());
//			
//			if (mei.getSound().equals("1")) {
//				List<NoteFile> list = mei.getNoteFiles();
//				for (final NoteFile nf : list) {
//					LinearLayout ll_detail_record_item = (LinearLayout) View.inflate(CopyOfEstablish.this, R.layout.ll_detail_record_item, null);
//					LinearLayout ll_detail_record_play = (LinearLayout) ll_detail_record_item.findViewById(R.id.ll_detail_record_play);
//					ImageView iv_detail_delete = (ImageView) ll_detail_record_item.findViewById(R.id.iv_detail_delete);
//					iv_detail_delete.setTag(ll_detail_record_item);
//					iv_detail_delete.setVisibility(View.INVISIBLE);
//					iv_detail_delete.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							ll_detail_recordlist.removeView((View) v.getTag());
//							deleteFiles.add(nf.getMid());
//						}
//					});
//					iv_deletes.add(iv_detail_delete);
//					TextView tv_detail_time = (TextView) ll_detail_record_item.findViewById(R.id.tv_detail_time);
//					ll_detail_record_play.setTag(nf.getMuris());
//					int time = Integer.parseInt(nf.getTime());
//			        int f = time/60;
//			        int m = time%60;
//					tv_detail_time.setText(f+":"+m+"'");
//					ll_detail_recordlist.addView(ll_detail_record_item);
//				}
//			}
//		}
//		else {
//			processType = "xz";
//			tv_titlebar_right.setTag("wc");
//		}
//	}
//
//	private void initView() {
//		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
//		btn_titlebar_left.setOnClickListener(this);
//		iv_detail_record_bg = (ImageView) findViewById(R.id.iv_detail_record_bg);
//		ll_detail_recordlist = (LinearLayout) findViewById(R.id.ll_detail_recordlist);
//		rl_detail_record_pop = (RelativeLayout) findViewById(R.id.rl_detail_record_pop);
//		rl_detail_event_recorder = (RelativeLayout) findViewById(R.id.rl_detail_event_recorder);
//		rl_detail_event_recorder.setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View arg0) {
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
//				imm.hideSoftInputFromWindow(tv_detail_title.getWindowToken(), 0);
//				imm.hideSoftInputFromWindow(tv_detail_content.getWindowToken(), 0);
//				
//				rl_detail_record_pop.setVisibility(View.VISIBLE);
//				try {
//					mp3Recorder.startRecording();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				return false;
//			}
//		});
//		rl_detail_event_recorder.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//					case MotionEvent.ACTION_UP:
//						if (mp3Recorder.isRecording()) {
//							try {
//								mp3Recorder.stopRecording(false);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//						break;
//				}
//				return false;
//			}
//		});
//		tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
//		tv_detail_record_finish = (TextView) findViewById(R.id.tv_detail_record_finish);
//		tv_detail_record_finish.setOnClickListener(this);
//		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
//		tv_titlebar_right.setOnClickListener(this);
//		tv_detail_title = (TextView) findViewById(R.id.tv_detail_title);
//		tv_detail_content = (TextView) findViewById(R.id.tv_detail_content);
//	}
//	
//	private void initRecorder() {
//		mp3Recorder = new Mp3Recorder(CopyOfEstablish.this, new RecordListener() {
//			
//			@Override
//			public void onTimerUpdate(int num) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onRecording() {
//				// TODO Auto-generated method stub
//				mp3Recorder.setDirAndName(sdDir, mDateFormat.format(new Date()) + ".mp3");
//				iv_detail_record_bg.setImageResource(R.anim.anim_recording);
//				Drawable background =  iv_detail_record_bg.getBackground();
//				if (background instanceof AnimationDrawable) {
//					AnimationDrawable animation = (AnimationDrawable) background;
//					animation.start();
//				}
//			}
//			
//			@Override
//			public void onInitFileError() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onDone(File file, int time) {
//				// TODO Auto-generated method stub
//		        int f = time/60;
//		        int m = time%60;
//			       
//				Drawable background =  iv_detail_record_bg.getBackground();
//				if (background instanceof AnimationDrawable) {
//					AnimationDrawable animation = (AnimationDrawable) background;
//					if (animation.isRunning()) {
//						animation.stop();
//					}
//				}	
//				iv_detail_record_bg.setImageResource(R.drawable.im_msg_mic);
//				
//				LinearLayout ll_detail_record_item = (LinearLayout) View.inflate(CopyOfEstablish.this, R.layout.ll_detail_record_item, null);
//				LinearLayout ll_detail_record_play = (LinearLayout) ll_detail_record_item.findViewById(R.id.ll_detail_record_play);
//				ImageView iv_detail_delete = (ImageView) ll_detail_record_item.findViewById(R.id.iv_detail_delete);
//				iv_detail_delete.setTag(ll_detail_record_item);
//				iv_detail_delete.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						ll_detail_recordlist.removeView((View) v.getTag());
//					}
//				});
//				TextView tv_detail_time = (TextView) ll_detail_record_item.findViewById(R.id.tv_detail_time);
//				ll_detail_record_play.setTag(file.getAbsolutePath());
//				tv_detail_time.setText(f+":"+m+"'");
//				ll_detail_recordlist.addView(ll_detail_record_item, 0);
//				recordFiles.add(file.getAbsolutePath() + "&" + time);
//				
//				rl_detail_record_pop.setVisibility(View.GONE);
//			}
//			
//			@Override
//			public void onCancel(File file, int time) {
//				// TODO Auto-generated method stub
//				Drawable background =  iv_detail_record_bg.getBackground();
//				if (background instanceof AnimationDrawable) {
//					AnimationDrawable animation = (AnimationDrawable) background;
//					if (animation.isRunning()) {
//						animation.stop();
//					}
//				}	
//				iv_detail_record_bg.setImageResource(R.drawable.im_msg_mic);
//				file.deleteOnExit();
//				rl_detail_record_pop.setVisibility(View.GONE);
//			}
//		});		
//	}
//	@Override
//	public boolean handleMessage(Message msg) {
//		switch (msg.what) {
//			case ActionType.SEND_MESSAGE:
//				Messages messages = (Messages) msg.obj;
//				switch (messages.id) {
////					case 1:
////						//创建新备忘录成功
////						String id = messages.getResult();
////						YzLog.e("yz", "新备忘录成功");
////						//上传录音文件
////						if (recordFiles.size() > 0) {
////							YzLog.e("yz", "新上传录音文件");
////							for (String filePath : recordFiles) {
////								PostInfo taskinfo = new PostInfo(Establish.this, ActionType.SEND_MESSAGE, handlerID, new Object[] {id, filePath}, 2);
////								TaskCoreManager.getInstance().execute(new PostTask(taskinfo));
////							}
////						}
////						break;
//					case 1:
//						//创建新备忘录成功
//						
//						break;
//				}
//				break;
//			case ActionType.RECEIVER_MESSAGE:
//				break;
//		}
//		return false;
//	}
//	
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.btn_titlebar_left:
//				finish();
//				break;
//			case R.id.tv_titlebar_right:
//				
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
//				imm.hideSoftInputFromWindow(tv_detail_title.getWindowToken(), 0);  
//				imm.hideSoftInputFromWindow(tv_detail_content.getWindowToken(), 0);  
//				
//				String type = (String) v.getTag();
//				if (type.equals("wc")) {
//					String title = tv_detail_title.getText().toString();
//					String content = tv_detail_content.getText().toString();
//					title = TextUtils.isEmpty(title) ? "" : title;
//					content = TextUtils.isEmpty(content) ? "" : content;
//					
//					if (processType.equals("xz")) {
//						//新增
//						//本地增加
//						EventItem ei = new EventItem();
//						ei.setMid("");
//						ei.setId("");
//						ei.setAid(ScApplication.getInstance().getAid());//用户ID
//						ei.setTitle(title);
//						ei.setContent(content);
//						ei.setStar("0");
//						if (recordFiles.size() > 0) {
//							ei.setSound("1");
//						}
//						else {
//							ei.setSound("0");
//						}
//						ei.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//						
//						List<NoteFile> nfils = new ArrayList<NoteFile>();
//						for (String filePath : recordFiles) {
//							String[] s = filePath.split("&");
//							NoteFile nf = new NoteFile();
//							nf.setId("");
//							nf.setRid("");
//							nf.setUris("");
//							nf.setMuris(s[0]);
//							nf.setTime(s[1]);
//							nfils.add(nf);
//						}
//						ei.setNoteFiles(nfils);
//						ei.setIssubmit("false");
//						ei.setSubmittype(EventItem.ADD);
//						
////						AddReminderInfo taskinfo = new AddReminderInfo(Establish.this, ActionType.SEND_MESSAGE, handlerID, new Object[] {ei});
////						TaskCoreManager.getInstance().execute(new AddReminderTask(taskinfo));	
//						CommonUtil.getInstance().add(ei, handlerID);
//					}
//					else if (processType.equals("bj")) {
//						//更新
//						//更新备忘录
//						EventItem ei = new EventItem();
//						ei.setMid(mei.getMid());
//						ei.setId(mei.getId());
//						ei.setAid(mei.getAid());//用户ID
//						ei.setTitle(title);
//						ei.setContent(content);
//						ei.setStar(mei.getStar());
//						if (ll_detail_recordlist.getChildCount() > 0) {
//							ei.setSound("1");
//						}
//						else {
//							ei.setSound("0");
//						}
//						ei.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//						ei.setNoteFiles(null);
//						ei.setIssubmit("false");
//						if (!mei.getSubmittype().equals(EventItem.ADD)) {
//							ei.setSubmittype(EventItem.UPDATE);
//						}
//						else {
//							ei.setSubmittype(mei.getSubmittype());
//						}
//						
////						CommonUtil.getInstance().client_update(ei, deleteFiles, recordFiles);
//						CommonUtil.getInstance().update(ei, deleteFiles, recordFiles);
////						ReminderDao rd = new ReminderDao(Establish.this);
////						rd.updateData(ei);
////						//更新文件
////						NoteListDao nd = new NoteListDao(Establish.this);
////						if (deleteFiles.size() != 0) {
////							for (String mid : deleteFiles) {
////								nd.deleteData(mid);
////							}
////						}
////						if (recordFiles.size() != 0) {
////							List<NoteFile> nfils = new ArrayList<NoteFile>();
////							for (String filePath : recordFiles) {
////								String[] s = filePath.split("&");
////								NoteFile nf = new NoteFile();
////								nf.setId("");
////								nf.setRid("");
////								nf.setUris("");
////								nf.setMuris(s[0]);
////								nf.setTime(s[1]);
////								nfils.add(nf);
////							}
////							nd.addDatas(mei.getMid(), nfils);
////						}
//					}
//					
//					setResult(1);
//					finish();
//				}
//				else if (type.equals("bj")){
//					tv_titlebar_right.setText("完成");
//					tv_titlebar_right.setTag("wc");
//					tv_detail_title.setEnabled(true);
//					tv_detail_content.setEnabled(true);
//					rl_detail_event_recorder.setVisibility(View.VISIBLE);
//					if (iv_deletes.size() != 0) {
//						for (ImageView iv : iv_deletes) {
//							iv.setVisibility(View.VISIBLE);
//						}
//					}
//				}
//				break;
//			case R.id.rl_detail_event_recorder:
//
//				break;
//			case R.id.tv_detail_record_finish:
//
//				break;	
//		}
//	}
//	
//	public void playRecord(View view) {
//		try {
//			ImageView iv_detail_rolling = (ImageView) view.findViewById(R.id.iv_detail_rolling);
//			String path = (String) view.getTag();
////			PlayStone.getInstance().play(path, iv_detail_rolling);
//			YzLog.e("yz", "path = " + path);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		
//		if (null != mp3Recorder && mp3Recorder.isRecording()) {
//			try {
//				mp3Recorder.stopRecording(true);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		PlayStone.getInstance().release();
//	}
//}
