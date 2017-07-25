package com.example.projectvocabulary.network;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.SessionWord;
import com.example.projectvocabulary.domain.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ignacy on 14.06.17.
 */

public interface ProjectVocabularyApi {

	@POST("login")
	Call<User> login(@Body LoginRequestDto request);

	@GET("users/{userId}")
	Call<User> users(@Path("userId") Long userId);

	@POST("dictionary/session")
	Call<List<SessionWord>> session(@Body SessionRequest request);

	@PUT("users/{userId}")
	Call<User> update(@Path("userId") Long userId, @Body User user);

	@POST("logout")
	void logout();

	@POST("/register")
	Call<User> register(@Body RegistrationDto dto);
}
