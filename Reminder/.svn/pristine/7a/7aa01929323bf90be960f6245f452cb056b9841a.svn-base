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
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.utils.YzLog;


public class RemindDetailDao {
	
	Context context;
	EntryDbHelper dbHelper;
	
	public static final String QUERY_ROW = "select * from "+EntryDbHelper.TABLE_NAME_REMIND_DETAIL+" where rowid = ?";
	
	public static final String QUERY_ID_ALL_DIFF = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_DETAIL + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +Remind.IDLE+ "'";
	
	public static final String QUERY_ID_ONE = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_DETAIL + " WHERE " + EntryDbHelper.MID+" = ?";	
	
	public static final String QUERY_LIST_CID= "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_DETAIL + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +Remind.DELETE + "' and " + EntryDbHelper.MCLASSID + " = ?";	
	
	public static final String QUERY_ID_NUM = "SELECT COUNT(*) FROM "
			+ EntryDbHelper.TABLE_NAME_REMIND_DETAIL + " WHERE "+EntryDbHelper.ID+" = ?";
	
	public static final String WHERE_MID = EntryDbHelper.MID+" = ?";
	
	public static final String WHERE_CLASSID = EntryDbHelper.CLASSID+" = ?";
	
	public RemindDetailDao(Context context) {
		this.context = context;
		dbHelper = new EntryDbHelper(context);
	}
	
