package com.sc.reminder.task.home;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.database.dao.ReminderDao;
import com.sc.reminder.model.memo.EventItem;

public class GetAllDiffInfo extends BaseTaskInfo<Messages>{

	public GetAllDiffInfo(Context context, int actionType, String handlerID) {
		super(context, actionType, handlerID);
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		ReminderDao rd = new ReminderDao(context);
		HashMap<String, EventItem> map = rd.queryAllDiff();
		Messages m = new Messages(5);
		m.setObjs(new Object[]{map});
		return m;
	}

}
