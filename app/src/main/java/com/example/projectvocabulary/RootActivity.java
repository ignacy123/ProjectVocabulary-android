package com.example.projectvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RootActivity extends AppCompatActivity {

	@BindView(R.id.fabtoolbar_fab)
	FloatingActionButton fab;
	@BindView(R.id.fabtoolbar)
	FABToolbarLayout fabtoolbar;
	@BindView(R.id.one)
	ImageView one;
	@BindView(R.id.two)
	ImageView two;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);
		ButterKnife.bind(this);
		fab.setOnClickListener(v -> fabtoolbar.show());
		api = ProjectVocabularyApiImpl.getInstance();
		Timber.plant(new Timber.DebugTree());

		View.OnClickListener oneClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				api.logout();
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		};
		one.setOnClickListener(oneClickListener);

		View.OnClickListener twoClickListenener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
				startActivity(intent);
			}
		};
		two.setOnClickListener(twoClickListenener);

	}

	ProjectVocabularyApi api;

	public void goToSession(View view) {
		Intent intent = new Intent(this, SessionActivity.class);
		startActivity(intent);
	}
}
