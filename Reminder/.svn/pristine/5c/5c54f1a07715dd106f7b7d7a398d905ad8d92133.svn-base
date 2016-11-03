package com.sc.reminder.task.net;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.model.memo.Note;
import com.sc.reminder.utils.YzLog;
import com.sc.reminder.utils.net.RequestUtil;

/**
 * 创建网络备忘录
 * 
 * @author hua
 * 
 */
public class PostInfo extends BaseTaskInfo<Messages> {

	Object[] objs;
	int type;

	public PostInfo(Context context, int actionType, String handlerID,
			Object[] objs, int type) {
		super(context, actionType, handlerID);
		this.objs = objs;
		this.type = type;
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		Messages m = null;
		try {
			if (type == 1) {
				// 创建备忘录
				Note note = (Note) objs[0];

				HashMap<String, String> params = new HashMap<String, String>();
				params.put("json", JSON.toJSONString(note));
				RequestUtil ru = new RequestUtil();
				String result = ru.postReminder(params);

				Log.e("yz", "caonima result =  " + result);
				JSONObject jo = JSON.parseObject(result);

				// result =
				// "{'timeList':[{'time':'2012年7月','items':'1,2,3'},{'time':'2012年8月','items':'4,5,6'},{'time':'2012年9月','items':'7,8,9'},{'time':'2012年10月','items':'10,11,12'},{'time':'2012年11月','items':'13,14,15'},{'time':'2012年12月','items':'16,17,18'}]}";
				// result = "[{'id':'1'},{'id':'2'},{'id':'3'},{'id':'4'}]";
				m = new Messages(1);
				m.setObjs(new Object[] { jo.get("id"), objs[1], objs[2] });
			} else {
				// 创建备忘录文件
				RequestUtil ru = new RequestUtil();
				String result = ru.postReminderFile((String) objs[0],
						(String) objs[1]);
				YzLog.e("yz", "cao result = " + result);
				m = new Messages(0);
				m.setResult(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m = new Messages(999);
			return m;
		}
		return m;
	}

}
