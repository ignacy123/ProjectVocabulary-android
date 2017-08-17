package com.example.projectvocabulary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.example.projectvocabulary.preferences.Preferences;
import com.example.projectvocabulary.sql.UserRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserDetailActivity extends AppCompatActivity {

	@BindView(R.id.textView3)
	TextView textview3;
	@BindView(R.id.textView5)
	TextView textview5;
	@BindView(R.id.textView7)
	TextView textview7;
	@BindView(R.id.editText6)
	TextView edittext6;
	@BindView(R.id.editText7)
	TextView edittext7;
	@BindView(R.id.editText8)
	TextView edittext8;
	@BindView(R.id.button4)
	Button button;

	ProjectVocabularyApi api;

	long userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		ButterKnife.bind(this);
		api = ProjectVocabularyApiImpl.getInstance();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		userId = sharedPref.getLong(Preferences.USER_ID, 0);
		User user = UserRepository.getInstance(this)
				.fetch();
		textview3.setText(user.getEmail());
		textview5.setText(user.getFirstName());
		textview7.setText(user.getLastName());

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

		api.update(userId, user)
				.enqueue(new Callback<User>() {

					@Override
					public void onResponse(Call<User> call, Response<User> response) {
						if (response.isSuccessful()) {
							Log.d("TAG", response.body()
									.getEmail());
						} else {
							new AlertDialog.Builder(UserDetailActivity.this).setMessage("BŁĄÐ")
									.show();
						}
						System.out.println(response.body());
						Timber.d("Connection successful %s", response);
					}

					@Override
					public void onFailure(Call<User> call, Throwable t) {
						Timber.d("Connection failed");
					}
				});

	}
}
