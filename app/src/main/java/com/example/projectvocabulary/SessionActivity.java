package com.example.projectvocabulary;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.SessionWord;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SessionActivity extends AppCompatActivity {

	@BindView(R.id.editText5)
	EditText answer;
	@BindView(R.id.textView)
	TextView word;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session);
		api = ProjectVocabularyApiImpl.getInstance();
		Timber.plant(new Timber.DebugTree());
		getWords();
		System.out.println(words);

	}

	ProjectVocabularyApi api;
	List<SessionWord> words;
	String sesAnswer;
	View.OnClickListener answerListener;

	public void getWords() {

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
							session(words);

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

	public void session(List<SessionWord> words) {
		while (words.size() > 0) {
			for (SessionWord sesWord : words) {
				word.setText(sesWord.getWord()
						.toString());
				answerListener = new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						sesAnswer = answer.getText()
								.toString();
						if (sesAnswer.equals(sesWord.getWord())) {
							words.remove(sesWord);
						}
					}
				};
			}
		}
	}

}
