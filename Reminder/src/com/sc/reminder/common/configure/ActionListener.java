package com.sc.reminder.common.configure;

public interface ActionListener {

	public interface ActionType {
		public static final int SEND_MESSAGE = 1;

		public static final int RECEIVER_MESSAGE = 2;
	}

	public void onRunningFailed(ActionType type, String failReason);

	public void onRunningComplete(ActionType type, String successTips);
}
