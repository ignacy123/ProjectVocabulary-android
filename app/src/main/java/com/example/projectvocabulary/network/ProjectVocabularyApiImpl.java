package com.example.projectvocabulary.network;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.User;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Path;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Network service singleton
 *
 * Created by tokruzel on 07/07/2017.
 */

public class ProjectVocabularyApiImpl implements ProjectVocabularyApi {

	private static ProjectVocabularyApiImpl instance;

	private ProjectVocabularyApi service;

	private User user = new User();

	public static final ProjectVocabularyApi getInstance() {
		if (instance == null) {
			instance = new ProjectVocabularyApiImpl();
		}
		return instance;
	}

	private ProjectVocabularyApiImpl() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		final CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		final JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieManager);

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging)
				.cookieJar(cookieJar)
				.build();
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.125:8080/projectvocabulary/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();

		service = retrofit.create(ProjectVocabularyApi.class);
	}

	@Override
	public Call<User> login(@Body LoginRequestDto request) {
		return service.login(request);
	}

	@Override
	public Call<User> users(@Path("userId") Long userId) {
		return service.users(userId);
	}

	@Override
	public Call<User> update(@Path("userId") Long userId) {
		return service.update(userId);
	}

	@Override
	public void setUserId(Long id) {
		user.setId(id);
	}

	@Override
	public Long getUserId() {
		return user.getId();
	}

	@Override
	public Call<User> session(@Body SessionRequest request) {
		return service.session(request);
	}
}
