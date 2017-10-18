package com.example.projectvocabulary.sql;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.projectvocabulary.domain.user.User;

/**
 * Created by ignacy on 21.09.17.
 */
@Dao
public interface UserDAO {

	@Query("DELETE FROM user")
	void clear();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void persist(User user);

	@Query("SELECT * FROM user")
	LiveData<User> fetch();
}
