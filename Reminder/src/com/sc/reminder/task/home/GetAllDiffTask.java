package com.sc.reminder.task.home;

import com.sc.reminder.common.base.BaseLogicTask;
import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;

public class GetAllDiffTask extends BaseLogicTask<Messages>{
	public GetAllDiffTask(BaseTaskInfo<Messages> taskInfo) {
		super(taskInfo);
	}

	@Override
	protected Messages execute() {
		Messages message = task.request();
		return message;
	}
	
}
