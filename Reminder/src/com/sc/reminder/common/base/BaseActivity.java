package com.sc.reminder.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View.OnClickListener;

import com.sc.reminder.common.configure.NotifyListener;

public abstract class BaseActivity extends Activity implements Callback,
		NotifyListener, OnClickListener{
	private Handler handler;
	protected String handlerID;

	@Override
	public abstract boolean handleMessage(Message msg);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handlerID = NotificationManager.getInstance().registerNotice(
				getCurrentHandler());
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NotificationManager.getInstance().unregisterNotice(handler);
	}

	private Handler creatHandler() {
		if (handler == null) {
			handler = new Handler(this);
		}
		return handler;
	}

	@Override
	public Handler getCurrentHandler() {
		return creatHandler();
	}

}
