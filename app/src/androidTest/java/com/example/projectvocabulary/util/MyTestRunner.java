package com.example.projectvocabulary.util;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class MyTestRunner extends AndroidJUnitRunner {

	@Override
	public Application newApplication(ClassLoader cl, String className, Context context)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.setProperty("org.mockito.android.target", context.getCacheDir()
				.getPath());
		return super.newApplication(cl, MyTestApplication.class.getName(), context);
	}
}