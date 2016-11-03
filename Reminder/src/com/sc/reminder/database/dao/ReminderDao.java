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
import com.sc.reminder.utils.YzLog;


public class ReminderDao {
	
	Context context;
	EntryDbHelper dbHelper;
	
	public static final String INSERT_DATA = "INSERT INTO "
			+ EntryDbHelper.TABLE_NAME_REMINDER
			+ " ("+EntryDbHelper.ID+", "
				+EntryDbHelper.TITLE+", "
				+EntryDbHelper.CONTENT+", "
				+EntryDbHelper.STAR+", "
				+EntryDbHelper.SOUND+", "
				+EntryDbHelper.CREATETIME+", "
//				+EntryDbHelper.NOTEFILES+", "
				+EntryDbHelper.ISSUBMIT+", "
				+EntryDbHelper.SUBMITTYPE+") values(?,?,?,?,?,?,?,?,?);select last_insert_rowid();";
	
	public static final String QUERY_ID_ALL = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMINDER + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +EventItem.DELETE+ "' ORDER BY " + EntryDbHelper.CREATETIME + " DESC";
	
	public static final String QUERY_ID_ALL_DIFF = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMINDER + " WHERE " + EntryDbHelper.SUBMITTYPE + " <> '" +EventItem.IDLE+ "' ORDER BY " + EntryDbHelper.CREATETIME + " DESC";
	
	public static final String QUERY_ID_ONE = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMINDER + " WHERE " + EntryDbHelper.MID+" = ?";	
	
	public static final String QUERY_ID_ONE_ID = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_REMINDER + " WHERE " + EntryDbHelper.ID+" = ?";	
	
	public static final String QUERY_ID_NUM = "SELECT COUNT(*) FROM "
			+ EntryDbHelper.TABLE_NAME_REMINDER + " WHERE "+EntryDbHelper.ID+" = ?";
	
	public static final String WHERE_MID = EntryDbHelper.MID+" = ?";
	
	public static final String WHERE_ID = EntryDbHelper.ID+" = ?";
	
//	public static final String QUERY_ROW = "SELECT * FROM "
//			+ EntryDbHelper.TABLE_NAME_REMINDER + " ORDER BY rowid LIMIT 1 OFFSET 1";
	public static final String QUERY_ROW = "select * from "+EntryDbHelper.TABLE_NAME_REMINDER+" where rowid = ?";
	
	public ReminderDao(Context context) {
		this.context = context;
		dbHelper = new EntryDbHelper(context);
	}
	
	/**
	 * 添加备忘录
	 * @param data
	 */
	public String addData(EventItem ei) {
		long row = -1;
		String mid = "";
		YzLog.e("yz", "addData = " + ei.getTitle());
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, ei.getId());
				cv.put(EntryDbHelper.AID, ei.getAid());
				cv.put(EntryDbHelper.TITLE, ei.getTitle());
				cv.put(EntryDbHelper.CONTENT, ei.getContent());
				cv.put(EntryDbHelper.STAR, ei.getStar());
				cv.put(EntryDbHelper.SOUND, ei.getSound());
				cv.put(EntryDbHelper.CREATETIME, ei.getCreatetime());
				cv.put(EntryDbHelper.ISSUBMIT, ei.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, ei.getSubmittype());
				row = db.insert(EntryDbHelper.TABLE_NAME_REMINDER, null, cv);
				db.setTransactionSuccessful();
				db.endTransaction();
				if (row != -1) {
					EventItem _ei = queryRow(row);
					mid = _ei.getMid();
					
					List<NoteFile> nfiles = ei.getNoteFiles();
					NoteListDao nd = new NoteListDao(context);
					nd.addDatas(mid, nfiles);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return mid;
	}	
	
