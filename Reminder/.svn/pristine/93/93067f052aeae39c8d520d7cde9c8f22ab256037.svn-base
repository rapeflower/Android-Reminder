package com.sc.reminder.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.Listener;
import com.sc.reminder.common.base.NotificationManager;
import com.sc.reminder.common.base.TaskCoreManager;
import com.sc.reminder.common.configure.ActionListener.ActionType;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.database.dao.NoteListDao;
import com.sc.reminder.database.dao.RemindDetailDao;
import com.sc.reminder.database.dao.RemindListDao;
import com.sc.reminder.database.dao.ReminderDao;
import com.sc.reminder.database.dao.TripDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.Note;
import com.sc.reminder.model.memo.NoteFile;
import com.sc.reminder.model.memo.NoteWithID;
import com.sc.reminder.model.memo.TimeItem;
import com.sc.reminder.model.remind.Remind;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.task.home.AddReminderInfo;
import com.sc.reminder.task.home.AddReminderTask;
import com.sc.reminder.task.home.GetAllDiffInfo;
import com.sc.reminder.task.home.GetAllDiffTask;
import com.sc.reminder.task.net.DeleteInfo;
import com.sc.reminder.task.net.DeleteTask;
import com.sc.reminder.task.net.PostInfo;
import com.sc.reminder.task.net.PostTask;
import com.sc.reminder.task.net.UpdateInfo;
import com.sc.reminder.task.net.UpdateTask;
import com.sc.reminder.utils.net.RequestUtil;

public class CommonUtil {

	private static CommonUtil commonUtil;
	protected String handlerID;

	private final static int TIME_NUMBERS = 60;
	private final static int TIME_HOURSES = 24;
	private final static int TIME_MILLISECONDS = 1000;

	RequestUtil ru = new RequestUtil();
	
	private CommonUtil() {
		handlerID = NotificationManager.getInstance().registerNotice(
				creatHandler());
	}

	public static CommonUtil getInstance() {
		if (commonUtil == null) {
			commonUtil = new CommonUtil();
		}
		return commonUtil;
	}

