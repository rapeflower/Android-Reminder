package com.sc.reminder.model.memo;

import java.util.List;

public class NoteWithID {
	/**
	 * 服务器ID
	 */
	String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
