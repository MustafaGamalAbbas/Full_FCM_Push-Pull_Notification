package android.io.piso.fcmNotification.callbacks;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by pisoo on 4/16/2018.
 *
 *  delegation channel for pulling messages from firebase
 */

public interface IPullFCMCallback {
    /**
     * retrieve remote message that triggered from firebase
     * @param message
     */
    void onMessageReceived(RemoteMessage message);

    /**
     * retrieve Token id "unique device id "
     * @param tokenId
     */
    void onDeviceRegistered(String tokenId);

    /**
     * delegate any error happened
     * @param errorMessage
     */
    void onErrorHappened(String errorMessage);
}
