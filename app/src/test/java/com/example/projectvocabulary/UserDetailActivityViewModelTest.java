package com.example.projectvocabulary;

import com.example.projectvocabulary.repositories.UserRepository;
import com.example.projectvocabulary.utils.MockedServiceLocator;
import com.example.projectvocabulary.viewmodels.UserDetailActivityViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

		assertThat(viewModel.getUser(), nullValue());
		verify(userRepository, times(1)).getUser();
	}
}

