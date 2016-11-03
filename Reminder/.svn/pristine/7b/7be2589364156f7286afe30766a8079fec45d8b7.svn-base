package com.sc.reminder.database.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sc.reminder.database.db.EntryDbHelper;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.NoteFile;
import com.sc.reminder.model.remind.Remind;
import com.sc.reminder.utils.YzLog;


public class RemindListDao {
	
	Context context;
	EntryDbHelper dbHelper;
	
	public static final String QUERY_LIST = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_LIST + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +Remind.DELETE + "'";	
	
	public static final String QUERY_ID_ALL_DIFF = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_LIST + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +Remind.IDLE+ "'";
	
	public static final String WHERE_MID = EntryDbHelper.MID+" = ?";
	
	public static final String WHERE_ID = EntryDbHelper.ID+" = ?";
	
	public static final String QUERY_ID_NUM = "SELECT COUNT(*) FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_LIST + " WHERE "+EntryDbHelper.ID+" = ?";
	
	public static final String QUERY_ROW = "select * from "+EntryDbHelper.TABLE_NAME_REMIND_LIST+" where rowid = ?";
	
	public RemindListDao(Context context) {
		this.context = context;
		dbHelper = new EntryDbHelper(context);
	}
	
	/**
	 * 获取提醒事项列表
	 * @param _nid
	 * @return
	 */
	public ArrayList<Remind> queryAll() {
		ArrayList<Remind> reminds = new ArrayList<Remind>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_LIST, null);
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String accountId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ACCOUNTID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					Remind remind = new Remind();
					remind.setMid(String.valueOf(mid));
					remind.setId(id);
					remind.setAccountId(accountId);
					remind.setTitle(title);
					remind.setIssubmit(issubmit);
					remind.setSubmittype(submittype);
					reminds.add(remind);
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
		
		return reminds;		
	}	
	
	/**
	 * 查询所有提醒事项列表不同
	 * @param _nid
	 * @return
	 */
	public ArrayList<Remind> queryAllDiff() {
		YzLog.e("yz", "queryRemindListDiff");
		ArrayList<Remind> reminds = new ArrayList<Remind>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ALL_DIFF, null);
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String accountId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ACCOUNTID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					Remind remind = new Remind();
					remind.setMid(String.valueOf(mid));
					remind.setId(id);
					remind.setAccountId(accountId);
					remind.setTitle(title);
					remind.setIssubmit(issubmit);
					remind.setSubmittype(submittype);
					reminds.add(remind);
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
		
		return reminds;		
	}	
	
	/**
	 * 检查是否有ID
	 * @param number
	 * @return
	 */
	public boolean hasID(Remind rm) {
		long count = 0;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_NUM, new String[] {rm.getId()});
				if (cursor.moveToFirst()) {
					count = cursor.getLong(0);
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
		return count > 0;
	}	
	
	/**
	 * 检查没有的ID集合
	 * @param ids
	 * @return
	 */
	public List<Remind> queryHasNoIDs(List<Remind> reminds) {
		List<Remind> _reminds = new ArrayList<Remind>();
		for (Remind rm : reminds) {
			if (!hasID(rm)) {
				rm.setIssubmit("true");
				rm.setSubmittype(Remind.IDLE);
				_reminds.add(rm);
			}
		}
		return _reminds;
	}		
	
	
	/**
	 * 多提醒添加
	 * @param data
	 */
	public void addDatas(List<Remind> reminds) {
		for (Remind rm : reminds) {
			addData(rm);
		}
	}	
	
	/**
	 * 单提醒添加
	 * @param data
	 */
	public Remind addData(Remind rm) {
		Remind _rm = null;
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, rm.getId());
				cv.put(EntryDbHelper.ACCOUNTID, rm.getAccountId());
				cv.put(EntryDbHelper.TITLE, rm.getTitle());
				cv.put(EntryDbHelper.ISSUBMIT, rm.getIssubmit());
				LogUtils.e("add = " + rm.getSubmittype());
				cv.put(EntryDbHelper.SUBMITTYPE, rm.getSubmittype());
				long row = db.insert(EntryDbHelper.TABLE_NAME_REMIND_LIST, null, cv);
				db.setTransactionSuccessful();
				db.endTransaction();
				if (row != -1) {
					_rm = queryRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		
		return _rm;
	}	

	/**
	 * 按行号查询
	 * @param number
	 * @return
	 */
	public Remind queryRow(long row) {
		Remind rm = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ROW, new String[] {String.valueOf(row)});
				YzLog.e("yz", "this cursor = " + cursor.getCount());
				if (cursor.moveToFirst()) {
					rm = new Remind();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String accountId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ACCOUNTID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					rm.setMid(String.valueOf(mid));
					rm.setId(id);
					rm.setAccountId(accountId);
					rm.setTitle(title);
					rm.setIssubmit(issubmit);
					rm.setSubmittype(submittype);
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
		
		return rm;
	}		
	
	/**
	 * 删除 by mid
	 * @param data
	 */
	public void deleteData(String mid) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMIND_LIST, WHERE_MID, new String[]{mid});
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
	 * 删除 by id
	 * @param data
	 */
	public void deleteDataByID(String id) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMIND_LIST, WHERE_ID, new String[]{id});
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
	 * 更新备忘录
	 * @param data
	 */
	public String updateData(Remind rm) {
		YzLog.e("yz", "updateData = " + rm.getTitle() + ",mid = " + rm.getMid());
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.MID, rm.getMid());
				cv.put(EntryDbHelper.ID, rm.getId());
				cv.put(EntryDbHelper.ACCOUNTID, rm.getAccountId());
				cv.put(EntryDbHelper.TITLE, rm.getTitle());
				cv.put(EntryDbHelper.ISSUBMIT, rm.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, rm.getSubmittype());
				db.update(EntryDbHelper.TABLE_NAME_REMIND_LIST, cv, WHERE_MID, new String[]{rm.getMid()});
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
		return rm.getId();
	}	
}
