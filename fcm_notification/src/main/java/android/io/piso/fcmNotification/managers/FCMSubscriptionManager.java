package android.io.piso.fcmNotification.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisoo on 4/17/2018.
 * Firebase subscription manager that you can subscribe to any channel
 */

public class FCMSubscriptionManager {

    private static List<String> topics;
    private static Context context;
    private static final FCMSubscriptionManager ourInstance = new FCMSubscriptionManager();

    public static FCMSubscriptionManager getInstance(Context context) {
        FCMSubscriptionManager.context = context;
        return ourInstance;
    }

    private FCMSubscriptionManager() {
        topics = new ArrayList<>();
    }

    /**
     * subscribe to a channel
     * @param topic
     */
    public  void subscribeToTopic(String topic) {
        loadArray();
        topics.add(topic);
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
        saveArray();
    }

    /**
     * unsubscribe to a channel
     * @param topic
     */
    public void unsubscribeFromTopic(String topic) {
        loadArray();
        topics.remove(topic);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
        saveArray();
    }

    /**
     * unsubscribe to all channels
     */
    public  void unsubscribeAll() {
        loadArray();
        for (String topic : topics)
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
        topics = new ArrayList<>();
        saveArray();
    }

    /**
     * subscribe to group of channels
     * @param topics_
     */
    public  void subscribeListOfTopics(List<String> topics_) {
        loadArray();
        for (String topic : topics)
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
        topics.addAll(topics_);
        saveArray();
    }
//////helper functions //////////////////////
    private  boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", topics.size());
        for (int i = 0; i < topics.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, topics.get(i));
        }
        mEdit1.apply();
        return mEdit1.commit();
    }

    private  void loadArray() {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(context);
        topics.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            topics.add(mSharedPreference1.getString("Status_" + i, null));
        }

    }
}