	/**
	 * 获取提醒事项列表
	 * @param _nid
	 * @return
	 */
	public ArrayList<RemindDetail> queryAllByCid(String _mclassid) {
		ArrayList<RemindDetail> reminds = new ArrayList<RemindDetail>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_LIST_CID, new String[] {_mclassid});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					RemindDetail rd = new RemindDetail();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String classid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CLASSID));
					String mclassid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MCLASSID));
					String createid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindertime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDERTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String repeat = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEAT));
					String repeatend = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEATEND));
					String sourceid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOURCEID));
					String states = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STATES));
					String isdel = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISDEL));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String updatetime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.UPDATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					rd.setMid(String.valueOf(mid));
					rd.setId(id);
					rd.setClassId(classid);
					rd.setMclassId(mclassid);
					rd.setCreateId(createid);
					rd.setReminderTime(remindertime);
					rd.setTitle(title);
					rd.setContent(content);
					rd.setRepeat(repeat);
					rd.setRepeatEnd(repeatend);
					rd.setSourceId(sourceid);
					rd.setStates(states);
					rd.setIsdel(isdel);
					rd.setCreateTime(createtime);
					rd.setUpdatetime(updatetime);
					rd.setIssubmit(issubmit);
					rd.setSubmittype(submittype);
					
					RemindUserDao rud = new RemindUserDao(context);
					rd.setUers(rud.queryAllById(_mclassid, String.valueOf(mid)));
					reminds.add(rd);
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
	 * 查询提醒事项列表所有不同
	 * @param _nid
	 * @return
	 */
	public ArrayList<RemindDetail> queryAllDiff() {
		YzLog.e("yz", "queryRemindDetailDiff");
		ArrayList<RemindDetail> reminds = new ArrayList<RemindDetail>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ALL_DIFF, null);
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					RemindDetail rd = new RemindDetail();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String classid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CLASSID));
					String mclassid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MCLASSID));
					String createid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindertime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDERTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String repeat = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEAT));
					String repeatend = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEATEND));
					String sourceid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOURCEID));
					String states = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STATES));
					String isdel = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISDEL));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String updatetime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.UPDATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					rd.setMid(String.valueOf(mid));
					rd.setId(id);
					rd.setClassId(classid);
					rd.setMclassId(mclassid);
					rd.setCreateId(createid);
					rd.setReminderTime(remindertime);
					rd.setTitle(title);
					rd.setContent(content);
					rd.setRepeat(repeat);
					rd.setRepeatEnd(repeatend);
					rd.setSourceId(sourceid);
					rd.setStates(states);
					rd.setIsdel(isdel);
					rd.setCreateTime(createtime);
					rd.setUpdatetime(updatetime);
					rd.setIssubmit(issubmit);
					rd.setSubmittype(submittype);
					
					RemindUserDao rud = new RemindUserDao(context);
					rd.setUers(rud.queryAllById(mclassid, String.valueOf(mid)));
					reminds.add(rd);
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
	 * 查询 by mid
	 * @param _mid
	 * @return
	 */
	public RemindDetail queryOne(String _mid) {
		RemindDetail rd = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ONE, new String[] {_mid});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				if (cursor.moveToNext()) {
					rd = new RemindDetail();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String classid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CLASSID));
					String mclassid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MCLASSID));
					String createid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindertime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDERTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String repeat = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEAT));
					String repeatend = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEATEND));
					String sourceid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOURCEID));
					String states = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STATES));
					String isdel = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISDEL));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String updatetime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.UPDATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					rd.setMid(String.valueOf(mid));
					rd.setId(id);
					rd.setClassId(classid);
					rd.setMclassId(mclassid);
					rd.setCreateId(createid);
					rd.setReminderTime(remindertime);
					rd.setTitle(title);
					rd.setContent(content);
					rd.setRepeat(repeat);
					rd.setRepeatEnd(repeatend);
					rd.setSourceId(sourceid);
					rd.setStates(states);
					rd.setIsdel(isdel);
					rd.setCreateTime(createtime);
					rd.setUpdatetime(updatetime);
					rd.setIssubmit(issubmit);
					rd.setSubmittype(submittype);
					
					RemindUserDao rud = new RemindUserDao(context);
					rd.setUers(rud.queryAllById(mclassid, String.valueOf(mid)));
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
		
		return rd;		
	}	
	
	/**
	 * 检查没有的ID集合
	 * @param ids
	 * @return
	 */
	public List<RemindDetail> queryHasNoIDs(List<RemindDetail> rds, String mid, String id) {
		List<RemindDetail> _reminds = new ArrayList<RemindDetail>();
		for (RemindDetail rm : rds) {
			if (!hasID(rm)) {
//				rm.setStates("2");
				rm.setClassId(id);
				rm.setMclassId(mid);
				rm.setIssubmit("true");
				rm.setSubmittype(Remind.IDLE);
				_reminds.add(rm);
			}
		}
		return _reminds;
	}			
	
	
	/**
	 * 检查是否有ID
	 * @param number
	 * @return
	 */
	public boolean hasID(RemindDetail rd) {
		long count = 0;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_NUM, new String[] {rd.getId()});
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
	 * 多提醒添加
	 * @param data
	 */
	public void addDatas(List<RemindDetail> reminds) {
		for (RemindDetail rd : reminds) {
			addData(rd);
		}
	}	
	
	/**
	 * 单提醒添加
	 * @param data
	 */
	public RemindDetail addData(RemindDetail rd) {
		RemindDetail _rd = null;
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, rd.getId());
				cv.put(EntryDbHelper.CLASSID, rd.getClassId());
				cv.put(EntryDbHelper.MCLASSID, rd.getMclassId());
				cv.put(EntryDbHelper.CREATEID, rd.getCreateId());
				cv.put(EntryDbHelper.REMINDERTIME, rd.getReminderTime());
				cv.put(EntryDbHelper.TITLE, rd.getTitle());
				cv.put(EntryDbHelper.CONTENT, rd.getContent());
				cv.put(EntryDbHelper.REPEAT, rd.getRepeat());
				cv.put(EntryDbHelper.REPEATEND, rd.getRepeatEnd());
				cv.put(EntryDbHelper.SOURCEID, rd.getSourceId());
				cv.put(EntryDbHelper.STATES, rd.getStates());
				cv.put(EntryDbHelper.ISDEL, rd.getIsdel());
				cv.put(EntryDbHelper.CREATETIME, rd.getCreateTime());
				cv.put(EntryDbHelper.UPDATETIME, rd.getUpdatetime());
				cv.put(EntryDbHelper.ISSUBMIT, rd.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, rd.getSubmittype());
				long row = db.insert(EntryDbHelper.TABLE_NAME_REMIND_DETAIL, null, cv);
				db.setTransactionSuccessful();
				db.endTransaction();
				if (row != -1) {
					_rd = queryRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		
		return _rd;
	}	
	
	/**
	 * 按行号查询
	 * @param number
	 * @return
	 */
	public RemindDetail queryRow(long row) {
		RemindDetail rd = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ROW, new String[] {String.valueOf(row)});
				YzLog.e("yz", "this cursor = " + cursor.getCount());
				if (cursor.moveToFirst()) {
					rd = new RemindDetail();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String classid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CLASSID));
					String mclassid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MCLASSID));
					String createid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindertime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDERTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String repeat = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEAT));
					String repeatend = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REPEATEND));
					String sourceid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOURCEID));
					String states = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STATES));
					String isdel = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISDEL));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String updatetime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.UPDATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					rd.setMid(String.valueOf(mid));
					rd.setId(id);
					rd.setClassId(classid);
					rd.setMclassId(mclassid);
					rd.setCreateId(createid);
					rd.setReminderTime(remindertime);
					rd.setTitle(title);
					rd.setContent(content);
					rd.setRepeat(repeat);
					rd.setRepeatEnd(repeatend);
					rd.setSourceId(sourceid);
					rd.setStates(states);
					rd.setIsdel(isdel);
					rd.setCreateTime(createtime);
					rd.setUpdatetime(updatetime);
					rd.setIssubmit(issubmit);
					rd.setSubmittype(submittype);
					
					RemindUserDao rud = new RemindUserDao(context);
					rd.setUers(rud.queryAllById(mclassid, String.valueOf(mid)));
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
		
		return rd;
	}		
		
	/**
	 * 更新提醒
	 * @param data
	 */
	public String updateData(RemindDetail rd, boolean isUsers) {
		YzLog.e("yz", "updateData = " + rd.getTitle() + ",mid = " + rd.getMid());
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, rd.getId());
				cv.put(EntryDbHelper.CLASSID, rd.getClassId());
				cv.put(EntryDbHelper.MCLASSID, rd.getMclassId());
				cv.put(EntryDbHelper.CREATEID, rd.getCreateId());
				cv.put(EntryDbHelper.REMINDERTIME, rd.getReminderTime());
				cv.put(EntryDbHelper.TITLE, rd.getTitle());
				cv.put(EntryDbHelper.CONTENT, rd.getContent());
				cv.put(EntryDbHelper.REPEAT, rd.getRepeat());
				cv.put(EntryDbHelper.REPEATEND, rd.getRepeatEnd());
				cv.put(EntryDbHelper.SOURCEID, rd.getSourceId());
				cv.put(EntryDbHelper.STATES, rd.getStates());
				cv.put(EntryDbHelper.ISDEL, rd.getIsdel());
				cv.put(EntryDbHelper.CREATETIME, rd.getCreateTime());
				cv.put(EntryDbHelper.UPDATETIME, rd.getUpdatetime());
				cv.put(EntryDbHelper.ISSUBMIT, rd.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, rd.getSubmittype());
				db.update(EntryDbHelper.TABLE_NAME_REMIND_DETAIL, cv, WHERE_MID, new String[]{rd.getMid()});
				db.setTransactionSuccessful();
				db.endTransaction();
				
				if (isUsers) {
					RemindUserDao rud = new RemindUserDao(context);
					rud.addDatas(rd.getUers());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
//		queryAll();
		return rd.getId();
	}	
	
	/**
	 * 删除 by mid
	 * @param data
	 */
	public void deleteData(String mid) {
		RemindDetail rd = queryOne(mid);
		RemindUserDao rud = new RemindUserDao(context);
		rud.deleteData(rd.getMclassId(), rd.getMid());
		
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMIND_DETAIL, WHERE_MID, new String[]{mid});
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
		RemindUserDao rud = new RemindUserDao(context);
		rud.clearDataByCid(classId);
		
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMIND_DETAIL, WHERE_CLASSID, new String[]{classId});
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
