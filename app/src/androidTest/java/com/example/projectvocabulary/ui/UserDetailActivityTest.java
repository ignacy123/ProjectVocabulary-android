package com.example.projectvocabulary.ui;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.projectvocabulary.R;
import com.example.projectvocabulary.UserDetailActivity;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.status.Resource;
import com.example.projectvocabulary.util.MyTestApplication;
import com.example.projectvocabulary.viewmodels.UserDetailActivityViewModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

/**
 * Created by ignacy on 09.11.17.
 */
@RunWith(AndroidJUnit4.class)
public class UserDetailActivityTest {

	@Rule
	public ActivityTestRule<UserDetailActivity> activityRule = new ActivityTestRule<>(UserDetailActivity.class, true, false);

	private UserDetailActivityViewModel viewModel;
	private MutableLiveData<Resource<User>> userData = new MutableLiveData<>();

	@Before
	public void setup() {
		viewModel = MyTestApplication.userDetailActivityViewModel;
		when(viewModel.getUser()).thenReturn(userData);

		Intent intent = new Intent(Intent.ACTION_PICK);
		activityRule.launchActivity(intent);
	}

	@Test
	public void showsData() {
		User user = new User();
		user.setEmail("wdw@wdw");
		user.setFirstName("Janusz");
		user.setLastName("Kowalski");
		user.setId(1L);
		userData.postValue(Resource.success(user));
		Espresso.onView(ViewMatchers.withId(R.id.textView5))
				.check(matches(withText(user.getFirstName())));
	}

}
