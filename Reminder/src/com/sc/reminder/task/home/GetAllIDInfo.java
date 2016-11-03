package com.sc.reminder.task.home;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.YzLog;
import com.sc.reminder.utils.net.RequestUtil;

public class GetAllIDInfo extends BaseTaskInfo<Messages>{

	public GetAllIDInfo(Context context, int actionType, String handlerID) {
		super(context, actionType, handlerID);
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		/**
		 * 1.检查是否有没提交的
		 * 2.同步数据
		 * 3.显示数据
		 */
		
		CommonUtil.getInstance().synchro();
		
		HashMap<String, String> params = new HashMap<String, String>();
		RequestUtil ru = new RequestUtil();
		String result = ru.getAllID(params);
		YzLog.e("yz", "getAllID result = " + result);
//		result = "{'timeList':[{'time':'2012年7月','items':'1,2,3'},{'time':'2012年8月','items':'4,5,6'},{'time':'2012年9月','items':'7,8,9'},{'time':'2012年10月','items':'10,11,12'},{'time':'2012年11月','items':'13,14,15'},{'time':'2012年12月','items':'16,17,18'}]}";
//		result = "[{'id':'1'}]";
		Messages m = new Messages(2);
		m.setResult(result);
		return m;
	}

}
