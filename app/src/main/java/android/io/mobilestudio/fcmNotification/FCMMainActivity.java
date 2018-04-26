package android.io.mobilestudio.fcmNotification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FCMMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.manageSub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FCMMainActivity.this, FCMSubscriptionActivity.class));
            }
        });
        findViewById(R.id.startPush).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FCMMainActivity.this, FCMPushActivity.class));
            }
        });
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            String sender = bundle.getString("name");
            int c =0 ;
        }
    }
}
