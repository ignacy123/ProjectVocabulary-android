package com.example.projectvocabulary;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
							new AlertDialog.Builder(SessionActivity.this).setMessage("BŁĄÐ")
									.show();
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
