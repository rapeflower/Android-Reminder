package com.sc.reminder.task.home;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.database.dao.ReminderDao;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.TimeItem;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.YzLog;

public class EventInfo extends BaseTaskInfo<Messages>{

	boolean isRefresh;
	
	public EventInfo(Context context, int actionType, String handlerID, boolean isRefresh) {
		super(context, actionType, handlerID);
		this.isRefresh = isRefresh;
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		
		ReminderDao rd = new ReminderDao(context);
		HashMap<String, EventItem> items = rd.queryAll();
		//测试数据
//		Date date = new Date();
//		EventItem ei1 = new EventItem();
//		ei1.setMid("1");
//		ei1.setId("1");
//		ei1.setContent("ei1_content");
//		ei1.setCreatetime(String.valueOf(System.currentTimeMillis()));
//		ei1.setIssubmit("true");
//		ei1.setSound("false");
//		ei1.setStar("true");
//		ei1.setTitle("ei1_title1");
//		ei1.setUris("ei1_uris");
//		items.put(ei1.getMid(), ei1);
		Messages m = null;
		if (isRefresh) {
			m = new Messages(3);
		}
		else {
			m = new Messages(2);
		}
//		String result = "{'id':'1','title':'test','content':'ahah','star':'false','sound':'true','createtime':'2013-12-12 00:00:00'}";
//		m.setResult(result);
//		Object[] objs = {CommonUtil.convert(items), items};
		//设置全局数据
		try {
			HashMap<String, TimeItem>  map = CommonUtil.getInstance().convert(items);
			YzLog.e("yz", "map size = " + map.size());
			ScApplication.getInstance().setTimeItems(map);
			ScApplication.getInstance().setResultList(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		m.setObjs(objs);
		return m;
	}

}
