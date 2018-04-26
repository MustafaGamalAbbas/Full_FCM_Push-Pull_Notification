package android.io.piso.fcmNotification.notification;

import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by pisoo on 4/16/2018.
 */

public class SampleNotification implements ISendNotification {
    private Context context;

    public SampleNotification(Context context) {
        this.context = context;
    }

    @Override
    public void sendNotification(NotificationBuilder builder) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(78867 /* ID of notification */, builder.getNotificationBuilder().build());
    }


}
