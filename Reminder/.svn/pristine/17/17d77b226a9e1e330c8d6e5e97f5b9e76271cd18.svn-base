package com.sc.reminder.task.net;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.model.memo.Note;
import com.sc.reminder.model.memo.NoteWithID;
import com.sc.reminder.utils.YzLog;
import com.sc.reminder.utils.net.RequestUtil;

/**
 * 更新网络备忘录
 * @author hua
 *
 */
public class UpdateInfo extends BaseTaskInfo<Messages>{

	Object[] objs;
	
	public UpdateInfo(Context context, int actionType, String handlerID, Object[] objs) {
		super(context, actionType, handlerID);
		this.objs = objs;
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		Messages m = new Messages(4);
		NoteWithID nwi = (NoteWithID) objs[0];
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("json", JSON.toJSONString(nwi));
		RequestUtil ru = new RequestUtil();
		String result = ru.updateReminder(params);
		YzLog.e("yz", "服务器已更新 id = " + nwi.getId());
		m.setResult(nwi.getId());
		return m;
	}

}
