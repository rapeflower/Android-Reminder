package com.sc.reminder.utils.net;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.sc.reminder.utils.net.Request.RequestMethod;

public class RequestUtil {
	
	HttpConnector conn;
	public static final String LOCALIP = "192.168.1.199";
	public static final String SERVERIP = "121.199.63.16";
	public static final String IP = "http://121.199.63.16:8080/portal/";
	
	/**
	 * 获取所有备忘录ID
	 * @param params
	 * @return
	 */
	public String getAllID(HashMap<String, String> params) {
		conn = new HttpConnector(false);
		Request request = new Request();
		request.setRequest_method(RequestMethod.POST);
		request.setUrl(IP + "getNoteIds.do");
		request.setParams(params);
		return conn.execute(request);
	}

	/**
	 * 获取单个备忘录
	 * @param params
	 * @return
	 */
	public String getReminderByID(HashMap<String, String> params) {
		conn = new HttpConnector(false);
		Request request = new Request();
		request.setRequest_method(RequestMethod.POST);
		request.setUrl(IP + "downloadNoteById.do");
		request.setParams(params);
		return conn.execute(request);
	}
	
	/**
	 * 新增备忘录
	 * @param params
	 * @return
	 */
	public String postReminder(HashMap<String, String> params) {
		conn = new HttpConnector(false);
		Request request = new Request();
		request.setRequest_method(RequestMethod.POST);
		request.setUrl(IP + "createNote.do");
		request.setParams(params);
		return conn.execute(request);
	}
	
	/**
	 * 新增备忘录文件
	 * @param params
	 * @return
	 */
	public String postReminderFile(String id, String filePath) {
		conn = new HttpConnector(false);
		return conn.execute(IP + "createNoteFileById.do", id, filePath);
	}
	
	/**
	 * 下载备忘录文件
	 * @param params
	 * @return
	 */
	public File getReminderFile(String path) {
		conn = new HttpConnector(false);
		return conn.execute(path);
	}
	
	/**
	 * 删除备忘录
	 * @param params
	 * @return
	 */
	public String deleteReminder(HashMap<String, String> params) {
		conn = new HttpConnector(false);
		Request request = new Request();
		request.setRequest_method(RequestMethod.POST);
		request.setUrl(IP + "delNoteById.do");
		request.setParams(params);
		return conn.execute(request);
	}
	
	/**
	 * 更新备忘录
	 * @param params
	 * @return
	 */
	public String updateReminder(HashMap<String, String> params) {
		conn = new HttpConnector(false);
		Request request = new Request();
		request.setRequest_method(RequestMethod.POST);
		request.setUrl(IP + "updateNoteById.do");
		request.setParams(params);
		return conn.execute(request);
	}
	
	/**
	 * 更新备忘录文件
	 * @param params
	 * @return
	 */
	public String updateReminderFile(String id, String filePath) {
		conn = new HttpConnector(false);
		return conn.execute(IP + "updateNoteFileById.do", id, filePath);
	}
	
	/**
	 * 新建提醒事项列表
	 * @param params
	 * @param listener
	 */
	public void newRemindList(HashMap<String, String> params, Listener<String> listener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "createReminderDirectory.do", params, listener, null);
	}	
	
	/**
	 * 获取提醒事项列表
	 * @param params
	 * @param listener
	 */
	public void getRemindList(HashMap<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "getReminderDirectory.do", params, listener, errorListener);
	}	
	
	/**
	 * 更新提醒事项列表
	 * @param params
	 * @param listener
	 */
	public void updateRemindList(HashMap<String, String> params, Listener<String> listener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "updateDirectoryName.do", params, listener, null);
	}	
	
	/**
	 * 删除提醒事项列表
	 * @param params
	 * @param listener
	 */
	public void deleteRemindList(HashMap<String, String> params, Listener<String> listener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "deleteReminder.do", params, listener, null);
	}	
	
	/**
	 * 获取提醒事项列表详情
	 * @param params
	 * @param listener
	 */
	public void getRemindListDetail(HashMap<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "getReminderList.do", params, listener, errorListener);
	}	
	
	/**
	 * 新建提醒事项详情
	 * @param params
	 * @param listener
	 */
	public void newRemindDetail(HashMap<String, String> params, Listener<String> listener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "createReminderDetail.do", params, listener, null);
	}
	
	/**
	 * 删除提醒事项详情
	 * @param params
	 * @param listener
	 */
	public void deleteReminderDetail(HashMap<String, String> params, Listener<String> listener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "delReminderDetail.do", params, listener, null);
	}
	
	/**
	 * 更新提醒事项详情
	 * @param params
	 * @param listener
	 */
	public void updateReminderDetail(HashMap<String, String> params, Listener<String> listener) {
		conn = new HttpConnector(false);
		conn.execute_volley(IP + "updateReminderDetail.do", params, listener, null);
	}	
	
	/**
	 * 待删
	 * @param params
	 * @return
	 */
	public String postReminderList(HashMap<String, String> params) {
		conn = new HttpConnector(false);
		Request request = new Request();
		request.setRequest_method(RequestMethod.POST);
		request.setUrl(IP + "createReminderDirectory.do");
		request.setParams(params);
		return conn.execute(request);
	}
}
