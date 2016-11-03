package com.sc.reminder.common.configure;

public interface RequestAction {
	public static long TIME_OUT = 10 * 1000;

	public int getActionType();

	public String getHandlerID();

	public String getRequestBody();

	public Object processHttpResponse(String data);

	public Object processXmppResponse(Packet packet);
}
