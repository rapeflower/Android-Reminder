package com.sc.reminder.model.memo;

public class NoteFile {
	/**
	 * 本地文件ID
	 */
	String mid;
	/**
	 * 服务器文件ID
	 */
	String id;
	/**
	 * 依赖备忘录ID
	 */
	String rid;
	/**
	 * 本地文件地址
	 */
	String muris;
	/**
	 * 服务器文件地址
	 */
	String uris;
	/**
	 * 时间
	 */
	String time;

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

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getMuris() {
		return muris;
	}

	public void setMuris(String muris) {
		this.muris = muris;
	}

	public String getUris() {
		return uris;
	}

	public void setUris(String uris) {
		this.uris = uris;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
