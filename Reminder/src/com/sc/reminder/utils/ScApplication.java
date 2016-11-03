package com.sc.reminder.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sc.reminder.R;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.TimeItem;
import com.sc.reminder.model.remind.Users;

public class ScApplication extends Application{
	private static ScApplication instance;
	
	//全局数据
	private HashMap<String, EventItem> resultList = new HashMap<String, EventItem>();
	private HashMap<String, TimeItem> timeItems = new HashMap<String, TimeItem>();
	private List<Users> users = new ArrayList<Users>();
	private String aid = "0";
	
	RequestQueue requestQueue;
	
	public static ScApplication getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		
		requestQueue = Volley.newRequestQueue(this);
	}
	
	Dialog dialog = null;
    public Dialog showLoadingDialog () {
        if (null == dialog) {
            dialog = new Dialog(this,R.style.style_dialog);
            dialog.setContentView(R.layout.dialog);
            dialog.setCancelable(true);
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);           
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }
  	
  	public void dismissLoadingDialog () {
  		if (null != dialog && dialog.isShowing()) {
  			dialog.dismiss();
  		}
  	}

  	public void showToast(String text) {
  		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
  	}
  	
	public boolean checkNet() {
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}  	
  	
	public HashMap<String, EventItem> getResultList() {
		return resultList;
	}

	public void setResultList(HashMap<String, EventItem> resultList) {
		this.resultList = resultList;
	}

	public HashMap<String, TimeItem> getTimeItems() {
		return timeItems;
	}

	public void setTimeItems(HashMap<String, TimeItem> timeItems) {
		this.timeItems = timeItems;
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}	
  	
}
