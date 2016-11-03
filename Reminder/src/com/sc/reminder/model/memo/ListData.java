package com.sc.reminder.model.memo;

public class ListData {
	/**
	 * 显示方式： 
	 * 1 -- 表格
	 * 2 -- 列表
	 */
	int type;
	/**
	 * 绑定数据
	 */
	Object[] objs;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

}
