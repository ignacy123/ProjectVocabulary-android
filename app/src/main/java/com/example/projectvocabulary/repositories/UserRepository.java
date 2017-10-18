package com.example.projectvocabulary.repositories;

import android.arch.lifecycle.LiveData;

import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.status.Resource;

/**
 * Created by ignacy on 28.09.17.
 */

public interface UserRepository {
	LiveData<Resource<User>> getUser();
}
