package com.example.projectvocabulary.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectvocabulary.domain.user.User;

/**
 * Created by ignacy on 25.07.17.
 */

public class UserRepositoryImpl implements  UserRepository {

	private static UserRepositoryImpl instance;
	private final ProjectVocabularyDbHelper dbHelper;

	public static final UserRepositoryImpl getInstance(Context context) {
		if (instance == null) {
			instance = new UserRepositoryImpl(context);
		}
		return instance;
	}

	public UserRepositoryImpl(Context context) {
		dbHelper = ProjectVocabularyDbHelper.getInstance(context);
	}

	@Override
	public void persist(User user) {
		clear();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.insert(Tables.USERS, null, user.getContentValues());
	}


	@Override
	public void clear() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(Tables.USERS, null, null);
	}


	@Override
	public User fetch() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(false, Tables.USERS, null, null, null, null, null, null, null);
		int count = cursor.getCount();
		User user = null;
		if (count == 1) {
			cursor.moveToFirst();
			user = new User(cursor);
		}
		cursor.close();
		return user;
	}
}
