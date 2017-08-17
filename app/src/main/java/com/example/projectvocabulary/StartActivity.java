package com.example.projectvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.sql.UserRepository;

public class StartActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		User user = UserRepository.getInstance(this)
				.fetch();
		if (user == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, RootActivity.class);
			startActivity(intent);

		}
		//		setContentView(R.layout.activity_start);
	}
}
