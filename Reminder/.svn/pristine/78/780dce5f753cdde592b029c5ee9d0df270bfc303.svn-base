package com.sc.reminder.database.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sc.reminder.database.db.EntryDbHelper;
import com.sc.reminder.model.remind.Remind;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.remind.Users;
import com.sc.reminder.utils.YzLog;


public class RemindUserDao {
	
	Context context;
	EntryDbHelper dbHelper;
	
	public static final String QUERY_LIST_ID= "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_USERS + " WHERE " + EntryDbHelper.MCLASSID + " = ?  and " + EntryDbHelper.MREMINDERID + " = ?";	
	
	public static final String WHERE_ID = EntryDbHelper.MCLASSID+" = ?  and " + EntryDbHelper.MREMINDERID + " = ?";
	
	public static final String WHERE_CID = EntryDbHelper.CID+" = ?";
	
	public static final String QUERY_ROW = "select * from "+EntryDbHelper.TABLE_NAME_REMIND_USERS+" where rowid = ?";
	
	public RemindUserDao(Context context) {
		this.context = context;
		dbHelper = new EntryDbHelper(context);
	}
	
	/**
	 * 获取用户列表
	 * @param _nid
	 * @return
	 */
	public ArrayList<Users> queryAllById(String _mclassid, String _mreminderid) {
		ArrayList<Users> users = new ArrayList<Users>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_LIST_ID, new String[] {_mclassid, _mreminderid});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					Users user = new Users();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String cid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CID));
					String mclassid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MCLASSID));
					String reminderid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDERID));
					String mreminderid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MREMINDERID));
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String aname = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ANAME));
					String reson = cursor.getString(cursor.getColumnIndex(EntryDbHelper.RESON));
					String phone = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PHONE));
					String email = cursor.getString(cursor.getColumnIndex(EntryDbHelper.EMAIL));
					String name = cursor.getString(cursor.getColumnIndex(EntryDbHelper.NAME));
					String status = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STATUS));
					
					user.setMid(String.valueOf(mid));
					user.setCid(cid);
					user.setMclassId(mclassid);
					user.setReminderId(reminderid);
					user.setMreminderId(mreminderid);
					user.setAid(aid);
					user.setAname(aname);
					user.setReson(reson);
					user.setPhone(phone);
					user.setEmail(email);
					user.setName(name);
					user.setStatus(status);
					
					users.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != db) {
				db.close();
			}
		}
		
		return users;		
	}	
	
	/**
	 * 多用户添加
	 * @param data
	 */
	public void addDatas(List<Users> users) {
		for (Users user : users) {
			addData(user);
		}
	}	
	
	/**
	 * 单用户添加
	 * @param data
	 */
	public Users addData(Users user) {
		Users _user = null;
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.CID, user.getCid());
				cv.put(EntryDbHelper.MCLASSID, user.getMclassId());
				cv.put(EntryDbHelper.REMINDERID, user.getReminderId());
				cv.put(EntryDbHelper.MREMINDERID, user.getMreminderId());
				cv.put(EntryDbHelper.AID, user.getAid());
				cv.put(EntryDbHelper.ANAME, user.getAname());
				cv.put(EntryDbHelper.RESON, user.getReson());
				cv.put(EntryDbHelper.PHONE, user.getPhone());
				cv.put(EntryDbHelper.EMAIL, user.getEmail());
				cv.put(EntryDbHelper.NAME, user.getName());
				cv.put(EntryDbHelper.STATUS, user.getStatus());
				long row = db.insert(EntryDbHelper.TABLE_NAME_REMIND_USERS, null, cv);
				db.setTransactionSuccessful();
				db.endTransaction();
				if (row != -1) {
					_user = queryRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		
		return _user;
	}		
	
	/**
	 * 按行号查询
	 * @param number
	 * @return
	 */
	public Users queryRow(long row) {
		Users user = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ROW, new String[] {String.valueOf(row)});
				YzLog.e("yz", "this cursor = " + cursor.getCount());
				if (cursor.moveToFirst()) {
					user = new Users();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String cid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CID));
					String mclassid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MCLASSID));
					String reminderid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDERID));
					String mreminderid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MREMINDERID));
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String aname = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ANAME));
					String reson = cursor.getString(cursor.getColumnIndex(EntryDbHelper.RESON));
					String phone = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PHONE));
					String email = cursor.getString(cursor.getColumnIndex(EntryDbHelper.EMAIL));
					String name = cursor.getString(cursor.getColumnIndex(EntryDbHelper.NAME));
					String status = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STATUS));
					
					user.setMid(String.valueOf(mid));
					user.setCid(cid);
					user.setMclassId(mclassid);
					user.setReminderId(reminderid);
					user.setMreminderId(mreminderid);
					user.setAid(aid);
					user.setAname(aname);
					user.setReson(reson);
					user.setPhone(phone);
					user.setEmail(email);
					user.setName(name);
					user.setStatus(status);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != db) {
				db.close();
			}
		}
		
		return user;
	}			
	
	/**
	 * 删除 by id
	 * @param data
	 */
	public void deleteData(String _mclassid, String _mreminderid) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMIND_USERS, WHERE_ID, new String[]{_mclassid, _mreminderid});
				db.setTransactionSuccessful();
				db.endTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}		

	/**
	 * 清空by classId
	 * @param classId
	 */
	public void clearDataByCid(String classId) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMIND_USERS, WHERE_CID, new String[]{classId});
				db.setTransactionSuccessful();
				db.endTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}	
	}	
}

