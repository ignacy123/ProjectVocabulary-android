package com.example.projectvocabulary.network.interceptors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.constants.Broadcasts;
import com.example.projectvocabulary.constants.Preferences;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.LoginRequestDto;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.sql.UserDAO;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by ignacy on 22.08.17.
 */

public class UnauthorizedInterceptor implements Interceptor {

	private final LocalBroadcastManager manager;
	ServiceLocator locator;
	ProjectVocabularyApi api;
	UserDAO repository;
	SharedPreferences sharedPref;

	public UnauthorizedInterceptor(ServiceLocator locator) {

		repository = locator.getUserDAO();
		sharedPref = locator.getSharedPreferences();
		manager = locator.getLocalBroadcastManager();
		this.locator = locator;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {

		Response response = chain.proceed(chain.request());
		if (response.code() == 401) {
			String email = "";// TODO
			String password = sharedPref.getString(Preferences.PASSWORD, "");
			retrofit2.Response<User> loginResponse = getApi().login(new LoginRequestDto(email, password))
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

	private ProjectVocabularyApi getApi() {
		if (api == null) {
			api = locator.getProjectVocabularyApi();
		}
		return api;
	}
}
