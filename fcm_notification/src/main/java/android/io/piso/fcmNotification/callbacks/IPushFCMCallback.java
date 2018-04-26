package android.io.piso.fcmNotification.callbacks;

/**
 * Created by pisoo on 4/17/2018.
 *  delegation channel for push notification to firebase
 */

public interface IPushFCMCallback {
    /**
     * on push done successfully
     */
    void onPushNotificationSuccess();

    /**
     * on error happened
     * @param errorMessage
     */
    void onError(String errorMessage);
}
