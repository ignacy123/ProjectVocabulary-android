package com.example.projectvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.projectvocabulary.domain.user.SessionRequest;
import com.example.projectvocabulary.domain.user.User;
import com.example.projectvocabulary.network.LoginRequestDto;
import com.example.projectvocabulary.network.ProjectVocabularyApi;
import com.example.projectvocabulary.network.ProjectVocabularyApiImpl;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SessionActivity extends AppCompatActivity {


    @BindView(R.id.editText5)
    EditText answer;
    @BindView(R.id.textView)
    EditText word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        api = ProjectVocabularyApiImpl.getInstance();
		SessionRequest sessionRequest = new SessionRequest();
		sessionRequest.setSize(20);
    }
    ProjectVocabularyApi api;
	SessionRequest sessionRequest;



    public void getWords(View view){api.session(sessionRequest)
        .enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAG", response.body().toString()
                                );

                    } else {
                        new AlertDialog.Builder(SessionActivity.this).setMessage("BŁĄÐ")
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
