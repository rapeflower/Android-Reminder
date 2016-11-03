package com.sc.reminder.task.home;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.base.NotificationManager;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.common.configure.ActionListener.ActionType;
import com.sc.reminder.database.dao.ReminderDao;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.utils.YzLog;

/**
 * 创建本地备忘录
 * @author hua
 *
 */
public class AddReminderInfo extends BaseTaskInfo<Messages>{
	
	Object[] objs;

	public AddReminderInfo(Context context, int actionType, String handlerID, Object[] objs) {
		super(context, actionType, handlerID);
		this.objs = objs;
	}

	@Override
	public Messages request() {
		Log.e("TestInfo", "request");
		
		ReminderDao rd = new ReminderDao(context);
		String mid = rd.addData((EventItem) objs[0]);
		YzLog.e("yz", "mid = " + mid);
		
		Messages m = new Messages(999);
		m.setResult(mid);
		
		Message message = new Message();
		message.what = ActionType.SEND_MESSAGE;
		Messages ms = new Messages(2);
		EventItem ei = rd.queryOne(mid);
		YzLog.e("yz", "ei的 = " + ei.getMid());
		ms.setObjs(new Object[]{ei});
		message.obj = ms;
		NotificationManager.getInstance().notifyById((String) objs[1], message);
		
		return m;
	}

}
