package android.io.piso.fcmNotification.services;

import android.io.piso.fcmNotification.managers.FCMPullNotificationManager;
import android.io.piso.fcmNotification.notification.NotificationBuilder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by pisoo on 4/15/2018.
 *
 */

public class ModuleFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FCMPullNotificationManager.getFcmCallback() != null) {
                    FCMPullNotificationManager.getFcmCallback().onDeviceRegistered(refreshedToken);
                }
                }


        }, 1000);
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }
}
