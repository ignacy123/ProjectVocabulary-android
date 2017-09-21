package com.example.projectvocabulary;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.example.projectvocabulary.sql.UserRepository;
import com.example.projectvocabulary.sql.UserRepositoryImpl;
import com.facebook.stetho.Stetho;

/**
 * Created by ignacy on 28.04.17.
 */

public class MyApplication extends Application implements ServiceLocator {

	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}
	public static ServiceLocator getServiceLocator(Application application){
		return (ServiceLocator) application;
	}

	@Override
	public ProjectVocabularyApi getProjectVocabularyApi() {
		return ProjectVocabularyApiImpl.getInstance(this);
	}

	@Override
	public UserRepository getUserRepository() {
		return UserRepositoryImpl.getInstance(this);
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

}
