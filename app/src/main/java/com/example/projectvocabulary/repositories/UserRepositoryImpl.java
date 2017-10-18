package com.example.projectvocabulary.repositories;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.projectvocabulary.base.AppExecutors;
import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.constants.Preferences;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.status.ApiResponse;
import com.example.projectvocabulary.network.status.NetworkBoundResource;
import com.example.projectvocabulary.network.status.Resource;
import com.example.projectvocabulary.sql.UserDAO;

/**
 * Created by ignacy on 28.09.17.
 */

public class UserRepositoryImpl implements UserRepository {

	private static UserRepositoryImpl instance;
	private ProjectVocabularyApi api;
	private SharedPreferences preferences;
	private AppExecutors appExecutors;

	public static final UserRepository getInstance(ServiceLocator locator) {
		if (instance == null) {
			instance = new UserRepositoryImpl(locator);
		}
		return instance;
	}

	UserDAO userDAO;

	@Override
	public LiveData<Resource<User>> getUser() {
		return new NetworkBoundResource<User, User>(appExecutors) {

			@Override
			protected void saveCallResult(@NonNull User item) {
				userDAO.persist(item);
			}

			@Override
			protected boolean shouldFetch(@Nullable User data) {
				return true;
			}

			@NonNull
			@Override
			protected LiveData<User> loadFromDb() {
				return userDAO.fetch();
			}

			@NonNull
			@Override
			protected LiveData<ApiResponse<User>> createCall() {

				return api.usersLd(preferences.getLong(Preferences.USER_ID, -1));

			}
		}.asLiveData();

	}

	private UserRepositoryImpl(ServiceLocator locator) {
		userDAO = locator.getUserDAO();
		api = locator.getProjectVocabularyApi();
		preferences = locator.getSharedPreferences();
		appExecutors = locator.getAppExecutors();
	}
}
