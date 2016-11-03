package com.sc.reminder.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EntryDbHelper extends SQLiteOpenHelper {

	private static final String TAG = "EntryDbHelper";
	public static final String TABLE_NAME_REMINDER = "ScReminder";
	public static final String TABLE_NAME_NOTEFILE = "ScNoteFile";
	public static final String TABLE_NAME_REMIND_LIST = "ScRemindList";
	public static final String TABLE_NAME_REMIND_DETAIL = "ScRemindDetail";
	public static final String TABLE_NAME_TRIP = "ScTrip";
	public static final String TABLE_NAME_REMIND_USERS = "ScRemindUsers";
	
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
	
	public static final String RID = "rid";//依赖ID
	public static final String MURIS = "muris";
	public static final String URIS = "uris";
	public static final String TIME = "time";
	
	public static final String CLASSID = "classId";
	public static final String MCLASSID = "mclassId";
	public static final String CREATEID = "createId";
	public static final String REMINDERTIME = "reminderTime";
	public static final String REPEAT = "repeat";
	public static final String REPEATEND = "repeatEnd";
	public static final String SOURCEID = "sourceId";
	public static final String STATES = "states";
	public static final String ISDEL = "isdel";
	public static final String UPDATETIME = "updatetime";
	
	public static final String CID = "cid";
	public static final String REMINDERID = "reminderId";
	public static final String MREMINDERID = "mreminderId";
	public static final String STATUS = "status";
	
	public static final String ANAME = "aname";
	public static final String RESON = "reson";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String NAME = "name";
	
	public static final String ALLDAY = "allday";
	public static final String ALLDATE = "alldate";
	public static final String PRIVACY = "privacy";
	public static final String ISOPEN = "isopen";
	public static final String ISLOCK = "islock";
	public static final String TYPES = "types";
	public static final String STARTTIME = "starttime";
	public static final String ENDTIME = "endtime";
	public static final String REMINDTIME = "remindtime";
	public static final String REMINDTYPE = "remindtype";

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
	
	public static final String CREATE_TABLE_NOTEFILE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_NOTEFILE
			+ "("
			+ MID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ ID + " TEXT NOT NULL , "
			+ RID + " TEXT NOT NULL , "
			+ MURIS + " TEXT NOT NULL , "
			+ URIS + " TEXT NOT NULL , " 
			+ TIME + " TEXT NOT NULL " 
			+ ")";
	
	public static final String CREATE_TABLE_REMIND_LIST = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_REMIND_LIST
			+ "("
			+ MID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ ID + " TEXT NOT NULL , "
			+ ACCOUNTID + " TEXT NOT NULL , "
			+ TITLE + " TEXT NOT NULL , " 
			+ ISSUBMIT + " TEXT NOT NULL , " 
			+ SUBMITTYPE + " TEXT NOT NULL " 
			+ ")";
	
	public static final String CREATE_TABLE_REMIND_DETAIL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_REMIND_DETAIL
			+ "("
			+ MID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ ID + " TEXT NOT NULL , "
			+ CLASSID + " TEXT NOT NULL , "
			+ MCLASSID + " TEXT NOT NULL , " 
			+ CREATEID + " TEXT NOT NULL , " 
			+ REMINDERTIME + " TEXT NOT NULL ," 
			+ TITLE + " TEXT NOT NULL ," 
			+ CONTENT + " TEXT NOT NULL ," 
			+ REPEAT + " TEXT NOT NULL ," 
			+ REPEATEND + " TEXT NOT NULL ," 
			+ SOURCEID + " TEXT NOT NULL ," 
			+ STATES + " TEXT NOT NULL ," 
			+ ISDEL + " TEXT NOT NULL ," 
			+ CREATETIME + " TEXT NOT NULL ," 
			+ UPDATETIME + " TEXT NOT NULL ," 
			+ ISSUBMIT + " TEXT NOT NULL , " 
			+ SUBMITTYPE + " TEXT NOT NULL " 			
			+ ")";
	
	public static final String CREATE_TABLE_TRIP = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_TRIP
			+ "("
			+ MID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ ID + " TEXT NOT NULL , "
			+ CREATEID + " TEXT NOT NULL , " 
			+ REMINDTYPE + " TEXT NOT NULL ," 
			+ REMINDTIME + " TEXT NOT NULL ," 
			+ TITLE + " TEXT NOT NULL ," 
			+ CONTENT + " TEXT NOT NULL ," 
			+ ALLDAY + " TEXT NOT NULL ," 
			+ ALLDATE + " TEXT NOT NULL ," 
			+ PRIVACY + " TEXT NOT NULL ," 
			+ ISOPEN + " TEXT NOT NULL ," 
			+ ISLOCK + " TEXT NOT NULL ," 
			+ TYPES + " TEXT NOT NULL ," 
			+ STARTTIME + " TEXT NOT NULL ," 
			+ ENDTIME + " TEXT NOT NULL ," 
			+ ISSUBMIT + " TEXT NOT NULL , " 
			+ SUBMITTYPE + " TEXT NOT NULL " 			
			+ ")";
	
	public static final String CREATE_TABLE_REMIND_USERS = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_REMIND_USERS
			+ "("
			+ MID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
			+ CID + " TEXT NOT NULL , "
			+ MCLASSID + " TEXT NOT NULL , "
			+ REMINDERID + " TEXT NOT NULL , " 
			+ MREMINDERID + " TEXT NOT NULL , " 
			+ AID + " TEXT NOT NULL , " 
			+ ANAME + " TEXT NOT NULL , " 
			+ RESON + " TEXT NOT NULL , " 
			+ PHONE + " TEXT NOT NULL , " 
			+ EMAIL + " TEXT NOT NULL , " 
			+ NAME + " TEXT NOT NULL , " 
			+ STATUS + " TEXT NOT NULL " 
			+ ")";	
	
	public EntryDbHelper(Context context) {
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

			db.execSQL(EntryDbHelper.CREATE_TABLE_REMINDER);
			db.execSQL(EntryDbHelper.CREATE_TABLE_NOTEFILE);
			db.execSQL(EntryDbHelper.CREATE_TABLE_REMIND_LIST);
			db.execSQL(EntryDbHelper.CREATE_TABLE_REMIND_DETAIL);
			db.execSQL(EntryDbHelper.CREATE_TABLE_TRIP);
			db.execSQL(EntryDbHelper.CREATE_TABLE_REMIND_USERS);
			
			db.setTransactionSuccessful();
			db.endTransaction();
			
			
		} catch (Exception e) {
			Log.e(TAG, " Exception : " + e);
		}
	}

}
