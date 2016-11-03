package com.sc.reminder.common.base;

public class TaskCoreManager {
	public static final String TAG = "TaskLogic";

	private TaskLoaderExecutor loaderExecutor;

	private volatile static TaskCoreManager instance;

	public static TaskCoreManager getInstance() {
		if (instance == null) {
			synchronized (TaskCoreManager.class) {
				if (instance == null) {
					instance = new TaskCoreManager();
				}
			}
		}
		return instance;
	}

	public TaskCoreManager() {
		loaderExecutor = new TaskLoaderExecutor();
	}

	@SuppressWarnings("rawtypes")
	public void execute(BaseLogicTask task) {
		if (loaderExecutor != null) {
			loaderExecutor.execute(task);
		}
	}
}
