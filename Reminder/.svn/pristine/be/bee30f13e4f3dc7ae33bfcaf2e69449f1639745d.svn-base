package com.sc.reminder.database.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sc.reminder.database.db.EntryDbHelper;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.NoteFile;
import com.sc.reminder.utils.YzLog;


public class NoteListDao {
	
	Context context;
	EntryDbHelper dbHelper;
	
	public static final String QUERY_LIST = "SELECT * FROM "
			+ EntryDbHelper.TABLE_NAME_NOTEFILE + " WHERE " + EntryDbHelper.RID+" = ?";	
	
	public static final String DELETE_WHERE_MID = EntryDbHelper.MID + "=?";
	public static final String DELETE_WHERE_RID = EntryDbHelper.RID + "=?";
	
	public NoteListDao(Context context) {
		this.context = context;
		dbHelper = new EntryDbHelper(context);
	}
	
	/**
	 * 获取文件列表
	 * @param _nid
	 * @return
	 */
	public ArrayList<NoteFile> queryList(String _rid) {
		ArrayList<NoteFile> nfiles = new ArrayList<NoteFile>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			if (db.isOpen()) {
				cursor = db.rawQuery(QUERY_LIST, new String[] {_rid});
				YzLog.e("yz", "cursor = " + cursor.getCount());
				while (cursor.moveToNext()) {
					int mid = cursor.getInt(cursor.getColumnIndex(EntryDbHelper.MID));
					String id = cursor.getString(cursor.getColumnIndex(EntryDbHelper.ID));
					String rid = cursor.getString(cursor.getColumnIndex(EntryDbHelper.RID));
					String muris = cursor.getString(cursor.getColumnIndex(EntryDbHelper.MURIS));
					String uris = cursor.getString(cursor.getColumnIndex(EntryDbHelper.URIS));
					String time = cursor.getString(cursor.getColumnIndex(EntryDbHelper.TIME));
					NoteFile nf = new NoteFile();
					nf.setMid(String.valueOf(mid));
					nf.setId(id);
					nf.setRid(rid);
					nf.setMuris(muris);
					nf.setUris(uris);
					nf.setTime(time);
					nfiles.add(nf);
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
		
		return nfiles;		
	}	
	
	/**
	 * 多文件添加
	 * @param data
	 */
	public void addDatas(String rid, List<NoteFile> nfiles) {
		for (NoteFile nf : nfiles) {
			addData(rid, nf);
		}
	}	
	
	/**
	 * 单文件添加
	 * @param data
	 */
	public void addData(String rid, NoteFile file) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
//				db.execSQL(INSERT_DATA, new Object[] {ei.getId(), ei.getTitle(), ei.getContent(), ei.getStar(), ei.getSound(), ei.getCreatetime(), files, ei.getIssubmit(), ei.getSubmittype()});
				ContentValues cv = new ContentValues();
				cv.put(EntryDbHelper.ID, file.getId());
				cv.put(EntryDbHelper.RID, rid);
				cv.put(EntryDbHelper.MURIS, file.getMuris());
				cv.put(EntryDbHelper.URIS, file.getUris());
				cv.put(EntryDbHelper.TIME, file.getTime());
				db.insert(EntryDbHelper.TABLE_NAME_NOTEFILE, null, cv);
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
	 * 用备忘录ID删除文件
	 * @param data
	 */
	public void deleteDataByRID(String rid) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				db.delete(EntryDbHelper.TABLE_NAME_NOTEFILE, DELETE_WHERE_RID, new String[]{rid});
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
	 * 单文件删除
	 * @param data
	 */
	public void deleteData(String mid) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.beginTransaction();
				db.delete(EntryDbHelper.TABLE_NAME_NOTEFILE, DELETE_WHERE_MID, new String[]{mid});
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
