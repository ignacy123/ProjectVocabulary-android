package com.example.projectvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;

import butterknife.BindView;
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

	ProjectVocabularyApi api;

	long userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		api = ProjectVocabularyApiImpl.getInstance();
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		userId = sharedPref.getLong("userId", 0);
		api.users(userId)
				.enqueue(new Callback<User>() {

					@Override
					public void onResponse(Call<User> call, Response<User> response) {
						if (response.isSuccessful()) {
							Log.d("TAG", response.body()
									.getEmail());
							textview3.setText(response.body()
									.getEmail());
							textview5.setText(response.body()
									.getFirstName());
							textview7.setText(response.body()
									.getLastName());

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
