package android.io.mobilestudio.fcmNotification;

import android.content.DialogInterface;
import android.content.Intent;
import android.io.piso.fcmNotification.callbacks.IPushFCMCallback;
import android.io.piso.fcmNotification.managers.FCMPushNotificationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FCMPushActivity extends AppCompatActivity {
    Map<String, String> data = new HashMap<>();
    TextView extraDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addData);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddExtraData();
            }
        });
        extraDataTextView = findViewById(R.id.tv_extradata);
        final EditText title, message, topicEditText;
        topicEditText = findViewById(R.id.topicEditText);
        title = findViewById(R.id.titleEditText);
        message = findViewById(R.id.messageEditText);
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FCMPushNotificationManager maneger = FCMPushNotificationManager.getInstance(getString(R.string.serverKey));
                maneger.setPushFCMCallback(new IPushFCMCallback() {
                    @Override
                    public void onPushNotificationSuccess() {
                        Toast.makeText(FCMPushActivity.this, "Pushed successfully ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(FCMPushActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
                List<String> tokens = new ArrayList<>();
                tokens.add("fA9YhUO6QZI:APA91bGMhtNjqWNbmUDNi9YfS7b-JckqUC1Or-RoQwiO-HBaKA2NLvHypxl0hx6nMFfHUZ-wytuE6NpC_cBufus3V9FEW8aoVHW7AEiJO19SZbl0n9-ykPx_-XzZ7njazRqdYEzOayla");
                tokens.add("fbphF3mJZJM:APA91bGm4-gG5V48WEPtunLXqEksfcFE24mPj9BvoeDOmalX5bNIwpjJqD-KrYVNXOi2SrCgj2T4S1kLH0yy8190gW8Fl-ahEX3fMpqQ8B9qRhnk9Bp0E1_gGn5AeUZz2q65XbwRNGHp");

                tokens.add("ej5nCzrzLkw:APA91bHsRX6OEcSsio2KUFyxa6YtA8-X8gYArVIAJqp7V5m_4CUchntKppWD0RVkT7EhcIQQr2EETTu3-7NGQlfVTLXTZi2hQ2L9TRT6hf31IuoFCgcKf59P1gB7J8FO7Q5tJqzTxDFC");
                //ej5nCzrzLkw:APA91bHsRX6OEcSsio2KUFyxa6YtA8-X8gYArVIAJqp7V5m_4CUchntKppWD0RVkT7EhcIQQr2EETTu3-7NGQlfVTLXTZi2hQ2L9TRT6hf31IuoFCgcKf59P1gB7J8FO7Q5tJqzTxDFC
                List<String> topics = new ArrayList<>();
                topics.add("all");
                topics.add("virtual");
                startActivity(new Intent(FCMPushActivity.this, FCMPullActivity.class));
                maneger.notifyByTopic(title.getText().toString(), message.getText().toString(), topicEditText.getText().toString()  )
                        .addExtraData(data).send();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchAddExtraData() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FCMPushActivity.this);
        alertDialog.setMessage("Enter extra data");

        final EditText key = new EditText(FCMPushActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        key.setLayoutParams(lp);
        key.setHint("Enter Key");
        alertDialog.setView(key);
        final EditText value = new EditText(FCMPushActivity.this);
        value.setLayoutParams(lp);
        value.setHint("Enter value");
        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LL.setWeightSum(6f);
        LL.setLayoutParams(LLParams);
        LL.addView(key);
        LL.addView(value);
        alertDialog.setView(LL);
        alertDialog.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!key.getText().toString().isEmpty() && !value.getText().toString().isEmpty()) {
                            String keystr = key.getText().toString();
                            String valuestr = value.getText().toString();

                            data.put(keystr, valuestr);
                            String currentExtraData = extraDataTextView.getText().toString();
                            extraDataTextView.setText(currentExtraData + "\n" + keystr + " : " + valuestr);

                            Toast.makeText(FCMPushActivity.this, keystr + " " + valuestr, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FCMPushActivity.this, "Enter key and value ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }
}
