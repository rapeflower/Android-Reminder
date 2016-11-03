package com.sc.reminder.common.configure;

public class Messages {
	
	public int id;
	
	String result;
	
	Object[] objs;
	
	public Messages (int id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

}
