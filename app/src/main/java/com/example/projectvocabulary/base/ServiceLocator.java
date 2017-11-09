package com.example.projectvocabulary.base;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.repositories.UserRepository;
import com.example.projectvocabulary.sql.AppDatabase;
import com.example.projectvocabulary.sql.UserDAO;

/**
 * Created by ignacy on 21.09.17.
 */

public interface ServiceLocator {
	ProjectVocabularyApi getProjectVocabularyApi();
	UserDAO getUserDAO();
	SharedPreferences getSharedPreferences();
	LocalBroadcastManager getLocalBroadcastManager();
	Context getContext();
	AppDatabase getAppDatabase();
	UserRepository getUserRepository();
	AppExecutors getAppExecutors();
	ViewModelProvider.Factory getViewModelFactory();
}
