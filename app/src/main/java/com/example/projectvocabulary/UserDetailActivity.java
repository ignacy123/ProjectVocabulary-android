package com.example.projectvocabulary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectvocabulary.base.ServiceLocator;
import com.example.projectvocabulary.constants.Preferences;
import com.example.projectvocabulary.databinding.ActivityUserDetailBinding;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.sql.UserDAO;
import com.example.projectvocabulary.viewmodels.UserDetailActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.projectvocabulary.network.status.Status.ERROR;
import static com.example.projectvocabulary.network.status.Status.SUCCESS;

public class UserDetailActivity extends BaseActivity {

	@BindView(R.id.editText6)
	TextView edittext6;
	@BindView(R.id.editText7)
	TextView edittext7;
	@BindView(R.id.editText8)
	TextView edittext8;
	@BindView(R.id.button4)
	Button button;
	@BindView(R.id.detail_form)
	View detail;
	@BindView(R.id.detail_progress)
	View progress;

	ActivityUserDetailBinding binding;
	ProjectVocabularyApi api;

	long userId;
	UserDAO userDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
		ButterKnife.bind(this);
		ServiceLocator locator = MyApplication.getServiceLocator(getApplication());
		UserDetailActivityViewModel viewModel = ViewModelProviders.of(this)
				.get(UserDetailActivityViewModel.class);
		api = locator.getProjectVocabularyApi();
		userDAO = locator.getUserDAO();
		SharedPreferences sharedPref = locator.getSharedPreferences();
		userId = sharedPref.getLong(Preferences.USER_ID, 0);

		viewModel.getUser()
				.observe(this, resource -> {
					User user = resource.data;
					binding.setResource(resource);
					//					showProgress(resource.status == LOADING);
					if (resource.status == ERROR) {

						Toast toast = Toast.makeText(this, "zepsulo sie", Toast.LENGTH_LONG);
						toast.show();

					}
					if (resource.status == SUCCESS) {
						binding.setUser(user);
					}
				});

		View.OnClickListener l = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				update(v);
			}
		};
		button.setOnClickListener(l);

	}

	private void update(View view) {
		User user = new User();

		user.setEmail(edittext6.getText()
				.toString());

		user.setFirstName(edittext7.getText()
				.toString());

		user.setLastName(edittext8.getText()
				.toString());
		showProgress(true);
		api.update(userId, user)
				.enqueue(new Callback<User>() {

					@Override
					public void onResponse(Call<User> call, Response<User> response) {
						showProgress(false);
						if (response.isSuccessful()) {
							Log.d("TAG", response.body()
									.getEmail());
							HandlerThread thread = new HandlerThread("background");
							thread.start();
							new android.os.Handler(thread.getLooper()).post(() -> {
								userDAO.persist(response.body());
							});
						} else {
							new AlertDialog.Builder(UserDetailActivity.this).setMessage("BŁĄÐ")
									.show();
						}
						System.out.println(response.body());
						Timber.d("Connection successful %s", response);
					}

					@Override
					public void onFailure(Call<User> call, Throwable t) {
						showProgress(false);
						Timber.d("Connection failed");
					}
				});

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			detail.setVisibility(show ? View.GONE : View.VISIBLE);
			detail.animate()
					.setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {

						@Override
						public void onAnimationEnd(Animator animation) {
							detail.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});
			progress.setVisibility(show ? View.VISIBLE : View.GONE);
			progress.animate()
					.setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {

						@Override
						public void onAnimationEnd(Animator animation) {
							progress.setVisibility(show ? View.VISIBLE : View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			progress.setVisibility(show ? View.VISIBLE : View.GONE);
			detail.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
