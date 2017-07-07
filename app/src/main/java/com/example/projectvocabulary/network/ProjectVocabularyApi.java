package com.example.projectvocabulary.network;

import com.example.projectvocabulary.domain.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ignacy on 14.06.17.
 */

public interface ProjectVocabularyApi {

	@POST("login")
	Call<User> login(@Body LoginRequestDto request);

	@GET("users/{userId}")
	Call<User> users(@Path("userId") Long userId);
}
