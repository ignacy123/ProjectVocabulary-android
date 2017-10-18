package com.example.projectvocabulary.sql;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.domain.user.User;

/**
 * Created by ignacy on 21.09.17.
 */

@Database(entities = { User.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase instance;

	public static final AppDatabase getInstance(ServiceLocator locator) {
		if (instance == null) {
			instance = Room.databaseBuilder(locator.getContext(),
					AppDatabase.class, "database-name").build();
		}
		return instance;
	}
	public abstract UserDAO userDao();
}
