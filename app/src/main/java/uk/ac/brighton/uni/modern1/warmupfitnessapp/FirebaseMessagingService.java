package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends Service {
    public FirebaseMessagingService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
       // Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Intent changeActivity = new Intent(this, CountdownTimer.class);
            startActivity(changeActivity);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
           // Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