	public HashMap<String, TimeItem> convert(HashMap<String, EventItem> items) {
		YzLog.e("yz", "convert = " + items.size());
		HashMap<String, TimeItem> timeItems = new HashMap<String, TimeItem>();
		try {
			Set<Entry<String, EventItem>> set = items.entrySet();
			Iterator<Entry<String, EventItem>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, EventItem> entry = it.next();
				EventItem ei = entry.getValue();
				YzLog.e("yz", "nima = " + ei.getTitle());
				// String date = new SimpleDateFormat("yyyy-MM").format(new
				// Date(Long.parseLong(ei.getCreatetime())));
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = format1.parse(ei.getCreatetime());
				String fdate = new SimpleDateFormat("yyyy-MM").format(date);
				YzLog.e("yz", "date = " + fdate);

				if (ei.getStar().equals("1")) {
					if (!timeItems.containsKey("star")) {
						TimeItem timeItem = new TimeItem();
						timeItem.setTime("星标备忘");
						timeItem.setItems(new ArrayList<String>());
						timeItems.put("star", timeItem);
					}
					TimeItem timeItem = timeItems.get("star");
					timeItem.getItems().add(ei.getMid());
				} else {
					if (!timeItems.containsKey(fdate)) {
						String[] _date = fdate.split("-");
						TimeItem timeItem = new TimeItem();
						timeItem.setTime(_date[0] + "年" + _date[1] + "月");
						timeItem.setItems(new ArrayList<String>());
						timeItems.put(fdate, timeItem);
					}
					TimeItem timeItem = timeItems.get(fdate);
					timeItem.getItems().add(ei.getMid());
				}

			}
			
			Set<Entry<String, TimeItem>> _set = timeItems.entrySet();
			Iterator<Entry<String, TimeItem>> _it = _set.iterator();
			while (_it.hasNext()) {
				Entry<String, TimeItem> entry = _it.next();
				TimeItem ti = entry.getValue();
				List<String> list = ti.getItems();
				
				Collections.sort(list, new Comparator<String>() {

					@Override
					public int compare(String s1, String s2) {
						if (Integer.parseInt(s1) > Integer.parseInt(s2)) {
							return -1;
						}
						else {
							return 1;
						}
					}

				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeItems;
	}

	/* 
	 * 
	 * 备忘录相关--开始
	 * 
	 * 
	 * */
	public void client_add(EventItem ei, String handlerID, String mHandlerID) {
		AddReminderInfo taskinfo = new AddReminderInfo(getContext(),
				ActionType.SEND_MESSAGE, handlerID, new Object[] { ei,
						mHandlerID });
		TaskCoreManager.getInstance().execute(new AddReminderTask(taskinfo));
	}

	public String client_delete(EventItem ei) {
		String id = "";
		ReminderDao rd = new ReminderDao(getContext());
		if (ei.getSubmittype().equals(EventItem.ADD)) {
			// 物理删除
			rd.deleteData(ei.getMid());
		} else {
			ei.setIssubmit("false");
			ei.setSubmittype(EventItem.DELETE);
			id = rd.updateData(ei);
		}

		return id;
	}

	public void client_update(EventItem ei, ArrayList<String> deleteFiles,
			ArrayList<String> recordFiles) {
		ReminderDao rd = new ReminderDao(getContext());
		rd.updateData(ei);
		// 更新文件
		NoteListDao nd = new NoteListDao(getContext());
		if (null != deleteFiles && deleteFiles.size() != 0) {
			for (String mid : deleteFiles) {
				nd.deleteData(mid);
			}
		}
		if (null != recordFiles && recordFiles.size() != 0) {
			List<NoteFile> nfils = new ArrayList<NoteFile>();
			for (String filePath : recordFiles) {
				String[] s = filePath.split("&");
				NoteFile nf = new NoteFile();
				nf.setId("");
				nf.setRid("");
				nf.setUris("");
				nf.setMuris(s[0]);
				nf.setTime(s[1]);
				nfils.add(nf);
			}
			nd.addDatas(ei.getMid(), nfils);
		}
	}

	public void server_add(EventItem ei) {
		YzLog.e("yz", "ei的 再次 = " + ei.getMid());
		// 联网增加
		Note note = new Note();
		note.setTitle(ei.getTitle());
		note.setContent(ei.getContent());
		note.setStar("0");
		List<NoteFile> nfiles = ei.getNoteFiles();
		if (nfiles.size() > 0) {
			note.setSound("1");
		} else {
			note.setSound("0");
		}
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		note.setCreatetime(date);
		// ei.setNoteFiles(noteFiles);
		PostInfo taskinfo = new PostInfo(getContext(), ActionType.SEND_MESSAGE,
				handlerID, new Object[] { note, nfiles, ei }, 1);
		TaskCoreManager.getInstance().execute(new PostTask(taskinfo));
	}

	public void server_delete(String id) {
		DeleteInfo taskinfo = new DeleteInfo(getContext(),
				ActionType.SEND_MESSAGE, handlerID, new Object[] { id });
		TaskCoreManager.getInstance().execute(new DeleteTask(taskinfo));
	}

	public void server_update(EventItem ei) {
		NoteWithID nwi = new NoteWithID();
		nwi.setId(ei.getId());
		nwi.setTitle(ei.getTitle());
		nwi.setContent(ei.getContent());
		nwi.setStar(ei.getStar());
		nwi.setSound(ei.getSound());
		nwi.setCreatetime(ei.getCreatetime());
		UpdateInfo taskinfo = new UpdateInfo(getContext(),
				ActionType.SEND_MESSAGE, handlerID, new Object[] { nwi });
		TaskCoreManager.getInstance().execute(new UpdateTask(taskinfo));
	}

	public void add(EventItem ei, String handlerID) {
		client_add(ei, handlerID, this.handlerID);
	}

	public void delete(EventItem ei) {
		String id = client_delete(ei);
		if (!id.equals("")) {
			if (checkNet()) {
				server_delete(id);
			}
		}
	}

	public void update(EventItem ei, ArrayList<String> deleteFiles,
			ArrayList<String> recordFiles) {
		client_update(ei, deleteFiles, recordFiles);
		if (!ei.getSubmittype().equals(EventItem.ADD)) {
			if (checkNet()) {
				server_update(ei);
			}
		}
	}

	/* 
	 * 
	 * 备忘录相关--结束
	 * 
	 * 
	 * */	
	
	/* 
	 * 
	 * 提醒事项分类相关--开始
	 * 
	 * 
	 * */		
	
	public void client_add_remind(Remind rm, AfterProcess ap) {
		RemindListDao rld = new RemindListDao(getContext());

		// TestInfo taskinfo = new TestInfo(getContext(),
		// ActionType.SEND_MESSAGE, handlerID, new Object[] {rm.getTitle()});
		// TaskCoreManager.getInstance().execute(new TestTask(taskinfo));
		Remind rd = rld.addData(rm);
		if (checkNet()) {
			server_add_remind(rd, ap);
		}
	}

	public void server_add_remind(final Remind rm, final AfterProcess ap) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("json", "{\"title\":\"" + rm.getTitle() + "\"}");
		ru.newRemindList(params, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				
				try {
					JSONObject jo = JSON.parseObject(arg0);
					LogUtils.e("新建id = " + jo.getString("id"));
					rm.setId(jo.getString("id"));
					rm.setIssubmit("true");
					rm.setSubmittype(Remind.IDLE);
					RemindListDao rld = new RemindListDao(getContext());
					rld.updateData(rm);
					
					if (null != ap) {
						ap.onDone();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void add_remind(Remind rm, AfterProcess ap) {
		client_add_remind(rm, ap);
	}

	public String client_delete_remind(Remind rm, AfterProcess ap) {
		String id = "";
		RemindListDao rld = new RemindListDao(getContext());
		if (rm.getSubmittype().equals(Remind.ADD)) {
			// 物理删除
			rld.deleteData(rm.getMid());
		} else {
			rm.setIssubmit("false");
			rm.setSubmittype(Remind.DELETE);
			id = rld.updateData(rm);
			
			if (checkNet() && !id.equals("")) {
				server_delete_remind(id, ap);
			}
		}

		return id;
	}

	public void server_delete_remind(final String id, final AfterProcess ap) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("json", "{\"id\":\"" + id + "\"}");
		ru.deleteRemindList(params, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				
				try {
					LogUtils.e("delete = " + arg0);
					RemindListDao rld = new RemindListDao(getContext());
					rld.deleteDataByID(id);
					
					if (null != ap) {
						ap.onDone();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	public void delete_remind(Remind rm, AfterProcess ap) {
		client_delete_remind(rm, ap);
	}

	public void client_update_remind(Remind rm, AfterProcess ap) {
		rm.setIssubmit("false");
		if (!rm.getSubmittype().equals(EventItem.ADD)) {
			rm.setSubmittype(EventItem.UPDATE);
		}

		RemindListDao rld = new RemindListDao(getContext());
		rld.updateData(rm);
		
		if (checkNet() && !rm.getSubmittype().equals(EventItem.ADD)) {
			server_update_remind(rm, ap);
		}
	}

	public void server_update_remind(final Remind rm, final AfterProcess ap) {
		HashMap<String, String> params = new HashMap<String, String>();
		LogUtils.e("更新 = " + rm.getId());
		params.put("json", "{\"id\":\"" + rm.getId() + "\",\"title\":\""+rm.getTitle()+"\"}");
		ru.updateRemindList(params, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				
				try {
					LogUtils.e("update = " + arg0);
					rm.setIssubmit("true");
					rm.setSubmittype(EventItem.IDLE);
					RemindListDao rld = new RemindListDao(getContext());
					rld.updateData(rm);
					
					if (null != ap) {
						ap.onDone();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	public void update_remind(Remind rm, AfterProcess ap) {
		client_update_remind(rm, ap);
	}

	/* 
	 * 
	 * 提醒事项分类相关--结束
	 * 
	 * 
	 * */		

	
	/* 
	 * 
	 * 提醒事项详情相关--开始
	 * 
	 * 
	 * */		
	
	public String client_add_remind_detail(RemindDetail rd) {
		String mid = "";
		
		RemindDetailDao rdl = new RemindDetailDao(getContext());
		
		RemindDetail _rd = rdl.addData(rd);
		
//		server_add_remind_detail(_rd);
		
		if (null != _rd) {
			mid = _rd.getMid();
			
			server_add_remind_detail(_rd);
		}
		
		return mid;
	}
	
	public void server_add_remind_detail(final RemindDetail rd) {
		if (null != rd) {
			if (!TextUtils.isEmpty(rd.getClassId())) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("json", "{\"classId\":\""+rd.getClassId()+
									"\",\"sourceId\":\""+rd.getSourceId()+
									"\",\"createId\":\""+rd.getCreateId()+
									"\",\"title\":\""+rd.getTitle()+
									"\",\"repeat\":\""+rd.getRepeat()+
									"\",\"repeatEnd\":\""+rd.getRepeatEnd()+
									"\",\"reminderTime\":\""+rd.getReminderTime()+
									"\",\"content\":\""+rd.getContent()+"\"}");
				ru.newRemindDetail(params, new Listener<String>() {

					@Override
					public void onResponse(String s) {
						LogUtils.e("newRemindDetail = " + s);
						RemindDetailDao rdd = new RemindDetailDao(getContext());
						rd.setId(s);
						rd.setIssubmit("true");
						rd.setSubmittype(RemindDetail.IDLE);
						rdd.updateData(rd, false);
					}
				});			
			}			
		}
	}
	
	public String add_remind_detail(RemindDetail rd) {
		return client_add_remind_detail(rd);
	}
	
	public String client_delete_remind_detail(RemindDetail rd) {
		String id = "";
		RemindDetailDao rdd = new RemindDetailDao(getContext());
		if (rd.getSubmittype().equals(Remind.ADD)) {
			// 物理删除
			rdd.deleteData(rd.getMid());
		} else {
			rd.setIssubmit("false");
			rd.setSubmittype(Remind.DELETE);
			id = rdd.updateData(rd, false);
			
			if (checkNet() && !id.equals("")) {
				server_delete_remind_detail(rd);
			}
		}

		return id;
	}

	public void server_delete_remind_detail(final RemindDetail rd) {
		if (null != rd) {
			if (!TextUtils.isEmpty(rd.getClassId())) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("json", "{\"id\":\"" + rd.getId() + "\",\"classId\":\""+rd.getClassId()+"\"}");
				ru.deleteReminderDetail(params, new Listener<String>() {

					@Override
					public void onResponse(String s) {
						LogUtils.e("deleteReminderDetail = " + s);
						
						RemindDetailDao rdd = new RemindDetailDao(getContext());
						rdd.deleteData(rd.getMid());
					}
				});			
			}			
		}
	}

	public void delete_remind_detail(RemindDetail rd) {
		client_delete_remind_detail(rd);
	}	
	
	public void client_update_remind_detail(RemindDetail rd, boolean isUsers) {
		rd.setIssubmit("false");
		if (!rd.getSubmittype().equals(EventItem.ADD)) {
			rd.setSubmittype(EventItem.UPDATE);
		}

		RemindDetailDao rdd = new RemindDetailDao(getContext());
		rdd.updateData(rd, isUsers);
		
		if (checkNet() && !rd.getSubmittype().equals(EventItem.ADD)) {
			server_update_remind_detail(rd);
		}
	}

	public void server_update_remind_detail(final RemindDetail rd) {
		if (null != rd && !TextUtils.isEmpty(rd.getId())) {
			HashMap<String, String> params = new HashMap<String, String>();
			LogUtils.e("更新 = " + rd.getId());
			params.put("json", "{\"classId\":\""+rd.getClassId()+
					"\",\"id\":\""+rd.getId()+
					"\",\"title\":\""+rd.getTitle()+
					"\",\"states\":\""+rd.getStates()+
					"\",\"repeat\":\""+rd.getRepeat()+
					"\",\"repeatEnd\":\""+rd.getRepeatEnd()+
					"\",\"reminderTime\":\""+rd.getReminderTime()+
					"\",\"content\":\""+rd.getContent()+"\"}");
			ru.updateReminderDetail(params, new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					
					try {
						LogUtils.e("update = " + arg0);
						
						RemindDetailDao rdd = new RemindDetailDao(getContext());
						rd.setIssubmit("true");
						rd.setSubmittype(RemindDetail.IDLE);
						rdd.updateData(rd, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});				
		}
	}

	public void update_remind_detail(RemindDetail rd, boolean isUsers) {
		client_update_remind_detail(rd, isUsers);
	}	
	/* 
	 * 
	 * 提醒事项详情相关--结束
	 * 
	 * 
	 * */		
	
	/* 
	 * 
	 * 行程相关--开始
	 * 
	 * 
	 * */	
	
	public String client_add_trip(Trip trip) {
		String mid = "";
		
		TripDao td = new TripDao(getContext());
		
		Trip _trip = td.addData(trip);
		
//		server_add_remind_detail(_rd);
		
		if (null != _trip) {
			mid = _trip.getMid();
		}
		
		return mid;
	}
	
	public void server_add_trip(final Trip trip) {
	}
	
	public String add_trip(Trip trip) {
		return client_add_trip(trip);
	}	
	
	
	public void client_update_trip(Trip trip) {
		trip.setIssubmit("false");
		if (!trip.getSubmittype().equals(Trip.ADD)) {
			trip.setSubmittype(Trip.UPDATE);
		}

		TripDao td = new TripDao(getContext());
		td.updateData(trip);
	}

	public void server_update_trip(final RemindDetail rd) {
	}

	public void update_trip(Trip trip) {
		client_update_trip(trip);
	}	
	
	public String client_delete_trip(Trip trip) {
		String id = "";
		TripDao td = new TripDao(getContext());
		if (trip.getSubmittype().equals(Trip.ADD)) {
			// 物理删除
			td.deleteData(trip.getMid());
		} else {
			trip.setIssubmit("false");
			trip.setSubmittype(Remind.DELETE);
			id = td.updateData(trip);
		}

		return id;
	}

	public void server_delete_trip(final Trip trip) {
	}

	public void delete_trip(Trip trip) {
		client_delete_trip(trip);
	}		
	/* 
	 * 
	 * 行程相关--结束
	 * 
	 * 
	 * */		
	private Handler creatHandler() {
		Handler handler = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case ActionType.SEND_MESSAGE:
					Messages messages = (Messages) msg.obj;
					String result = messages.getResult();
					switch (messages.id) {
					case 1:
						// 网络创建新备忘录成功
						Object[] objs = messages.getObjs();
						YzLog.e("yz", "新备忘录成功");
						// 上传录音文件
						List<NoteFile> nfiles = (List<NoteFile>) objs[1];
						if (nfiles.size() > 0) {
							YzLog.e("yz", "新上传录音文件");
							for (NoteFile nf : nfiles) {
								PostInfo taskinfo = new PostInfo(
										getContext(),
										ActionType.SEND_MESSAGE,
										handlerID,
										new Object[] { objs[0], nf.getMuris() },
										2);
								TaskCoreManager.getInstance().execute(
										new PostTask(taskinfo));
							}
						}
						// 更新本地记录
						ReminderDao rd = new ReminderDao(getContext());
						EventItem ei = (EventItem) objs[2];
						YzLog.e("yz", "更新的 id = " + String.valueOf(objs[0]));
						ei.setId(String.valueOf(objs[0]));
						ei.setIssubmit("true");
						ei.setSubmittype("idle");
						rd.updateData(ei);
						break;
					case 2:
						// 本地创建备忘录后
						Object[] objs2 = messages.getObjs();
						if (checkNet()) {
							server_add((EventItem) objs2[0]);
						}
						break;
					case 3:
						// 网络删除后
						// 更新本地记录
						Object[] objs3 = messages.getObjs();
						ReminderDao rd2 = new ReminderDao(getContext());
						rd2.deleteDataByID((String) objs3[0]);
						break;
					case 4:
						// 网络更新后
						// 更新本地记录
						String id = messages.getResult();
						ReminderDao rd3 = new ReminderDao(getContext());
						EventItem ei2 = rd3.queryOneById(id);
						ei2.setIssubmit("true");
						ei2.setSubmittype(EventItem.IDLE);
						rd3.updateData(ei2);
						break;
					case 5:
						// 查询所有不同后
						Object[] objs4 = messages.getObjs();
						HashMap<String, EventItem> map = (HashMap<String, EventItem>) objs4[0];
						Set<Entry<String, EventItem>> set = map.entrySet();
						Iterator<Entry<String, EventItem>> it = set.iterator();
						while (it.hasNext()) {
							Entry<String, EventItem> entry = it.next();
							EventItem ei3 = entry.getValue();
							String submittype = ei3.getSubmittype();
							if (submittype.equals(EventItem.ADD)) {
								server_add(ei3);
							}
							if (submittype.equals(EventItem.DELETE)) {
								server_delete(ei3.getId());
							}
							if (submittype.equals(EventItem.UPDATE)) {
								server_update(ei3);
							}
						}
						break;
					}
					break;
				case ActionType.RECEIVER_MESSAGE:
					break;
				}
				return false;
			}
		});
		return handler;
	}	
	
	public void synchro() {
		YzLog.e("yz", "执行异步方法");
		GetAllDiffInfo taskinfo = new GetAllDiffInfo(getContext(),
				ActionType.SEND_MESSAGE, handlerID);
		TaskCoreManager.getInstance().execute(new GetAllDiffTask(taskinfo));
		
		RemindListDao rld = new RemindListDao(getContext());
		ArrayList<Remind> reminds = rld.queryAllDiff();
		
		for (Remind rd : reminds) {
			String submittype = rd.getSubmittype();
			if (submittype.equals(Remind.ADD)) {
				server_add_remind(rd, null);
			}
			if (submittype.equals(Remind.DELETE)) {
				server_delete_remind(rd.getId(), null);
			}
			if (submittype.equals(Remind.UPDATE)) {
				server_update_remind(rd, null);
			}
		}
		
		RemindDetailDao rdd = new RemindDetailDao(getContext());
		ArrayList<RemindDetail> remindDetails = rdd.queryAllDiff();
		for (RemindDetail rd : remindDetails) {
			String submittype = rd.getSubmittype();
			if (submittype.equals(Remind.ADD)) {
				server_add_remind_detail(rd);
			}
			if (submittype.equals(Remind.DELETE)) {
				server_delete_remind_detail(rd);
			}
			if (submittype.equals(Remind.UPDATE)) {
				server_update_remind_detail(rd);
			}
		}
	}

	public boolean checkNet() {
		ConnectivityManager cm = (ConnectivityManager) getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Context getContext() {
		return ScApplication.getInstance().getApplicationContext();
	}

	/**
	 * 获取当前时间距离指定日期时差的大致表达形式
	 * 
	 * @param long date 日期
	 * @return 时差的大致表达形式
	 * */
	public static String getDiffTime(long date) {
		String strTime = "很久很久以前";
		long time = Math.abs(new Date().getTime() - date);
		// 一分钟以内
		if (time < TIME_NUMBERS * TIME_MILLISECONDS) {
			strTime = "刚刚";
		} else {
			int min = (int) (time / TIME_MILLISECONDS / TIME_NUMBERS);
			if (min < TIME_NUMBERS) {
				if (min < 15) {
					strTime = "一刻钟前";
				} else if (min < 30) {
					strTime = "半小时前";
				} else {
					strTime = "1小时前";
				}
			} else {
				int hh = min / TIME_NUMBERS;
				if (hh < TIME_HOURSES) {
					strTime = hh + "小时前";
				} else {
					int days = hh / TIME_HOURSES;
					if (days <= 6) {
						strTime = days + "天前";
					} else {
						int weeks = days / 7;
						if (weeks < 3) {
							strTime = weeks + "周前";
						}
					}
				}
			}
		}

		return strTime;
	}

	public interface AfterProcess {
		void onDone();
	}
}
