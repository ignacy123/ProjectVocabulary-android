package com.example.projectvocabulary.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

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

	@Override
	public ProjectVocabularyApi getProjectVocabularyApi() {
		return null;
	}

	@Override
	public UserDAO getUserDAO() {
		return null;
	}

	@Override
	public SharedPreferences getSharedPreferences() {
		return null;
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
}
