package com.example.projectvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import butterknife.BindView;

public class RootActivity extends AppCompatActivity {

//	@BindView(R.id.fabtoolbar_toolbar)
//	Toolbar toolbar;
//	@BindView(R.id.fabtoolbar_fab)
//	FloatingActionButton fab;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);
	}

	public void goToSession(View view){
		Intent intent = new Intent(this, SessionActivity.class);
		startActivity(intent);
	}
}
