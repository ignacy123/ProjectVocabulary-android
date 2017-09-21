package com.example.projectvocabulary.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.sql.UserRepository;
import com.example.projectvocabulary.sql.UserRepositoryImpl;

/**
 * Created by ignacy on 21.09.17.
 */

public interface ServiceLocator {
	ProjectVocabularyApi getProjectVocabularyApi();
	UserRepository getUserRepository();
	SharedPreferences getSharedPreferences();
	LocalBroadcastManager getLocalBroadcastManager();
	Context getContext();
}
