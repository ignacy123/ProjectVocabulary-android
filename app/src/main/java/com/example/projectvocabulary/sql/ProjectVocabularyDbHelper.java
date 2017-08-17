package com.example.projectvocabulary.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;

/**
 * Created by ignacy on 28.04.17.
 */

public class ProjectVocabularyDbHelper extends SQLiteOpenHelper {

	private static ProjectVocabularyDbHelper instance;

	public static final ProjectVocabularyDbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new ProjectVocabularyDbHelper(context);
		}
		return instance;
	}

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	public ProjectVocabularyDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createUsers());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(deleteUsers());
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	private String deleteUsers() {
		return "DROP TABLE IF EXISTS " + Tables.USERS;
	}

	private String createUsers() {
		//@formatter:off
		return "CREATE TABLE " + Tables.USERS + " (" +
				UserColumns.ID + " INTEGER PRIMARY KEY," +
				UserColumns.FIRST_NAME + " TEXT," +
				UserColumns.LAST_NAME + " TEXT," +
				UserColumns.EMAIL+ " TEXT)";
		//@formatter:on
	}
}