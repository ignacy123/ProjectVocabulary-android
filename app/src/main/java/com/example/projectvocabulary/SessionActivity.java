package com.example.projectvocabulary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.SessionWord;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;

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

						}
						System.out.println(response.body());
						Timber.d("Connection successful %s", response);
					}

					@Override
					public void onFailure(Call<List<SessionWord>> call, Throwable t) {
						Timber.d("Connection failed");
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
