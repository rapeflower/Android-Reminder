package com.sc.reminder.common.base;

import android.content.Context;

import com.sc.reminder.common.configure.Packet;
import com.sc.reminder.common.configure.RequestAction;

public abstract class BaseTaskInfo<T> implements RequestAction {
	public Context context;
	private int actionType;
	private String handlerID;

	public BaseTaskInfo(Context context, int actionType, String handlerID) {
		this.context = context;
		this.actionType = actionType;
		this.handlerID = handlerID;
	}

	public abstract T request();

	@Override
	public int getActionType() {
		return actionType;
	}

	@Override
	public String getHandlerID() {
		return handlerID;
	}

	@Override
	public String getRequestBody() {
		return null;
	}

	@Override
	public Object processHttpResponse(String data) {
		return null;
	}

	@Override
	public Object processXmppResponse(Packet packet) {
		return null;
	}

}
