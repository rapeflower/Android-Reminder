package com.sc.reminder.task.net;

import com.sc.reminder.common.base.BaseLogicTask;
import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;

public class UpdateTask extends BaseLogicTask<Messages>{

	public UpdateTask(BaseTaskInfo<Messages> taskInfo) {
		super(taskInfo);
	}

	@Override
	protected Messages execute() {
		return task.request();
	}

}
