package com.sc.reminder.common.base;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sc.reminder.common.configure.ContentType;

public class TaskLoaderExecutor {
	private ThreadPoolExecutor executorService;
	private int maxSize = ContentType.TaskContetType.MAX_SIZE;
	private int maxTaskNum = ContentType.TaskContetType.MAX_TASK_NUM;

	public TaskLoaderExecutor() {
		initExecutors();
	}

	@SuppressWarnings("rawtypes")
	public void execute(BaseLogicTask worker) {
		initExecutors();
		try {
			executorService.submit(worker);
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void shutdownNow() {
		if (executorService != null && !executorService.isShutdown()) {
			List<Runnable> runList = executorService.shutdownNow();
		}
	}

	private void initExecutors() {
		if (executorService == null || executorService.isShutdown()) {
			executorService = new ThreadPoolExecutor(0, maxSize, 60L,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
							maxTaskNum));
		}
	}
}
