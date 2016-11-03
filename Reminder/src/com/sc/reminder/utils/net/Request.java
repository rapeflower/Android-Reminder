package com.sc.reminder.utils.net;

import java.util.HashMap;

public class Request {
	
	public enum RequestMethod {
		/**
		 * get请求
		 */
		GET,
		
		/**
		 * post请求
		 */
		POST
	}
	
	private boolean isDialog = false;
	
	private RequestMethod request_method = RequestMethod.POST;
	
	private String url;
	
	private HashMap<String, String> params;

	public RequestMethod getRequest_method() {
		return request_method;
	}

	public void setRequest_method(RequestMethod request_method) {
		this.request_method = request_method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public boolean isDialog() {
		return isDialog;
	}

	public void setDialog(boolean isDialog) {
		this.isDialog = isDialog;
	}
	
}
