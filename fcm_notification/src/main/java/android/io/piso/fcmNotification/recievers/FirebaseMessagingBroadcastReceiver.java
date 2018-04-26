package android.io.piso.fcmNotification.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.io.piso.fcmNotification.services.NotificationSoundService;

/**
 * Created by pisoo on 1/24/2018.
 */

public class FirebaseMessagingBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

       Intent i = new Intent(context, NotificationSoundService.class);
       context.startService(i);
    }

}
