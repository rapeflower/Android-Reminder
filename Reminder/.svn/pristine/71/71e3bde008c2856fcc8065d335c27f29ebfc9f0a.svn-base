//package com.sc.reminder.database.dao;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.provider.ContactsContract;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.sc.myphonefirewall.database.db.EntryDbHelper;
//import com.sc.myphonefirewall.model.firewall.BlackListNum;
//import com.sc.myphonefirewall.model.firewall.InterceptNum;
//
//public class TelephonymanagerDao {
//	EntryDbHelper dbHelper;
//	Context context;
//
//	public static final String INSERT_NUM = "INSERT INTO "
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER
//			+ " ("+EntryDbHelper.NUMBER+", "+EntryDbHelper.INTERCEPTPHONE+", "+EntryDbHelper.INTERCEPTSMS+", "+EntryDbHelper.NAME+") values(?,?,?,?) ";
//	public static final String UPDATE_NUM = "UPDATE"
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER
//			+ " SET "+EntryDbHelper.INTERCEPTPHONE+" = ?, "+EntryDbHelper.INTERCEPTSMS+" = ?"
//			+ " WHERE "+EntryDbHelper.NUMBER+" = ?" ;
//	public static final String QUERY_NUM = "SELECT COUNT(*) FROM "
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER + " WHERE "+EntryDbHelper.NUMBER+" = ?";
//	public static final String QUERY_NUM_ALL = "SELECT * FROM "
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER;
//	public static final String QUERY_NUM_ONE = "SELECT * FROM "
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER + " WHERE "+EntryDbHelper.NUMBER+" = ?";
//	public static final String DELETE_NUM = "DELETE FROM "
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER + " WHERE "+EntryDbHelper.NUMBER+" = ?";
//	public static final String DELETE_NUM_ALL = "DELETE FROM "
//			+ EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER;
//
//	public TelephonymanagerDao(Context context) {
//		this.context = context;
//		dbHelper = new EntryDbHelper(context);
//	}
//
//	/**
//	 * ��Ӻ������
//	 * 
//	 * @param number
//	 * @param isPhone
//	 * @param isSms
//	 */
//	public void addNum(String number, String isPhone, String isSms, String name) {
//		
//		if (hasNum(number)) {
//			Log.e("yz", "�ظ���Ӻ��룡");
//			return;
//		}
//		
//		SQLiteDatabase db = null;
//		try {
//			db = dbHelper.getWritableDatabase();
//
//			if (db.isOpen()) {
//				db.beginTransaction();
//				db.execSQL(INSERT_NUM, new Object[] {number, isPhone, isSms, name});
////				ContentValues values = new ContentValues(); 
////				values.put(EntryDbHelper.NUMBER, number); 
////				values.put(EntryDbHelper.INTERCEPTPHONE, isPhone); 
////				values.put(EntryDbHelper.INTERCEPTSMS, isSms); 
//////				values.put(EntryDbHelper.NAME, name); 
////				long r = db.insert(EntryDbHelper.TABLE_NAME_TELEPHONYMANAGER, "nilc", values);
////				Log.e("yz", "r = " + r);
//				db.setTransactionSuccessful();
//				db.endTransaction();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != db) {
//				db.close();
//			}
//		}
//	}
//
//	/**
//	 * ��ѯ�Ƿ��д˺���
//	 * 
//	 * @param number
//	 * @return
//	 */
//	public boolean hasNum(String number) {
//		long count = 0;
//		SQLiteDatabase db = null;
//		Cursor cursor = null;
//		try {
//			db = dbHelper.getReadableDatabase();
//			if (db.isOpen()) {
//				cursor = db.rawQuery(QUERY_NUM, new String[] {number});
//				if (cursor.moveToFirst()) {
//					count = cursor.getLong(0);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != cursor) {
//				cursor.close();
//			}
//			if (null != db) {
//				db.close();
//			}
//		}
//		return count > 0;
//	}
//
//	/**
//	 * ��ѯ����
//	 * 
//	 * @param number
//	 */
//	public InterceptNum queryNum(String number) {
//		InterceptNum it = null;
//		SQLiteDatabase db = null;
//		Cursor cursor = null;
//		try {
//			db = dbHelper.getReadableDatabase();
//			if (db.isOpen()) {
//				cursor = db.rawQuery(QUERY_NUM_ONE, new String[] {number});
//				if (cursor.moveToFirst()) {
//					it = new InterceptNum();
//					
//					int id = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.ID));
//					String num = cursor.getString(cursor.getColumnIndex(EntryDbHelper.NUMBER));
//					String name = cursor.getString(cursor.getColumnIndex(EntryDbHelper.NAME));
//					String inter_phone = cursor.getString(cursor.getColumnIndex(EntryDbHelper.INTERCEPTPHONE));
//					String inter_sms = cursor.getString(cursor.getColumnIndex(EntryDbHelper.INTERCEPTSMS));
//					
////					Log.e("yz", "id = " + id);
////					Log.e("yz", "num" + num);
////					Log.e("yz", "inter_phone = " + inter_phone);
////					Log.e("yz", "inter_sms = " + inter_sms);					
//					
//					it.setId(id);
//					it.setName(name);
//					it.setNumber(num);
//					it.setInterceptphone(inter_phone);
//					it.setInterceptsms(inter_sms);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != cursor) {
//				cursor.close();
//			}
//			if (null != db) {
//				db.close();
//			}
//		}
//		
//		return it;
//	}
//	
//	public InterceptNum isInterceptSms(String number) {
//		InterceptNum it = queryNum(number);
//		if (null != it && it.getInterceptsms().equals("true")) {
//			return it;
//		}
//		else {
//			return null;
//		}
//	}
//	
//	public InterceptNum isInterceptPhone(String number) {
//		InterceptNum it = queryNum(number);
//		if (null != it && it.getInterceptphone().equals("true")) {
//			return it;
//		}
//		else {
//			return null;
//		}
//	}
//
//	/**
//	 * ɾ�����
//	 * 
//	 * @param number
//	 */
//	public void deleteNum(String number) {
//		SQLiteDatabase db = null;
//		try {
//			db = dbHelper.getReadableDatabase();
//			if (db.isOpen()) {
//				db.beginTransaction();
//				db.execSQL(DELETE_NUM, new String[] {number});
//				db.setTransactionSuccessful();
//				db.endTransaction();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != db) {
//				db.close();
//			}
//		}
//	}
//	
//	/**
//	 * ɾ�����к���
//	 * 
//	 * @param number
//	 */
//	public void deleteNumAll() {
//		SQLiteDatabase db = null;
//		try {
//			db = dbHelper.getReadableDatabase();
//			if (db.isOpen()) {
//				db.beginTransaction();
//				db.execSQL(DELETE_NUM_ALL);
//				db.setTransactionSuccessful();
//				db.endTransaction();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != db) {
//				db.close();
//			}
//		}
//	}
//	
//	/**
//	 * ��ѯ���к���
//	 * @return
//	 */
//	public Map<String, InterceptNum> queryAllNum() {
//		Map<String, InterceptNum> map = new HashMap<String, InterceptNum>();
//		SQLiteDatabase db = null;
//		Cursor cursor = null;
//		try {
//			db = dbHelper.getReadableDatabase();
//			if (db.isOpen()) {
//				cursor = db.rawQuery(QUERY_NUM_ALL, null);
//				while (cursor.moveToNext()) {
//					int id = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.ID));
//					String num = cursor.getString(cursor.getColumnIndex(EntryDbHelper.NUMBER));
//					String inter_phone = cursor.getString(cursor.getColumnIndex(EntryDbHelper.INTERCEPTPHONE));
//					String inter_sms = cursor.getString(cursor.getColumnIndex(EntryDbHelper.INTERCEPTSMS));
//					String name = cursor.getString(cursor.getColumnIndex(EntryDbHelper.NAME));
//					
//					InterceptNum it = new InterceptNum();
//					it.setId(id);
//					it.setNumber(num);
//					it.setInterceptphone(inter_phone);
//					it.setInterceptsms(inter_sms);
//					String theName = getName(context, num);
////					if (!TextUtils.isEmpty(theName)) {
////						it.setName(theName);
////					}
////					else {
////						it.setName(name);
////					}
//					if (TextUtils.isEmpty(theName)) {
//						it.setIscontacts(false);
//						it.setName(name);
//					}
//					else {
//						it.setIscontacts(true);
//						it.setName(theName);
//					}					
//					map.put(num, it);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != cursor) {
//				cursor.close();
//			}
//			if (null != db) {
//				db.close();
//			}
//		}
//		
//		return map;
//	}
//	
//	/**
//	 * ���º���״̬
//	 * @param number
//	 */
//	public void updateNum(String number, String isPhone, String isSms) {
//		SQLiteDatabase db = null;
//		try {
//			db = dbHelper.getWritableDatabase();
//
//			if (db.isOpen()) {
//				db.beginTransaction();
//				db.execSQL(UPDATE_NUM, new Object[] {isPhone, isSms, number});
//				db.setTransactionSuccessful();
//				db.endTransaction();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != db) {
//				db.close();
//			}
//		}		
//	}
//	
//	public String getName(Context context, String incomingNumber) {
//		Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+incomingNumber);
//		ContentResolver  resolver = context.getContentResolver();
//		Cursor cursor = resolver.query(uri, 
//				new String[]{ContactsContract.Contacts.DISPLAY_NAME},
//				null,
//				null, 
//				null);
//		
//		if(cursor.moveToFirst()){
//			String phonename =	cursor.getString(0);
//			cursor.close();
//			return phonename;
//		}
//		return "";
//	}	
//}
