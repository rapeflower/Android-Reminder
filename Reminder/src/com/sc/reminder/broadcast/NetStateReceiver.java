package com.sc.reminder.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.YzLog;

public class NetStateReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("yz", "NetStateReceiver");
		boolean isNet = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		if (cm != null) {
	        NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        NetworkInfo activeInfo = cm.getActiveNetworkInfo();  
	        YzLog.e("yz", "当前网络状态 :" + "mobile:"+mobileInfo.isConnected()+"\n"+"wifi:"+wifiInfo.isConnected());
			
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if(info[i].getState() == NetworkInfo.State.CONNECTED) {
						isNet = true;
					}
				}
			}
		}
		
		if (isNet) {
			CommonUtil.getInstance().synchro();
		}
	}
}
