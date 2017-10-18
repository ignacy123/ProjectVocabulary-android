package com.example.projectvocabulary.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import com.example.projectvocabulary.base.AppExecutors;
import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.repositories.UserRepository;
import com.example.projectvocabulary.sql.AppDatabase;
import com.example.projectvocabulary.sql.UserDAO;

import static org.mockito.Mockito.mock;

/**
 * Created by ignacy on 18.10.17.
 */

public class MockedServiceLocator extends Application implements ServiceLocator {

	private UserRepository mockedUserRepository = mock(UserRepository.class);
	private UserDAO mockedUserDAO = mock(UserDAO.class);
	private ProjectVocabularyApi mockedProjectVocabulary = mock(ProjectVocabularyApi.class);
	private SharedPreferences mockedSharedPreferences = mock(SharedPreferences.class);

	@Override
	public ProjectVocabularyApi getProjectVocabularyApi() {
		return mockedProjectVocabulary;
	}

	@Override
	public UserDAO getUserDAO() {
		return mockedUserDAO;
	}

	@Override
	public SharedPreferences getSharedPreferences() {
		return mockedSharedPreferences;
	}

	@Override
	public LocalBroadcastManager getLocalBroadcastManager() {
		return null;
	}

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public AppDatabase getAppDatabase() {
		return null;
	}

	@Override
	public UserRepository getUserRepository() {
		return mockedUserRepository;
	}

	@Override
	public AppExecutors getAppExecutors() {
		return new InstantAppExecutors();
	}
}
