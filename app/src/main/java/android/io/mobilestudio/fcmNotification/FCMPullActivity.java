package android.io.mobilestudio.fcmNotification;


import android.content.Intent;
import android.io.piso.fcmNotification.callbacks.IPullFCMCallback;
import android.io.piso.fcmNotification.managers.FCMPullNotificationManager;
import android.io.piso.fcmNotification.notification.NotificationBuilder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

public class FCMPullActivity extends AppCompatActivity implements IPullFCMCallback {
        TextView notification , data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcmpull);
        notification = findViewById(R.id.notification);
        data = findViewById(R.id.data);
        FCMPullNotificationManager manager = FCMPullNotificationManager.getInstance(this);
        manager.registerListener( this);
        Uri customSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_sound);
        Bundle bundle = new Bundle();
        bundle.putString("say","Mustafa");
        NotificationBuilder builder = new NotificationBuilder(this)
                .setContentIntent(new Intent(getApplicationContext(), FCMMainActivity.class))
                .setDefaultSound()
                .setNotificationIcon(R.drawable.iocn1024);

        manager.autoLaunchNotification(builder);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        if(message.getNotification() != null){
            String currnetText = notification.getText().toString();
            notification.setText(currnetText+"\n"
                                +"  body : "+ message.getNotification().getBody()+"\n"
                                +"  title : "+ message.getNotification().getTitle()+"\n");
        }
        if(message.getData()!=null){
           Set<String> sets =  message.getData().keySet();
           for(String s :sets){
               String currnetText = data.getText().toString();
               data.setText(currnetText+"\n  "+s+" : "+ message.getData().get(s));
           }
        }
    }

    @Override
    public void onDeviceRegistered(String tokenId) {
        Toast.makeText(this, "tokenId : "+tokenId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorHappened(String errorMessage) {
        Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT).show();
    }
}
