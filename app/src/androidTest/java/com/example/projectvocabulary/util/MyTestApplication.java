package com.example.projectvocabulary.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.example.projectvocabulary.MyApplication;
import com.example.projectvocabulary.viewmodels.UserDetailActivityViewModel;

import static org.mockito.Mockito.mock;

public class MyTestApplication extends MyApplication {

	@Override
	public ViewModelProvider.Factory getViewModelFactory() {
		return new ViewModelProvider.Factory() {

			@Override
			public <T extends ViewModel> T create(final Class<T> modelClass) {
				if (UserDetailActivityViewModel.class.isAssignableFrom(modelClass)) {
					return (T) userDetailActivityViewModel;
				}
				throw new IllegalStateException("Not mocked ViewModel type: " + modelClass);
			}
		};
	}

	public static UserDetailActivityViewModel userDetailActivityViewModel = mock(UserDetailActivityViewModel.class);

}
