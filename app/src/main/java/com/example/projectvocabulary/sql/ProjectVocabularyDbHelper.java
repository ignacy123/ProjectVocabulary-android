package com.example.savingtoansql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projectvocabulary.sql.Tables;
import com.example.projectvocabulary.sql.UserContract;

/**
 * Created by ignacy on 28.04.17.
 */

public class ProjectVocabularyDbHelper extends SQLiteOpenHelper {

	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + Tables.USERS + " (" + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY,"
					+ UserContract.UserEntry.COLUMN_NAME_TITLE + " TEXT," + UserContract.UserEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Tables.USERS;
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	public ProjectVocabularyDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}