package com.example.projectvocabulary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.projectvocabulary.constants.Broadcasts;

/**
 * Created by ignacy on 22.08.17.
 */

public class BaseActivity extends AppCompatActivity {

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			CharSequence text;
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				text = getString(R.string.conn_failed);

			} else {
				text = getString(R.string.not_connected);
			}
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
		manager.registerReceiver(receiver, new IntentFilter(Broadcasts.NETWORK_FAILURE));
		manager.registerReceiver(logOutReceiver, new IntentFilter(Broadcasts.LOGOUT));
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
		manager.unregisterReceiver(receiver);
		manager.unregisterReceiver(logOutReceiver);

	}

	BroadcastReceiver logOutReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			CharSequence text = getString(R.string.cred_changed);
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			Intent intentTwo = new Intent(context, LoginActivity.class);
			startActivity(intentTwo);
		}
	};

}
