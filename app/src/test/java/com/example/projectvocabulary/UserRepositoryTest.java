package com.example.projectvocabulary;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import com.example.projectvocabulary.constants.Preferences;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.status.ApiResponse;
import com.example.projectvocabulary.network.status.Resource;
import com.example.projectvocabulary.repositories.UserRepository;
import com.example.projectvocabulary.repositories.UserRepositoryImpl;
import com.example.projectvocabulary.sql.UserDAO;
import com.example.projectvocabulary.utils.ApiUtil;
import com.example.projectvocabulary.utils.MockedServiceLocator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by ignacy on 18.10.17.
 */
public class UserRepositoryTest {

	UserRepository repository;
	MockedServiceLocator mockedServiceLocator = new MockedServiceLocator();
	UserDAO userDAO;
	ProjectVocabularyApi api;
	SharedPreferences preferences;

	@Before
	public void setUp() {
		repository = UserRepositoryImpl.getInstance(mockedServiceLocator);
		userDAO = mockedServiceLocator.getUserDAO();
		api = mockedServiceLocator.getProjectVocabularyApi();
		preferences = mockedServiceLocator.getSharedPreferences();
	}

	@Test
	public void givesUser() {
		MutableLiveData<User> user = new MutableLiveData<>();
		User returnedUser = new User();
		returnedUser.setEmail("wew");
		returnedUser.setFirstName("wow");
		user.setValue(returnedUser);
		when(preferences.getLong(Preferences.USER_ID, -1)).thenReturn(1L);
		LiveData<ApiResponse<User>> call = ApiUtil.successCall(returnedUser);
		when(api.usersLd(1L)).thenReturn(call);
		when(userDAO.fetch()).thenReturn(user);
		Observer observer = mock(Observer.class);
		repository.getUser()
				.observeForever(observer);
		verify(observer).onChanged(Resource.loading(null));
	}

	@Rule
	public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

}
