package com.example.projectvocabulary.db;

import android.support.test.runner.AndroidJUnit4;

import com.example.projectvocabulary.domain.user.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.projectvocabulary.util.LiveDataTestUtil.getValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by ignacy on 09.11.17.
 */
@RunWith(AndroidJUnit4.class)
public class UserDAOTest extends DbTest {

	@Test
	public void fetchesUser() throws InterruptedException {
		User user = getValue(db.userDao()
				.fetch());
		assertThat(user, nullValue());
	}

	@Test
	public void insertsAndFetchesUser() throws InterruptedException {
		User user = new User();
		user.setEmail("wdw@wdw");
		user.setFirstName("Janusz");
		user.setLastName("Kowalski");
		user.setId(1L);
		db.userDao()
				.persist(user);
		User userFromDb = getValue(db.userDao()
				.fetch());
		assertThat(userFromDb, is(user));
	}

	@Test
	public void clearsDatabase() throws InterruptedException {

		User user = new User();
		user.setEmail("wdw@wdw");
		user.setFirstName("Janusz");
		user.setLastName("Kowalski");
		user.setId(1L);
		db.userDao()
				.persist(user);
		db.userDao()
				.clear();

		User userFromDb = getValue(db.userDao()
				.fetch());
		assertThat(userFromDb, nullValue());

	}

}
