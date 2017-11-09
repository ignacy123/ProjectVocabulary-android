package com.example.projectvocabulary.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.test.InstrumentationRegistry;

import com.example.projectvocabulary.sql.AppDatabase;

import org.junit.After;
import org.junit.Before;

/**
 * Created by ignacy on 09.11.17.
 */

abstract public class DbTest {
	protected AppDatabase db;

	@Before
	public void initDb() {
		db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
				AppDatabase.class).build();
	}

	@After
	public void closeDb() {
		db.close();
	}
}
