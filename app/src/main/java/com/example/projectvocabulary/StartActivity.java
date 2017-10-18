package com.example.projectvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.projectvocabulary.base.ServiceLocator;

public class StartActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ServiceLocator locator = MyApplication.getServiceLocator(getApplication());
		locator.getUserDAO()
				.fetch()
				.observe(this, user1 -> {

					if (user1 == null) {
						Intent intent = new Intent(StartActivity.this, LoginActivity.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent(StartActivity.this, RootActivity.class);
						startActivity(intent);

						setContentView(R.layout.activity_start);
					}
				});
	}
}
