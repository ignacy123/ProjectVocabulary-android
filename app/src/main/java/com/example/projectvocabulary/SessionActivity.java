package com.example.projectvocabulary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.SessionWord;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.LoginRequestDto;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.example.projectvocabulary.preferences.Preferences;
import com.example.projectvocabulary.sql.UserRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SessionActivity extends AppCompatActivity {

	@BindView(R.id.editText5)
	EditText answer;
	@BindView(R.id.textView)
	TextView word;
	@BindView(R.id.button3)
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session);
		api = ProjectVocabularyApiImpl.getInstance();
		Timber.plant(new Timber.DebugTree());
		ButterKnife.bind(this);
		getWordsAndStartSession();
		System.out.println(words);

	}

	ProjectVocabularyApi api;
	List<SessionWord> words;
	String sesAnswer;
	View.OnClickListener answerListener;

	public void getWordsAndStartSession() {

		SessionRequest sessionRequest = new SessionRequest();
		sessionRequest.setSize(20);

		api.session(sessionRequest)
				.enqueue(new Callback<List<SessionWord>>() {

					@Override
					public void onResponse(Call<List<SessionWord>> call, Response<List<SessionWord>> response) {
						if (response.isSuccessful()) {
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
												}
												System.out.println(user);
												Timber.d("Connection successful %s", response);
											}

											@Override
											public void onFailure(Call<User> call, Throwable t) {
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

}