	/**
	 * 更新备忘录
	 * @param data
	 */
	public String updateData(EventItem ei) {
		YzLog.e("yz", "updateData = " + ei.getTitle() + ",mid = " + ei.getMid());
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, ei.getId());
				cv.put(EntryDbHelper.AID, ei.getAid());
				cv.put(EntryDbHelper.TITLE, ei.getTitle());
				cv.put(EntryDbHelper.CONTENT, ei.getContent());
				cv.put(EntryDbHelper.STAR, ei.getStar());
				cv.put(EntryDbHelper.SOUND, ei.getSound());
				cv.put(EntryDbHelper.CREATETIME, ei.getCreatetime());
				cv.put(EntryDbHelper.ISSUBMIT, ei.getIssubmit());
				cv.put(EntryDbHelper.SUBMITTYPE, ei.getSubmittype());
				db.update(EntryDbHelper.TABLE_NAME_REMINDER, cv, WHERE_MID, new String[]{ei.getMid()});
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
//		queryAll();
		return ei.getId();
	}	
	
	/**
	 * 查询所有ID
	 * @return
	 */
	public HashMap<String, EventItem> queryAll() {
		YzLog.e("yz", "queryAll");
		LinkedHashMap<String, EventItem> items = new LinkedHashMap<String, EventItem>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ALL, null);
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String star = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STAR));
					String sound = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOUND));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					Log.e("yz", "mid = " + mid);
					Log.e("yz", "id = " + id);
					
					EventItem ei = new EventItem();
					ei.setMid(String.valueOf(mid));
					ei.setId(id);
					ei.setAid(aid);
					ei.setTitle(title);
					ei.setContent(content);
					ei.setStar(star);
					ei.setSound(sound);
					ei.setCreatetime(createtime);
					ei.setIssubmit(issubmit);
					ei.setSubmittype(submittype);
					NoteListDao nd = new NoteListDao(context);
					ei.setNoteFiles(nd.queryList(String.valueOf(mid)));
					
					items.put(String.valueOf(mid), ei);
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
		
		return items;		
	}
	
	/**
	 * 查询所有不同
	 * @return
	 */
	public HashMap<String, EventItem> queryAllDiff() {
		YzLog.e("yz", "queryAllDiff");
		LinkedHashMap<String, EventItem> items = new LinkedHashMap<String, EventItem>();
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
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String star = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STAR));
					String sound = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOUND));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					EventItem ei = new EventItem();
					ei.setMid(String.valueOf(mid));
					ei.setId(id);
					ei.setAid(aid);
					ei.setTitle(title);
					ei.setContent(content);
					ei.setStar(star);
					ei.setSound(sound);
					ei.setCreatetime(createtime);
					ei.setIssubmit(issubmit);
					ei.setSubmittype(submittype);
					NoteListDao nd = new NoteListDao(context);
					ei.setNoteFiles(nd.queryList(String.valueOf(mid)));
					
					items.put(String.valueOf(mid), ei);
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
		
		return items;		
	}
	
	/**
	 * 按行号查询
	 * @param number
	 * @return
	 */
	public EventItem queryRow(long row) {
		EventItem ei = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ROW, new String[] {String.valueOf(row)});//
				YzLog.e("yz", "this cursor = " + cursor.getCount());
				if (cursor.moveToFirst()) {
					ei = new EventItem();
					
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String star = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STAR));
					String sound = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOUND));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					ei.setMid(String.valueOf(mid));
					ei.setId(id);
					ei.setAid(aid);
					ei.setTitle(title);
					ei.setContent(content);
					ei.setStar(star);
					ei.setSound(sound);
					ei.setCreatetime(createtime);
					ei.setIssubmit(issubmit);
					ei.setSubmittype(submittype);
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
		
		return ei;
	}	
	
	/**
	 * 检查是否有ID
	 * @param number
	 * @return
	 */
	public boolean hasID(String id) {
		long count = 0;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_NUM, new String[] {id});
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
	public ArrayList<String> queryHasNoIDs(String _ids) {
		ArrayList<String> ids = new ArrayList<String>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			JSONArray ja = JSON.parseArray(_ids);
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				for (int i=0;i<ja.size();i++) {
					JSONObject jo = ja.getJSONObject(i);
					String _id = "";
					Object id = jo.get("id");
					if (id instanceof Integer) {
						_id = String.valueOf((Integer) id);
					}
					if (!hasID(_id)) {
						ids.add(_id);
					}
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
		return ids;
	}	
	
	/**
	 * 查询 by mid
	 * @param _mid
	 * @return
	 */
	public EventItem queryOne(String _mid) {
		EventItem ei = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ONE, new String[] {_mid});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				if (cursor.moveToNext()) {
					ei = new EventItem();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String star = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STAR));
					String sound = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOUND));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					ei.setMid(String.valueOf(mid));
					ei.setId(id);
					ei.setAid(aid);
					ei.setTitle(title);
					ei.setContent(content);
					ei.setStar(star);
					ei.setSound(sound);
					ei.setCreatetime(createtime);
					ei.setIssubmit(issubmit);
					ei.setSubmittype(submittype);
					NoteListDao nd = new NoteListDao(context);
					ei.setNoteFiles(nd.queryList(String.valueOf(mid)));
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
		
		return ei;		
	}
	
	/**
	 * 查询 by id
	 * @param _id
	 * @return
	 */
	public EventItem queryOneById(String _id) {
		EventItem ei = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_ID_ONE_ID, new String[] {_id});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				if (cursor.moveToNext()) {
					ei = new EventItem();
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String aid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.AID));
					String title = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TITLE));
					String content = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CONTENT));
					String star = cursor.getString(cursor.getColumnIndex(EntryDbHelper.STAR));
					String sound = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SOUND));
					String createtime = cursor.getString(cursor.getColumnIndex(EntryDbHelper.CREATETIME));
					String issubmit = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ISSUBMIT));
					String submittype = cursor.getString(cursor.getColumnIndex(EntryDbHelper.SUBMITTYPE));
					
					ei.setMid(String.valueOf(mid));
					ei.setId(id);
					ei.setAid(aid);
					ei.setTitle(title);
					ei.setContent(content);
					ei.setStar(star);
					ei.setSound(sound);
					ei.setCreatetime(createtime);
					ei.setIssubmit(issubmit);
					ei.setSubmittype(submittype);
					NoteListDao nd = new NoteListDao(context);
					ei.setNoteFiles(nd.queryList(String.valueOf(mid)));
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
		
		return ei;		
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
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMINDER, WHERE_MID, new String[]{mid});
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
				int i = db.delete(EntryDbHelper.TABLE_NAME_REMINDER, WHERE_ID, new String[]{id});
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
