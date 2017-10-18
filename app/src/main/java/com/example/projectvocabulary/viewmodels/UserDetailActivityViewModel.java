package com.example.projectvocabulary.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.projectvocabulary.MyApplication;
import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.status.Resource;
import com.example.projectvocabulary.repositories.UserRepository;

/**
 * Created by ignacy on 28.09.17.
 */

public class UserDetailActivityViewModel extends AndroidViewModel {

	UserRepository userRepository;

	public LiveData<Resource<User>> getUser() {
		return userRepository.getUser();
	}

	public UserDetailActivityViewModel(Application application) {
		super(application);
		ServiceLocator serviceLocator = MyApplication.getServiceLocator(application);
		userRepository = serviceLocator.getUserRepository();
	}
}
