package com.example.projectvocabulary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import com.example.projectvocabulary.base.AppExecutors;
import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.example.projectvocabulary.repositories.UserRepository;
import com.example.projectvocabulary.repositories.UserRepositoryImpl;
import com.example.projectvocabulary.sql.AppDatabase;
import com.example.projectvocabulary.sql.UserDAO;
import com.facebook.stetho.Stetho;

/**
 * Created by ignacy on 28.04.17.
 */

public class MyApplication extends Application implements ServiceLocator {

	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}

	public static ServiceLocator getServiceLocator(Application application) {
		return (ServiceLocator) application;
	}

	@Override
	public ProjectVocabularyApi getProjectVocabularyApi() {
		return ProjectVocabularyApiImpl.getInstance(this);
	}

	@Override
	public UserDAO getUserDAO() {
		return getAppDatabase().userDao();
	}

	@Override
	public SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public LocalBroadcastManager getLocalBroadcastManager() {
		return LocalBroadcastManager.getInstance(this);
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public AppDatabase getAppDatabase() {
		return AppDatabase.getInstance(this);
	}

	@Override
	public UserRepository getUserRepository() {
		return UserRepositoryImpl.getInstance(this);
	}

	@Override
	public AppExecutors getAppExecutors() {
		return AppExecutors.getInstance();
	}

	@Override
	public ViewModelProvider.Factory getViewModelFactory() {
		if (sDefaultFactory == null) {
			sDefaultFactory = new ViewModelProviders.DefaultFactory(this);
		}
		return sDefaultFactory;
	}

	@SuppressLint("StaticFieldLeak")
	private static ViewModelProviders.DefaultFactory sDefaultFactory;

}
