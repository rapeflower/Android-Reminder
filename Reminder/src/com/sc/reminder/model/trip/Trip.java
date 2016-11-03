package com.sc.reminder.model.trip;

import java.util.List;

import com.sc.reminder.model.remind.Users;

public class Trip {

	public static String IDLE = "idle";
	public static String ADD = "add";
	public static String UPDATE = "update";
	public static String DELETE = "delete";

	String mid;
	String id;
	String createId;
	String remindtype;
	String remindtime;
	String title;
	String content;
	String allday;
	String alldate;
	String privacy;
	String isopen;
	String islock;
	String types;
	String starttime;
	String endtime;
	List<Users> users;
	String issubmit;
	String submittype;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getRemindtype() {
		return remindtype;
	}

	public void setRemindtype(String remindtype) {
		this.remindtype = remindtype;
	}

	public String getRemindtime() {
		return remindtime;
	}

	public void setRemindtime(String remindtime) {
		this.remindtime = remindtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAllday() {
		return allday;
	}

	public void setAllday(String allday) {
		this.allday = allday;
	}

	public String getAlldate() {
		return alldate;
	}

	public void setAlldate(String alldate) {
		this.alldate = alldate;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public String getIslock() {
		return islock;
	}

	public void setIslock(String islock) {
		this.islock = islock;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getIssubmit() {
		return issubmit;
	}

	public void setIssubmit(String issubmit) {
		this.issubmit = issubmit;
	}

	public String getSubmittype() {
		return submittype;
	}

	public void setSubmittype(String submittype) {
		this.submittype = submittype;
	}

}
