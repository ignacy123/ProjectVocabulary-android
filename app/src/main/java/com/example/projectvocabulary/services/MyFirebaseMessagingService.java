package com.example.projectvocabulary.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.projectvocabulary.R;
import com.example.projectvocabulary.RootActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

	private static final String TAG = "FCM Service";

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		String title = remoteMessage.getNotification()
				.getTitle();
		showNotifaction(title);
		// TODO: Handle FCM messages here.
		// If the application is in the foreground handle both data and notification messages here.
		// Also if you intend on generating your own notifications as a result of a received FCM
		// message, here is where that should be initiated.
		Log.d(TAG, "From: " + remoteMessage.getFrom());
		Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification()
				.getBody());
	}

	private void showNotifaction(String title) {
		Intent resultIntent = new Intent(this, RootActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		// The id of the channel.
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_priority_high_black_24dp)
				.setContentTitle("My notification")
				.setContentText(title)
				.setContentIntent(pendingIntent);
		// Creates an explicit intent for an Activity in your app

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your app to the Home screen.
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// mNotificationId is a unique integer your app uses to identify the
		// notification. For example, to cancel the notification, you can pass its ID
		// number to NotificationManager.cancel().
		mNotificationManager.notify(0, mBuilder.build());
	}
}
