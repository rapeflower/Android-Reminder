package com.sc.reminder.task.net;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.model.memo.Note;
import com.sc.reminder.utils.YzLog;
import com.sc.reminder.utils.net.RequestUtil;

/**
 * 删除网络备忘录
 * @author hua
 *
 */
public class DeleteInfo extends BaseTaskInfo<Messages>{

	Object[] objs;
	
	public DeleteInfo(Context context, int actionType, String handlerID, Object[] objs) {
		super(context, actionType, handlerID);
		this.objs = objs;
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		Messages m = new Messages(3);
		String id = (String) objs[0];
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("json", "{'id':'"+id+"'}");
		RequestUtil ru = new RequestUtil();
		String result = ru.deleteReminder(params);
		YzLog.e("yz", "服务器已删除 id = " + result);
		m.setObjs(new Object[]{id});
		return m;
	}

}
