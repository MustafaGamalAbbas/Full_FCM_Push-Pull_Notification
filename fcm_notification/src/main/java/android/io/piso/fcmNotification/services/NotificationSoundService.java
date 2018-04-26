package android.io.piso.fcmNotification.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by pisoo on 2/10/2018.
 *
 */

public class NotificationSoundService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer mp;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences myPrefs = this.getSharedPreferences("FCM", 0);
         if(myPrefs.getBoolean("autolaunch",false))
         {
             Uri soundURI = Uri.parse(myPrefs.getString("url", "none"));
             if (!soundURI.toString().equals("none")) {
                 mp = MediaPlayer.create(this, soundURI);
                 mp.start();
                 mp.setOnCompletionListener(this);
             }
         }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }
}
