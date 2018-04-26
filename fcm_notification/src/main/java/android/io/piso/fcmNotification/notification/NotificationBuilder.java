package android.io.piso.fcmNotification.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

/**
 * Created by pisoo on 4/16/2018.
 */

public class NotificationBuilder {

    private Context context;
    private Intent intent ;

    public Intent getIntent() {
        return intent;
    }

    private NotificationCompat.Builder builder;

    public NotificationBuilder(Context context) {
        this.context = context;
        builder = new NotificationCompat.Builder(context, "FCM");
        builder.setAutoCancel(true);
    }

    public NotificationBuilder setNotificationTitle(String title) {
        this.builder.setContentTitle(title);
        return this;
    }

    public NotificationBuilder setNotificationMessage(String notificationMessage) {
        this.builder.setContentText(notificationMessage);
        return this;
    }

    public NotificationBuilder setAutoCancel(boolean cancel) {
        this.builder.setAutoCancel(cancel);
        return this;
    }

    public NotificationBuilder setContentIntent(Intent intent) {
        this.intent = intent ;
        PendingIntent pendingIntent = PendingIntent.getActivity(context,new Random().nextInt() /* Request code */, intent, 0);
        this.builder.setContentIntent(pendingIntent);
        return this;
    }

    public NotificationBuilder setNotificationIcon(int icon) {
        this.builder.setSmallIcon(icon);
        return this;
    }

    public NotificationBuilder setDefaultSound() {
        SharedPreferences myPrefs = context.getSharedPreferences("FCM", 0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        myPrefs.edit().putString("url",defaultSoundUri.toString()).apply();
        return this;
    }

    public NotificationBuilder setCustomSound(Uri uri) {
        SharedPreferences myPrefs = context.getSharedPreferences("FCM", 0);
        SharedPreferences.Editor myPrefsEdit = myPrefs.edit();
         myPrefsEdit.putString("url",uri.toString()).apply();
        return this;
    }

    public NotificationCompat.Builder getNotificationBuilder() {
        return this.builder;
    }

    public Context getContext() {
        return context;
    }

}
