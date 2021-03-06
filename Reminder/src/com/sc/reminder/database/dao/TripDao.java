package com.sc.reminder.database.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sc.reminder.database.db.EntryDbHelper;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.NoteFile;
import com.sc.reminder.model.remind.Remind;
import com.sc.reminder.model.remind.RemindDetail;
import com.sc.reminder.model.trip.Trip;
import com.sc.reminder.utils.YzLog;


public class TripDao {
	
	Context context;
	EntryDbHelper dbHelper;
	
	public static final String QUERY_ALL = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_TRIP + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +Trip.DELETE+ "' ORDER BY " + EntryDbHelper.STARTTIME + " ASC";
	
	public static final String QUERY_ID_ONE = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_TRIP + " WHERE " + EntryDbHelper.MID+" = ?";	
	
	public static final String QUERY_ID_ONE_ID = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_TRIP + " WHERE " + EntryDbHelper.ID+" = ?";	
	
	public static final String QUERY_ID_NUM = "SELECT COUNT(*) FROM "
			+ EntryDbHelper.TABLE_NAME_TRIP + " WHERE "+EntryDbHelper.ID+" = ?";
	
	public static final String QUERY_ROW = "select * from "+EntryDbHelper.TABLE_NAME_TRIP+" where rowid = ?";
	
	public static final String WHERE_MID = EntryDbHelper.MID+" = ?";
	
	public static final String WHERE_ID = EntryDbHelper.ID+" = ?";
	
	public TripDao(Context context) {
		this.context = context;
		dbHelper = new EntryDbHelper(context);
	}
	
