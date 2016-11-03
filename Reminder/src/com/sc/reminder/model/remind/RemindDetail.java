package com.sc.reminder.model.remind;

import java.util.List;

public class RemindDetail {

	public static String IDLE = "idle";
	public static String ADD = "add";
	public static String UPDATE = "update";
	public static String DELETE = "delete";

	/**
	 * 本地ID
	 */
	String mid;
	/**
	 * 服务器ID
	 */
	String id;
	/**
	 * 分类ID
	 */
	String classId;
	/**
	 * 本地分类ID
	 */
	String mclassId;
	/**
	 * 用户ID
	 */
	String createId;
	/**
	 * 提醒时间
	 */
	String reminderTime;
	/**
	 * 标题
	 */
	String title;
	/**
	 * 重复
	 */
	String repeat;
	/**
	 * 结束重复
	 */
	String repeatEnd;
	/**
	 * 来自于谁创建的(用户Id)
	 */
	String sourceId;
	/**
	 * 操作状态,0-灰色,1-红色 2-黄色 3-绿色
	 */
	String states;
	/**
	 * 备注
	 */
	String content;
	/**
	 * 删除状态,0-未删除
	 */
	String isdel;
	/**
	 * 创建时间
	 */
	String createTime;
	/**
	 * 修改时间
	 */
	String updatetime;
	/**
	 * 用户集
	 */
	List<Users> uers;
	/**
	 * 是否同步
	 */
	String issubmit;
	/**
	 * 同步类型
	 */
	String submittype;


	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getRepeatEnd() {
		return repeatEnd;
	}

	public void setRepeatEnd(String repeatEnd) {
		this.repeatEnd = repeatEnd;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public List<Users> getUers() {
		return uers;
	}

	public void setUers(List<Users> uers) {
		this.uers = uers;
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

	public String getMclassId() {
		return mclassId;
	}

	public void setMclassId(String mclassId) {
		this.mclassId = mclassId;
	}

}
