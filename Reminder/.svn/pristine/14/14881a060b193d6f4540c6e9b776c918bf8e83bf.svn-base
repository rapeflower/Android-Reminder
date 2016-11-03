package com.sc.reminder.view.remind;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.RemindDetailDao;
import com.sc.reminder.database.dao.RemindUserDao;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.remind.Users;
import com.sc.reminder.utils.ScApplication;

public class AssignList extends Activity implements OnClickListener {

	ImageButton btn_titlebar_left;
	ImageButton btn_titlebar_right;
	TextView tv_view_detail_none;
	LinearLayout ll_home_list_assignlist;
	
	RemindDetailDao rdd;
	RemindUserDao rud;
	
	String mid = "";
	RemindDetail rd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.assignlist_remind);
		
		rdd = new RemindDetailDao(this);
		rud = new RemindUserDao(this);
		
		mid = getIntent().getStringExtra("mid");
		
		initView();
		
		initData();
	}

	private void initData() {
		
		ll_home_list_assignlist.removeAllViews();
		
		rd = rdd.queryOne(mid);
		List<Users> users = rd.getUers();
		LogUtils.e("users size = " + users.size());
		if (users.size() > 0) {
			tv_view_detail_none.setVisibility(View.GONE);
			
			for (Users user : users) {
				RelativeLayout assignlist_item = (RelativeLayout) View.inflate(this, R.layout.assignlist_item, null);
				ImageView iv_view_assign_state = (ImageView) assignlist_item.findViewById(R.id.iv_view_assign_state);
				TextView tv_view_assign_name = (TextView) assignlist_item.findViewById(R.id.tv_view_assign_name);
				TextView tv_view_assign_reson = (TextView) assignlist_item.findViewById(R.id.tv_view_assign_reson);
				TextView tv_view_assign_reson_value = (TextView) assignlist_item.findViewById(R.id.tv_view_assign_reson_value);
				TextView tv_view_assign_p1 = (TextView) assignlist_item.findViewById(R.id.tv_view_assign_p1);
				TextView tv_view_assign_p2 = (TextView) assignlist_item.findViewById(R.id.tv_view_assign_p2);
				tv_view_assign_p2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						rud.deleteData(rd.getMclassId(), rd.getMid());
						
						initData();
					}
				});
				if (!TextUtils.isEmpty(user.getStatus())) {
					switch (Integer.parseInt(user.getStatus())) {
						case 0://拒绝
							iv_view_assign_state.setBackgroundResource(R.drawable.red);
							tv_view_assign_p1.setVisibility(View.VISIBLE);
							break;
						case 1://进行中
							iv_view_assign_state.setBackgroundResource(R.drawable.yellow);
							tv_view_assign_p2.setVisibility(View.VISIBLE);
							break;
						case 2://已完成
							iv_view_assign_state.setBackgroundResource(R.drawable.green);
							tv_view_assign_p1.setVisibility(View.VISIBLE);
							break;
					}
				}		
				
				tv_view_assign_name.setText(user.getName());
				if (!TextUtils.isEmpty(user.getReson())) {
					tv_view_assign_reson.setVisibility(View.VISIBLE);
					tv_view_assign_reson_value.setVisibility(View.VISIBLE);
					tv_view_assign_reson_value.setText(user.getReson());
				}
				
				ll_home_list_assignlist.addView(assignlist_item);
			}			
		}
		else {
			tv_view_detail_none.setVisibility(View.VISIBLE);
		}

	}	
	
	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
		btn_titlebar_right = (ImageButton) findViewById(R.id.btn_titlebar_right);
		btn_titlebar_right.setOnClickListener(this);
		tv_view_detail_none = (TextView) findViewById(R.id.tv_view_detail_none);
		ll_home_list_assignlist = (LinearLayout) findViewById(R.id.ll_home_list_assignlist);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_titlebar_left:
				finish();
				break;		
			case R.id.btn_titlebar_right:
				Intent intent2 = new Intent(AssignList.this, Contacts.class);
				startActivityForResult(intent2, 1);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == 1) {
			
			if (null != rd) {
				Users _user = new Users();
				String aid = data.getStringExtra("aid");
				List<Users> users = ScApplication.getInstance().getUsers();
				for (Users user : users) {
					if (user.getAid().equals(aid)) {
						_user = user;
						break;
					}
				}
				
				_user.setCid(rd.getClassId());
				_user.setMclassId(rd.getMclassId());
				_user.setReminderId(rd.getId());
				_user.setMreminderId(rd.getMid());
				_user.setStatus("1");
				_user.setReson("");
				
				List<Users> _users = new ArrayList<Users>();
				_users.add(_user);
				rd.setUers(_users);
				rdd.updateData(rd, true);
				
				initData();				
			}
		}
		
	}
}
