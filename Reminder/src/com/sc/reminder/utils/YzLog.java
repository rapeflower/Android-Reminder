package com.sc.reminder.utils;

public class YzLog {

	public static boolean IS_SHOW_LOG = true;

	public interface LogInterface {

		void d(String logTag, String logText);

		void e(String logTag, String logText);

		void w(String logTag, String logText);

		void v(String logTag, String logText);

		void i(String logTag, String logText);

		void print(String logText);

	}

	private static LogInterface instance;

	public static LogInterface getInstance() {
		if (instance == null)
			instance = newDefaultAndroidLog();
		return instance;
	}

	public static void setInstance(LogInterface instance) {
		YzLog.instance = instance;
	}

	private static LogInterface newDefaultAndroidLog() {
		return new LogInterface() {

			// @Override
			public void w(String logTag, String logText) {
				android.util.Log.w(logTag, logText);
			}

			// @Override
			public void v(String logTag, String logText) {
				android.util.Log.v(logTag, logText);
			}

			// @Override
			public void i(String logTag, String logText) {
				android.util.Log.i(logTag, logText);
			}

			// @Override
			public void e(String logTag, String logText) {
				android.util.Log.e(logTag, logText);
			}

			// @Override
			public void d(String logTag, String logText) {
				android.util.Log.d(logTag, logText);
			}

			// @Override
			public void print(String logText) {
				android.util.Log.d("Debug Output", logText);
			}
		};
	}

	public static void d(String logTag, String logText) {
		if (IS_SHOW_LOG) {
			getInstance().d(logTag, logText);
		}
	}

	public static void e(String logTag, String logText) {
		if (IS_SHOW_LOG) {
			getInstance().e(logTag, logText);
		}

	}

	public static void w(String logTag, String logText) {
		if (IS_SHOW_LOG) {
			getInstance().w(logTag, logText);
		}

	}

	public static void v(String logTag, String logText) {
		if (IS_SHOW_LOG) {
			getInstance().v(logTag, logText);
		}

	}

	public static void i(String logTag, String logText) {
		if (IS_SHOW_LOG) {
			getInstance().i(logTag, logText);
		}

	}

	public static void out(String logText) {
		if (IS_SHOW_LOG) {
			getInstance().print(logText);
		}
	}
}
