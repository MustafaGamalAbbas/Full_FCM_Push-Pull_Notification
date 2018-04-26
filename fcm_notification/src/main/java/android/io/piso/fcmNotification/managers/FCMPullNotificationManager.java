package android.io.piso.fcmNotification.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.io.piso.fcmNotification.callbacks.IPullFCMCallback;
import android.io.piso.fcmNotification.notification.ISendNotification;
import android.io.piso.fcmNotification.notification.NotificationBuilder;
import android.io.piso.fcmNotification.notification.SampleNotification;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

/**
 * Created by pisoo on 4/16/2018.
 * <p>
 * Singleton pull manager that manage all flow of data when received from firebase
 */

public class FCMPullNotificationManager {

    private static final FCMPullNotificationManager ourInstance = new FCMPullNotificationManager();
    private static IPullFCMCallback fcmCallback;
    private static ISendNotification sendNotification;
    private static NotificationBuilder builder;
    private static Context context;

    public static FCMPullNotificationManager getInstance(Context context) {
        FCMPullNotificationManager.context = context;
        SharedPreferences myPrefs = FCMPullNotificationManager.context.getSharedPreferences("FCM", 0);
        myPrefs.edit().putBoolean("autolaunch", false).apply();
        return ourInstance;
    }

    private FCMPullNotificationManager() {
    }

    public static NotificationBuilder getBuilder() {
        return builder;
    }

    /**
     * Register callback to be delegated when retrieve messages from firebase
     *
     * @param fcmCallback
     */
    public void registerListener(@NonNull IPullFCMCallback fcmCallback) {
        FCMPullNotificationManager.fcmCallback = fcmCallback;
    }

    /***
     *  Just tell the module to release notification when receive massage from firebase
     * @param builder
     */
    public void autoLaunchNotification(@NonNull NotificationBuilder builder) {
        FCMPullNotificationManager.sendNotification = new SampleNotification(builder.getContext());
        FCMPullNotificationManager.builder = builder;
        SharedPreferences myPrefs = builder.getContext().getSharedPreferences("FCM", 0);
        myPrefs.edit().putBoolean("autolaunch", true).apply();
    }

    /**
     * @return IPullFCMCallback
     */
    public static IPullFCMCallback getFcmCallback() {
        return fcmCallback;
    }

    /**
     * Get the class that care to sent notification with somehow to push notification to current device
     *
     * @return
     */
    public static ISendNotification getSendNotification() {
        return sendNotification;
    }


}
