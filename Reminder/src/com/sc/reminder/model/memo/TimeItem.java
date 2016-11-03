package com.sc.reminder.model.memo;

import java.util.List;

public class TimeItem {
	/**
	 * 显示时间
	 */
	String time;
	/**
	 * item列表
	 */
	List<String> items;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	
}