	/**
	 * 获取提醒事项列表
	 * @param _nid
	 * @return
	 */
	public ArrayList<Trip> queryAll() {
		ArrayList<Trip> trips = new ArrayList<Trip>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ALL, null);
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					Trip trip = new Trip();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String createId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindtype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTYPE));
					String remindtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String allday = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDAY));
					String alldate = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDATE));
					String privacy = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PRIVACY));
					String isopen = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISOPEN));
					String islock = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISLOCK));
					String types = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TYPES));
					String starttime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STARTTIME));
					String endtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ENDTIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					trip.setMid(String.valueOf(mid));
					trip.setId(id);
					trip.setCreateId(createId);
					trip.setRemindtype(remindtype);
					trip.setRemindtime(remindtime);
					trip.setTitle(title);
					trip.setContent(content);
					trip.setAllday(allday);
					trip.setAlldate(alldate);
					trip.setPrivacy(privacy);
					trip.setIsopen(isopen);
					trip.setIslock(islock);
					trip.setTypes(types);
					trip.setStarttime(starttime);
					trip.setEndtime(endtime);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					
					trips.add(trip);
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
		
		return trips;		
	}		
	
	public ArrayList<Trip> queryByAllDay(String s){
		
		ArrayList<Trip> trips = new ArrayList<Trip>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String sqlString = "SELECT * FROM "
				+ EntryDbHelper.TABLE_NAME_TRIP + " WHERE " + EntryDbHelper.ALLDAY + " like" + "'%" + s + "%'";
		db = dbHelper.getReadableDatabase();
		
		try {
			if(db.isOpen()){
				cursor = db.rawQuery(sqlString, null);
				
				while(cursor.moveToNext()){
					Trip trip = new Trip();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String createId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindtype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTYPE));
					String remindtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String allday = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDAY));
					String alldate = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDATE));
					String privacy = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PRIVACY));
					String isopen = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISOPEN));
					String islock = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISLOCK));
					String types = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TYPES));
					String starttime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STARTTIME));
					String endtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ENDTIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					trip.setMid(String.valueOf(mid));
					trip.setId(id);
					trip.setCreateId(createId);
					trip.setRemindtype(remindtype);
					trip.setRemindtime(remindtime);
					trip.setTitle(title);
					trip.setContent(content);
					trip.setAllday(allday);
					trip.setAlldate(alldate);
					trip.setPrivacy(privacy);
					trip.setIsopen(isopen);
					trip.setIslock(islock);
					trip.setTypes(types);
					trip.setStarttime(starttime);
					trip.setEndtime(endtime);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					
					trips.add(trip);
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
		return trips;
	}
	
	/**
	 * 根据事件的名称查询
	 * @return
	 */
	public ArrayList<Trip> queryByTitle(String s){
		ArrayList<Trip> trips = new ArrayList<Trip>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		String sqlString = "SELECT * FROM "
				+ EntryDbHelper.TABLE_NAME_TRIP + " WHERE " + EntryDbHelper.TITLE + " like" + "'%" + s + "%'";
		
		db = dbHelper.getReadableDatabase();
		try {
			if(db.isOpen()){
				cursor = db.rawQuery(sqlString, null);
				
				while(cursor.moveToNext()){
					Trip trip = new Trip();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String createId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindtype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTYPE));
					String remindtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String allday = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDAY));
					String alldate = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDATE));
					String privacy = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PRIVACY));
					String isopen = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISOPEN));
					String islock = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISLOCK));
					String types = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TYPES));
					String starttime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STARTTIME));
					String endtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ENDTIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					trip.setMid(String.valueOf(mid));
					trip.setId(id);
					trip.setCreateId(createId);
					trip.setRemindtype(remindtype);
					trip.setRemindtime(remindtime);
					trip.setTitle(title);
					trip.setContent(content);
					trip.setAllday(allday);
					trip.setAlldate(alldate);
					trip.setPrivacy(privacy);
					trip.setIsopen(isopen);
					trip.setIslock(islock);
					trip.setTypes(types);
					trip.setStarttime(starttime);
					trip.setEndtime(endtime);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					
					trips.add(trip);
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
		
		return trips;
	}
	
	/**
	 * 添加行程
	 * @param data
	 */
	public Trip addData(Trip trip) {
		long row = -1;
		String mid = "";
		Trip _trip = null;
		YzLog.e("yz", "addData = " + trip.getTitle());
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, trip.getId());
				cv.put(EntryDbHelper.CREATEID, trip.getCreateId());
				cv.put(EntryDbHelper.REMINDTYPE, trip.getRemindtype());
				cv.put(EntryDbHelper.REMINDTIME, trip.getRemindtime());
				cv.put(EntryDbHelper.TITLE, trip.getTitle());
				cv.put(EntryDbHelper.CONTENT, trip.getContent());
				cv.put(EntryDbHelper.ALLDAY, trip.getAllday());
				cv.put(EntryDbHelper.ALLDATE, trip.getAlldate());
				cv.put(EntryDbHelper.PRIVACY, trip.getPrivacy());
				cv.put(EntryDbHelper.ISOPEN, trip.getIsopen());
				cv.put(EntryDbHelper.ISLOCK, trip.getIslock());
				cv.put(EntryDbHelper.TYPES, trip.getTypes());
				cv.put(EntryDbHelper.STARTTIME, trip.getStarttime());
				cv.put(EntryDbHelper.ENDTIME, trip.getEndtime());
				cv.put(EntryDbHelper.ISSUBMIT, trip.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, trip.getSubmittype());
				row = db.insert(EntryDbHelper.TABLE_NAME_TRIP, null, cv);
				db.setTransactionSuccessful();
				db.endTransaction();
				if (row != -1) {
					_trip = queryRow(row);
					mid = _trip.getMid();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return _trip;
	}	
	
	
	/**
	 * 按行号查询
	 * @param number
	 * @return
	 */
	public Trip queryRow(long row) {
		Trip trip = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ROW, new String[] {String.valueOf(row)});//
				YzLog.e("yz", "this cursor = " + cursor.getCount());
				if (cursor.moveToFirst()) {
					trip = new Trip();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String createId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindtype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTYPE));
					String remindtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String allday = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDAY));
					String alldate = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDATE));
					String privacy = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PRIVACY));
					String isopen = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISOPEN));
					String islock = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISLOCK));
					String types = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TYPES));
					String starttime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STARTTIME));
					String endtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ENDTIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					trip.setMid(String.valueOf(mid));
					trip.setId(id);
					trip.setCreateId(createId);
					trip.setRemindtype(remindtype);
					trip.setRemindtime(remindtime);
					trip.setTitle(title);
					trip.setContent(content);
					trip.setAllday(allday);
					trip.setAlldate(alldate);
					trip.setPrivacy(privacy);
					trip.setIsopen(isopen);
					trip.setIslock(islock);
					trip.setTypes(types);
					trip.setStarttime(starttime);
					trip.setEndtime(endtime);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
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
		
		return trip;
	}	
	
	/**
	 * 查询 by mid
	 * @param _mid
	 * @return
	 */
	public Trip queryOne(String _mid) {
		Trip trip = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ONE, new String[] {_mid});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				if (cursor.moveToNext()) {
					trip = new Trip();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String createId = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATEID));
					String remindtype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTYPE));
					String remindtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.REMINDTIME));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String allday = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDAY));
					String alldate = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ALLDATE));
					String privacy = cursor.getString(cursor.getColumnIndex(EntryDbHelper.PRIVACY));
					String isopen = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISOPEN));
					String islock = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISLOCK));
					String types = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TYPES));
					String starttime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STARTTIME));
					String endtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ENDTIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					trip.setMid(String.valueOf(mid));
					trip.setId(id);
					trip.setCreateId(createId);
					trip.setRemindtype(remindtype);
					trip.setRemindtime(remindtime);
					trip.setTitle(title);
					trip.setContent(content);
					trip.setAllday(allday);
					trip.setAlldate(alldate);
					trip.setPrivacy(privacy);
					trip.setIsopen(isopen);
					trip.setIslock(islock);
					trip.setTypes(types);
					trip.setStarttime(starttime);
					trip.setEndtime(endtime);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
					trip.setIssubmit(issubmit);
					trip.setSubmittype(submittype);
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
		
		return trip;		
	}
	
	/**
	 * 更新行程
	 * @param data
	 */
	public String updateData(Trip trip) {
		YzLog.e("yz", "updateData = " + trip.getTitle() + ",mid = " + trip.getMid());
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.MID, trip.getMid());
				cv.put(EntryDbHelper.ID, trip.getId());
				cv.put(EntryDbHelper.CREATEID, trip.getCreateId());
				cv.put(EntryDbHelper.REMINDTYPE, trip.getRemindtype());
				cv.put(EntryDbHelper.REMINDTIME, trip.getRemindtime());
				cv.put(EntryDbHelper.TITLE, trip.getTitle());
				cv.put(EntryDbHelper.CONTENT, trip.getContent());
				cv.put(EntryDbHelper.ALLDAY, trip.getAllday());
				cv.put(EntryDbHelper.ALLDATE, trip.getAlldate());
				cv.put(EntryDbHelper.PRIVACY, trip.getPrivacy());
				cv.put(EntryDbHelper.ISOPEN, trip.getIsopen());
				cv.put(EntryDbHelper.ISLOCK, trip.getIslock());
				cv.put(EntryDbHelper.TYPES, trip.getTypes());
				cv.put(EntryDbHelper.STARTTIME, trip.getStarttime());
				cv.put(EntryDbHelper.ENDTIME, trip.getEndtime());
				cv.put(EntryDbHelper.ISSUBMIT, trip.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, trip.getSubmittype());
				db.update(EntryDbHelper.TABLE_NAME_TRIP, cv, WHERE_MID, new String[]{trip.getMid()});
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
		return trip.getId();
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
				int i = db.delete(EntryDbHelper.TABLE_NAME_TRIP, WHERE_MID, new String[]{mid});
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
				int i = db.delete(EntryDbHelper.TABLE_NAME_TRIP, WHERE_ID, new String[]{id});
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
