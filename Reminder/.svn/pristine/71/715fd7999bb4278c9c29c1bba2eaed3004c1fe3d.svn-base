package com.sc.reminder.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UIUtil {

	private static UIUtil flowUtil = null;
	private final Context context;

	private UIUtil(Context context) {
		this.context = context;
	}

	public static UIUtil getInstance(Context context) {
		if (flowUtil == null) {
			flowUtil = new UIUtil(context);
		}
		return flowUtil;
	}

	public int getScreenW() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	public int getScreenH() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.heightPixels;
	}

	public int[] getScreenWH() {
		int[] wh = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		wh[0] = dm.widthPixels;
		wh[1] = dm.heightPixels;
		return wh;
	}

	/**
	 * 获取屏幕尺寸
	 * 
	 * @param context
	 * @return
	 */

	public static int[] getScreenSize(Context context) {
		int[] wh = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		int[] size = { width, height };
		return size;
	}

	// /**
	// * 获取屏幕尺寸
	// * @param context
	// * @return
	// */
	// public static int[] getScreenSize(Context context){
	// WindowManager wm = (WindowManager) context.getSystemService(
	// Context.WINDOW_SERVICE);
	// int width = wm.getDefaultDisplay().getWidth();
	// int height = wm.getDefaultDisplay().getHeight();
	// int[] size = {width,height};
	//
	// return size;
	// }

	// 转换dip为px
	public int convertDIP2PX(int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	// 转换px为dip
	public int convertPX2DIP(int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		// YzLog.d("scale", scale+"");
		// YzLog.d("px", (px/scale + 0.5f*(px>=0?1:-1))+"");
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * scrollview嵌套listview时调用此方法，listview的item外层必须是LinearLayout
	 * 
	 * @param listView
	 */
	public void measureListView(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
