package com.sc.reminder.common.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import android.os.Handler;
import android.os.Message;

public class NotificationManager {
	private volatile static NotificationManager instance;

	private Map<Handler, String> notifyMap = Collections
			.synchronizedMap(new HashMap<Handler, String>());

	public NotificationManager() {

	}

	public static NotificationManager getInstance() {
		if (instance == null) {
			synchronized (NotificationManager.class) {
				if (instance == null) {
					instance = new NotificationManager();
				}
			}
		}
		return instance;
	}
	
	public String registerNotice(Handler handler) {
		if (handler == null) {
			return null;
		}
		if (!notifyMap.containsKey(handler)) {
			String key = UUID.randomUUID().toString();
			notifyMap.put(handler, key);
			return key;
		}
		return notifyMap.get(handler);
	}

	public void unregisterNotice(Handler handler) {
		notifyMap.remove(handler);
	}

	public void notifyAllHandler(final Message msg) {
		if (msg == null) {
			return;
		}
		// ��ǰʱ�̵�mapʵ����ͼ
		Set<Entry<Handler, String>> entries = notifyMap.entrySet();
		synchronized (notifyMap) {
			for (Entry<Handler, String> entry : entries) {
				Handler handler = entry.getKey();
				if (handler != null) {
					handler.sendMessage(msg);
				}
			}
		}
	}

	public void notifyById(final String key, final Message msg) {
		if (key == null || msg == null) {
			return;
		}
		Set<Entry<Handler, String>> entries = notifyMap.entrySet();
		synchronized (notifyMap) {
			for (Entry<Handler, String> entry : entries) {
				if (key.equals(entry.getValue())) {
					Handler handler = entry.getKey();
					if (handler != null) {
						handler.sendMessage(msg);
					}
				}
			}
		}
	}

	public void clearAll() {
		notifyMap.clear();
	}

}
