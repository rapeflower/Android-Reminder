package com.sc.reminder.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CopyOfEntryDbHelper extends SQLiteOpenHelper {

	private static final String TAG = "EntryDbHelper";
	public static final String TABLE_NAME_REMINDER = "ScReminder";
	
	public static final String MID = "_id";//本地ID
	public static final String ID = "nid";//服务器ID
	public static final String AID = "aid";//用户ID
	public static final String ACCOUNTID = "accountId";//用户ID
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String STAR = "star";
	public static final String SOUND = "sound";
	public static final String CREATETIME = "createtime";
	public static final String NOTEFILES = "notefiles";
	public static final String ISSUBMIT = "issubmit";
	public static final String SUBMITTYPE = "submittype";
	
	public static final String CREATE_TABLE_REMINDER = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_REMINDER
			+ "("
			+ MID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ ID + " TEXT NOT NULL , "
			+ AID + " TEXT NOT NULL , "
			+ TITLE + " TEXT NOT NULL , "
			+ CONTENT + " TEXT NOT NULL , "
			+ STAR + " TEXT NOT NULL , " 
			+ SOUND + " TEXT NOT NULL , " 
			+ CREATETIME + " TEXT NOT NULL , " 
//			+ NOTEFILES + " TEXT NOT NULL , " 
			+ ISSUBMIT + " TEXT NOT NULL , " 
			+ SUBMITTYPE + " TEXT NOT NULL " 
			+ ")";
	
	public CopyOfEntryDbHelper(Context context) {
		super(context, TABLE_NAME_REMINDER + ".db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void createTable(SQLiteDatabase db) {
		try {
			db.beginTransaction();

			db.execSQL(CopyOfEntryDbHelper.CREATE_TABLE_REMINDER);
			
			db.setTransactionSuccessful();
			db.endTransaction();
			
			
		} catch (Exception e) {
			Log.e(TAG, " Exception : " + e);
		}
	}

}
