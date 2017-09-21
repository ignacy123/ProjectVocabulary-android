package com.example.projectvocabulary.sql;

import com.example.projectvocabulary.domain.user.User;

/**
 * Created by ignacy on 21.09.17.
 */

public interface UserRepository {

	void persist(User user);

	void clear();

	User fetch();
}
