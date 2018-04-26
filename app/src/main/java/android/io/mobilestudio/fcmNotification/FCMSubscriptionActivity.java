package android.io.mobilestudio.fcmNotification;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.io.piso.fcmNotification.managers.FCMSubscriptionManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FCMSubscriptionActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    List<String> values;
    Button subscribe, unSubscribeAll;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcmsubscription);
        values = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_item);
        subscribe = (Button) findViewById(R.id.bt_subscribe);
        unSubscribeAll = (Button) findViewById(R.id.bt_unsubscribeAll);
        subscribe.setOnClickListener(this);
        unSubscribeAll.setOnClickListener(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FCMSubscriptionManager.getInstance(FCMSubscriptionActivity.this).unsubscribeFromTopic(values.get(i));
                values.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.bt_subscribe:
                launchAddExtraData();
                break;

            case R.id.bt_unsubscribeAll:
                UnSubscribeAll();
                values.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void launchAddExtraData() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FCMSubscriptionActivity.this);
        alertDialog.setMessage("Enter channel name");

        final EditText name = new EditText(FCMSubscriptionActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        name.setLayoutParams(lp);
        name.setHint("Name");
        alertDialog.setView(name);
        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LL.setWeightSum(6f);
        LL.setLayoutParams(LLParams);
        LL.addView(name);
        alertDialog.setView(LL);
        alertDialog.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!name.getText().toString().isEmpty()) {
                            values.add(name.getText().toString());
                            adapter.notifyDataSetChanged();
                            FCMSubscriptionManager.getInstance(FCMSubscriptionActivity.this).subscribeToTopic(name.getText().toString());
                        } else {
                            Toast.makeText(FCMSubscriptionActivity.this, "Please enter name ", Toast.LENGTH_SHORT).show();
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

    public void UnSubscribeAll() {
        for (String value : values) {
            FCMSubscriptionManager.getInstance(this).unsubscribeFromTopic(value);
        }
    }

    private boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size1", values.size());
        for (int i = 0; i < values.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status1_" + i, values.get(i));
        }
        mEdit1.apply();
        return mEdit1.commit();
    }

    private void loadArray() {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(this);
        values.clear();
        int size = mSharedPreference1.getInt("Status_size1", 0);

        for (int i = 0; i < size; i++) {
            values.add(mSharedPreference1.getString("Status1_" + i, null));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveArray();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadArray();
        adapter.notifyDataSetChanged();
    }
}
