package com.sc.reminder.view.remind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.reminder.R;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.remind.Users;
import com.sc.reminder.ui.contacts.CharacterParser;
import com.sc.reminder.ui.contacts.ClearEditText;
import com.sc.reminder.ui.contacts.PinyinComparator;
import com.sc.reminder.ui.contacts.SideBar;
import com.sc.reminder.ui.contacts.SideBar.OnTouchingLetterChangedListener;
import com.sc.reminder.ui.contacts.SortAdapter;
import com.sc.reminder.ui.contacts.SortModel;
import com.sc.reminder.utils.ScApplication;

public class Contacts extends Activity implements OnClickListener{
	
	ImageButton btn_titlebar_left;
	
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	
	List<Users> users = new ArrayList<Users>();
	
	private CharacterParser characterParser;
	
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		initView();
		
		initData();
	}

	private void initView() {
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);
		btn_titlebar_left.setOnClickListener(this);
	}

	Handler handler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message arg0) {
			initViews();
			
			ScApplication.getInstance().dismissLoadingDialog();
			return false;
		}
	});
	
	private void initData() {
		
		users = ScApplication.getInstance().getUsers();
		
		if (users.size() != 0) {
			handler.sendEmptyMessage(1);
			return;
		}
		
		users.clear();
		ScApplication.getInstance().showLoadingDialog();
		
		try {

			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					Cursor cur = getContentResolver().query(
							ContactsContract.Contacts.CONTENT_URI,
							null,
							null,
							null,
							ContactsContract.Contacts.DISPLAY_NAME
									+ " COLLATE LOCALIZED ASC");

					// 循环遍历
					if (cur.moveToFirst()) {
						
						int idColumn = cur
								.getColumnIndex(ContactsContract.Contacts._ID);
						int displayNameColumn = cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
						do {
							Users user = new Users();
							
							// 获得联系人的ID号
							String contactId = "";
							contactId = cur.getString(idColumn);
							// 获得联系人姓名
							String disPlayName = "";
							disPlayName = cur
									.getString(displayNameColumn);
							// 查看该联系人有多少个电话号码。如果没有这返回值为0
							int phoneCount = cur.getInt(cur
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
							user.setAid(contactId);
							user.setName(disPlayName);
							user.setAname(disPlayName);
							LogUtils.i("username = " + disPlayName);
							String phoneNumber = "";
							if (phoneCount > 0) {
								// 获得联系人的电话号码
								Cursor phones = getContentResolver()
										.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
												null,
												ContactsContract.CommonDataKinds.Phone.CONTACT_ID
														+ " = " + contactId,
												null, null);
								if (phones.moveToFirst()) {
									do {
										// 遍历所有的电话号码
										phoneNumber = phones.getString(phones
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
										String phoneType = phones.getString(phones
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
										LogUtils.i("phoneNumber = "
												+ phoneNumber);
										LogUtils.i("phoneType = " + phoneType);
									} while (phones.moveToNext());
								}
								phones.close();
							}
							user.setPhone(phoneNumber);

							// 获取该联系人邮箱
							Cursor emails = getContentResolver()
									.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
											null,
											ContactsContract.CommonDataKinds.Phone.CONTACT_ID
													+ " = " + contactId, null,
											null);
							String emailValue = "";
							
							if (emails.moveToFirst()) {
								do {
									// 遍历所有的电话号码
									String emailType = emails.getString(emails
											.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
									emailValue = emails.getString(emails
											.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
									
									LogUtils.i("emailType = " + emailType);
									LogUtils.i("emailValue = " + emailValue);
								} while (emails.moveToNext());
							}

							user.setEmail(emailValue);
							
							emails.close();
							users.add(user);
							
							// // 获取该联系人IM
							// Cursor IMs = getContentResolver().query(
							// Data.CONTENT_URI,
							// new String[] { Data._ID, Im.PROTOCOL, Im.DATA },
							// Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE
							// + "='"
							// + Im.CONTENT_ITEM_TYPE + "'",
							// new String[] { contactId }, null);
							// if (IMs.moveToFirst()) {
							// do {
							// String protocol = IMs.getString(IMs
							// .getColumnIndex(Im.PROTOCOL));
							// String date = IMs
							// .getString(IMs.getColumnIndex(Im.DATA));
							// Log.i("protocol", protocol);
							// Log.i("date", date);
							// } while (IMs.moveToNext());
							// }
							//
							// // 获取该联系人地址
							// Cursor address = getContentResolver()
							// .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
							// null,
							// ContactsContract.CommonDataKinds.Phone.CONTACT_ID
							// + " = " + contactId, null, null);
							// if (address.moveToFirst()) {
							// do {
							// // 遍历所有的地址
							// String street = address
							// .getString(address
							// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
							// String city = address
							// .getString(address
							// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
							// String region = address
							// .getString(address
							// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
							// String postCode = address
							// .getString(address
							// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
							// String formatAddress = address
							// .getString(address
							// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
							// Log.i("street", street);
							// Log.i("city", city);
							// Log.i("region", region);
							// Log.i("postCode", postCode);
							// Log.i("formatAddress", formatAddress);
							// } while (address.moveToNext());
							// }
							//
							// // 获取该联系人组织
							// Cursor organizations =
							// getContentResolver().query(
							// Data.CONTENT_URI,
							// new String[] { Data._ID, Organization.COMPANY,
							// Organization.TITLE },
							// Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE
							// + "='"
							// + Organization.CONTENT_ITEM_TYPE + "'",
							// new String[] { contactId }, null);
							// if (organizations.moveToFirst()) {
							// do {
							// String company =
							// organizations.getString(organizations
							// .getColumnIndex(Organization.COMPANY));
							// String title =
							// organizations.getString(organizations
							// .getColumnIndex(Organization.TITLE));
							// Log.i("company", company);
							// Log.i("title", title);
							// } while (organizations.moveToNext());
							// }
							//
							// // 获取备注信息
							// Cursor notes = getContentResolver().query(
							// Data.CONTENT_URI,
							// new String[] { Data._ID, Note.NOTE },
							// Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE
							// + "='"
							// + Note.CONTENT_ITEM_TYPE + "'",
							// new String[] { contactId }, null);
							// if (notes.moveToFirst()) {
							// do {
							// String noteinfo = notes.getString(notes
							// .getColumnIndex(Note.NOTE));
							// Log.i("noteinfo", noteinfo);
							// } while (notes.moveToNext());
							// }
							//
							// // 获取nickname信息
							// Cursor nicknames = getContentResolver().query(
							// Data.CONTENT_URI,
							// new String[] { Data._ID, Nickname.NAME },
							// Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE
							// + "='"
							// + Nickname.CONTENT_ITEM_TYPE + "'",
							// new String[] { contactId }, null);
							// if (nicknames.moveToFirst()) {
							// do {
							// String nickname_ = nicknames.getString(nicknames
							// .getColumnIndex(Nickname.NAME));
							// Log.i("nickname_", nickname_);
							// } while (nicknames.moveToNext());
							// }

						} while (cur.moveToNext());

					}

					cur.close();
					
					handler.sendEmptyMessage(1);
				}
			});

			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void initViews() {
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				TextView title = (TextView) view.findViewById(R.id.title);
				Users user = (Users) title.getTag();
				Intent intent = new Intent();//mycode
				intent.putExtra("aid", user.getAid());
				setResult(1, intent);
				finish();
			}
		});
		
		users = filledData();
		Collections.sort(users, pinyinComparator);
		adapter = new SortAdapter(this, users);
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	private List<Users> filledData(){
		List<Users> filterusers = new ArrayList<Users>();
		
		for(int i=0; i<users.size(); i++){
			Users user = users.get(i);
			String pinyin = characterParser.getSelling(user.getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			if(sortString.matches("[A-Z]")){
				user.setSortLetters(sortString.toUpperCase());
			}else{
				user.setSortLetters("#");
			}
			
			filterusers.add(user);
		}
		return filterusers;
		
	}
	
	private void filterData(String filterStr){
		List<Users> filterusers = new ArrayList<Users>();
		if(TextUtils.isEmpty(filterStr)){
			filterusers = users;
		}else{
			filterusers.clear();
			for(Users user : users){
				String name = user.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterusers.add(user);
				}
			}
		}
		
		Collections.sort(filterusers, pinyinComparator);
		adapter.updateListView(filterusers);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_titlebar_left:
			finish();
			break;
	}		
	}
	
}
