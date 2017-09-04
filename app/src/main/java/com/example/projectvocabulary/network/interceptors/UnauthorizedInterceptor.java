package com.example.projectvocabulary.network.interceptors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.projectvocabulary.constants.Broadcasts;
import com.example.projectvocabulary.constants.Preferences;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.LoginRequestDto;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.example.projectvocabulary.sql.UserRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by ignacy on 22.08.17.
 */

public class UnauthorizedInterceptor implements Interceptor {

	private final LocalBroadcastManager manager;
	ProjectVocabularyApi api;
	UserRepository repository;
	SharedPreferences sharedPref;

	public UnauthorizedInterceptor(Context context) {
		api = ProjectVocabularyApiImpl.getInstance(context);
		repository = UserRepository.getInstance(context);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		manager = LocalBroadcastManager.getInstance(context);
	}

	@Override
	public Response intercept(Chain chain) throws IOException {

		Response response = chain.proceed(chain.request());
		if (response.code() == 401) {
			String email = repository.fetch()
					.getEmail();
			String password = sharedPref.getString(Preferences.PASSWORD, "");
			retrofit2.Response<User> loginResponse = api.login(new LoginRequestDto(email, password))
					.execute();
			if (loginResponse.isSuccessful()) {
				User user = loginResponse.body();
				Log.d("TAG", user.getEmail());

				SharedPreferences.Editor editor = sharedPref.edit();

				editor.putLong(Preferences.USER_ID, user.getId());
				editor.putString(Preferences.PASSWORD, password);
				editor.commit();

				repository.persist(user);
				response = chain.proceed(chain.request());
			} else {

				manager.sendBroadcast(new Intent(Broadcasts.LOGOUT));
			}
		}
		return response;
	}
}
