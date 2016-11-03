package com.sc.reminder.common.base;

import android.os.Message;
import android.text.TextUtils;

public abstract class BaseLogicTask<T> implements Runnable {
	protected BaseTaskInfo<T> task;

	public BaseLogicTask(BaseTaskInfo<T> taskInfo) {
		this.task = taskInfo;
	}

	@Override
	public void run() {
		if (task == null) {
			return;
		}
		T obj = execute();
		notifyCaller(obj);
	}
	
	abstract protected T execute();

	protected void notifyCaller(T obj) {
		Message msg = new Message();
		msg.obj = obj;
		msg.what = task.getActionType();
		// msg.what = ResourceDownloader.DOWNLOAD_FINISHED;
		// NotificationManager.getInstance().notifyById(task.getHandlerID(),
		// msg);
		notifyCaller(msg);
	}

	protected void notifyCaller(Message msg) {
		String handlerID = task.getHandlerID();
		if (!TextUtils.isEmpty(handlerID)) {
			NotificationManager.getInstance().notifyById(handlerID, msg);
		} else {
			NotificationManager.getInstance().notifyAllHandler(msg);
		}

	}

}
