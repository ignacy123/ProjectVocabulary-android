package com.example.projectvocabulary;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.status.Resource;
import com.example.projectvocabulary.repositories.UserRepository;
import com.example.projectvocabulary.utils.MockedServiceLocator;
import com.example.projectvocabulary.viewmodels.UserDetailActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ignacy on 18.10.17.
 */

public class UserDetailActivityViewModelTest {

	UserDetailActivityViewModel viewModel;
	MockedServiceLocator mockedServiceLocator = new MockedServiceLocator();
	private UserRepository userRepository;

	@Before
	public void setUp() {
		viewModel = new UserDetailActivityViewModel(mockedServiceLocator);
		userRepository = mockedServiceLocator.getUserRepository();
	}

	@Test
	public void assertNull() {
		assertThat(viewModel.getUser(), nullValue());
	}

	@Test
	public void assertReturnsUser() {
		MutableLiveData<Resource<User>> liveDataUser = new MutableLiveData<>();
		User returnedUser = new User();
		liveDataUser.setValue(Resource.success(returnedUser));
		when(userRepository.getUser()).thenReturn(liveDataUser);
		assertThat(viewModel.getUser(), is(liveDataUser));
		assertThat(viewModel.getUser()
				.getValue().data, is(returnedUser));
		verify(userRepository, times(2)).getUser();
	}

	@Rule
	public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
}

