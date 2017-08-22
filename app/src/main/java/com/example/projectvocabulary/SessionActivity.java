package com.example.projectvocabulary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.SessionWord;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.LoginRequestDto;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.example.projectvocabulary.constants.Preferences;
import com.example.projectvocabulary.sql.UserRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SessionActivity extends BaseActivity {

	@BindView(R.id.editText5)
	EditText answer;
	@BindView(R.id.textView)
	TextView word;
	@BindView(R.id.button3)
	Button button;
	@BindView(R.id.session_view)
	View session;
	@BindView(R.id.session_progress)
	View mProgressView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session);
		api = ProjectVocabularyApiImpl.getInstance(getApplicationContext());
		Timber.plant(new Timber.DebugTree());
		ButterKnife.bind(this);
		getWordsAndStartSession();
		System.out.println(words);

	}

	ProjectVocabularyApi api;
	List<SessionWord> words;

	public void getWordsAndStartSession() {

		SessionRequest sessionRequest = new SessionRequest();
		sessionRequest.setSize(20);

		showProgress(true);

		api.session(sessionRequest)
				.enqueue(new Callback<List<SessionWord>>() {

					@Override
					public void onResponse(Call<List<SessionWord>> call, Response<List<SessionWord>> response) {
						if (response.isSuccessful()) {

							showProgress(false);
							Log.d("TAG", response.body()
									.toString());
							System.out.println(response.body());
							words = response.body();
							nextQuestion();

						} else {
							if (response.code() == 401) {
								String email = UserRepository.getInstance(SessionActivity.this)
										.fetch()
										.getEmail();
								SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								String password = sharedPref.getString(Preferences.PASSWORD, "");
								api.login(new LoginRequestDto(email, password))
										.enqueue(new Callback<User>() {

											@Override
											public void onResponse(Call<User> call, Response<User> response) {

												showProgress(false);
												User user = response.body();
												if (response.isSuccessful()) {
													Log.d("TAG", user.getEmail());

													SharedPreferences sharedPref =
															PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
													SharedPreferences.Editor editor = sharedPref.edit();

													editor.putLong(Preferences.USER_ID, user.getId());
													editor.putString(Preferences.PASSWORD, password);
													editor.commit();

													UserRepository.getInstance(getApplicationContext())
															.persist(user);
													getWordsAndStartSession();
												} else {
													showProgress(false);
													Context context = getApplicationContext();
													CharSequence text =
															"Your credentials have changed. If you want to contiue using the application you need to log in again.";
													int duration = Toast.LENGTH_LONG;

													Toast toast = Toast.makeText(context, text, duration);
													toast.show();
													Intent intent = new Intent(SessionActivity.this, LoginActivity.class);
													startActivity(intent);

												}
												System.out.println(user);
												Timber.d("Connection successful %s", response);
											}

											@Override
											public void onFailure(Call<User> call, Throwable t) {
												showProgress(false);
												Timber.d("Connection failed");
											}
										});
							}
						}
						System.out.println(response.body());
						Timber.d("Connection successful %s", response);
					}

					@Override
					public void onFailure(Call<List<SessionWord>> call, Throwable t) {
						Timber.d("Connection failed");

						Intent intent = new Intent(SessionActivity.this, RootActivity.class);
						startActivity(intent);
					}
				});
	}

	public void nextQuestion() {
		if (words.isEmpty()) {
			finish();
		} else {
			answerWord(words.get(0));
		}
	}

	public void answerWord(SessionWord sessionWord) {

		word.setText(sessionWord.getWord());

		View.OnClickListener answerListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isTranslationCorrect(sessionWord, answer.getText()
						.toString())) {
					words.remove(sessionWord);
					answer.getText()
							.clear();
					nextQuestion();
				}
			}
		};
		button.setOnClickListener(answerListener);
	}

	private boolean isTranslationCorrect(SessionWord word, String answer) {
		List<String> translations = word.getTranslations();
		for (String translation : translations) {
			if (answer.equals(translation)) {
				return true;
			}
		}
		return false;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			session.setVisibility(show ? View.GONE : View.VISIBLE);
			session.animate()
					.setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {

						@Override
						public void onAnimationEnd(Animator animation) {
							session.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});
			System.out.println("at this point progress bar should appear");
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate()
					.setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {

						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			session.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
