package com.sc.reminder.model.memo;

import java.util.List;

public class EventItem {
	
	public static String IDLE = "idle";
	public static String ADD = "add";
	public static String UPDATE = "update";
	public static String DELETE = "delete";
	
	/**
	 * 本地MID
	 */
	String mid;
	/**
	 * 服务器ID
	 */
	String id;
	/**
	 * 用户ID
	 */
	String aid;
	/**
	 * 备忘录标题
	 */
	String title;
	/**
	 * 内容
	 */
	String content;
	/**
	 * 是否是星标
	 */
	String star;
	/**
	 * 是否有录音
	 */
	String sound;
	/**
	 * 创建时间
	 */
	String createtime;
	/**
	 * 录音文件
	 */
	List<NoteFile> noteFiles;
	/**
	 * 是否同步
	 */
	String issubmit;
	/**
	 * 同步类型
	 */
	String submittype;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getIssubmit() {
		return issubmit;
	}

	public void setIssubmit(String issubmit) {
		this.issubmit = issubmit;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSubmittype() {
		return submittype;
	}

	public void setSubmittype(String submittype) {
		this.submittype = submittype;
	}

	public List<NoteFile> getNoteFiles() {
		return noteFiles;
	}

	public void setNoteFiles(List<NoteFile> noteFiles) {
		this.noteFiles = noteFiles;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	
}
