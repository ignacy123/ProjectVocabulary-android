package com.example.projectvocabulary.network.interceptors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.projectvocabulary.constants.Broadcasts;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by ignacy on 22.08.17.
 */

public class NetworkFailureInterceptor implements Interceptor {

	LocalBroadcastManager manager;

	public NetworkFailureInterceptor(Context context) {
		manager = LocalBroadcastManager.getInstance(context);
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		try {
			Response response = chain.proceed(chain.request());
			if (!response.isSuccessful() && response.code() != 401 && response.code() != 400) {
				manager.sendBroadcast(new Intent(Broadcasts.NETWORK_FAILURE));
			}
			return response;

		} catch (IOException e) {

			manager.sendBroadcast(new Intent(Broadcasts.NETWORK_FAILURE));
			throw e;
		}
	}
}
