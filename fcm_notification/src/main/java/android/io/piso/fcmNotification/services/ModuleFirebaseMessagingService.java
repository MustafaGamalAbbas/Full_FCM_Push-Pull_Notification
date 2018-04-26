package android.io.piso.fcmNotification.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.io.piso.fcmNotification.managers.FCMPullNotificationManager;
import android.io.piso.fcmNotification.notification.NotificationBuilder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by pisoo on 4/15/2018.
 */

public class ModuleFirebaseMessagingService extends FirebaseMessagingService {
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // ...
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FCMPullNotificationManager.getFcmCallback() != null) {
                    FCMPullNotificationManager.getFcmCallback().onMessageReceived(remoteMessage);
                }
                if(remoteMessage.getNotification()!=null){
                    if(FCMPullNotificationManager.getBuilder()!=null){

                        NotificationBuilder builder = FCMPullNotificationManager.getBuilder();
                        builder.setNotificationMessage(remoteMessage.getNotification().getBody());
                        builder.setNotificationTitle(remoteMessage.getNotification().getTitle());
                        if(builder.getIntent()!= null){
                            Bundle bundle = getBundlefromData(remoteMessage);
                            Intent intent = builder.getIntent();
                            intent.putExtras(bundle);
                            builder.setContentIntent(intent);
                        }
                        if (FCMPullNotificationManager.getSendNotification() != null && FCMPullNotificationManager.getBuilder() != null) {
                            FCMPullNotificationManager.getSendNotification().sendNotification(
                                    builder
                            );
                        }
                    }

                }


            }
        }, 1000);
//        notifyByTopic(remoteMessage.getMessageType(),new Intent(getApplicationContext(),));

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
//                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See notifyByTopic method below.
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }
    private Bundle getBundlefromData(RemoteMessage message){
        Bundle bundle =new Bundle();
        if(message.getData()!=null){
            Set<String> sets =  message.getData().keySet();
            for(String s :sets){
                bundle.putString(s,message.getData().get(s));
            }
        }
        return bundle;
    }
}
