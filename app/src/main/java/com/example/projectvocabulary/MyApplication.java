package com.example.projectvocabulary;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by ignacy on 28.04.17.
 */

public class MyApplication extends Application {

	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}
}
