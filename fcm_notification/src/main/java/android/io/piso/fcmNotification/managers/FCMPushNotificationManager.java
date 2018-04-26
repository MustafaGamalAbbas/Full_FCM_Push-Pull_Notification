package android.io.piso.fcmNotification.managers;

import android.annotation.SuppressLint;
import android.io.piso.fcmNotification.callbacks.IPushFCMCallback;
import android.io.piso.fcmNotification.models.Notification;
import android.io.piso.fcmNotification.pushMethod.FCMPostMan;
import android.io.piso.fcmNotification.pushMethod.IPostServer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pisoo on 4/15/2018.
 * Singleton push manager that manage all push notification to someone, ones, group or groups of people
 */

public class FCMPushNotificationManager {
    private final String FIREBASE_GROUP_SERVER_NODE = "/topics/";
    private static Notification notification;
    private static IPostServer postServer;
    private IPushFCMCallback pushFCMCallback;
    private List<String> topics;
    private static final FCMPushNotificationManager ourInstance = new FCMPushNotificationManager();

    public static FCMPushNotificationManager getInstance(String serverKey) {
        postServer = new FCMPostMan(serverKey);
        notification = new Notification();
        return ourInstance;
    }

    private FCMPushNotificationManager() {
    }

    /**
     * Allow to you to select group of people to notify them
     *
     * @param title
     * @param message
     * @param topic
     * @return
     */
    public FCMPushNotificationManager notifyByTopic(String title, String message, String topic) {
        topics = new ArrayList<>();
        topics.add(topic);
        notification.setTitle(title);
        notification.setMessage(message);

        return this;
    }

    /**
     * Allow to you to select groups of people to notify them
     *
     * @param title
     * @param message
     * @param topics
     * @return
     */
    public FCMPushNotificationManager notifyByTopics(String title, String message, List<String> topics) {
        this.topics = new ArrayList<>();
        this.topics.addAll(topics);

        notification.setTitle(title);
        notification.setMessage(message);

        return this;
    }

    /**
     * Allow to you to select someone to notify him/her
     *
     * @param title
     * @param message
     * @param token
     * @return
     */
    public FCMPushNotificationManager notifyByToken(String title, String message, String token) {

        List<String> tokens = new ArrayList<>();
        tokens.add(token);
        notification.setRegistration_ids(tokens);
        notification.setTitle(title);
        notification.setMessage(message);
        return this;

    }

    /**
     * Allow to you to select someones notify them
     *
     * @param title
     * @param message
     * @param tokens
     * @return
     */
    public FCMPushNotificationManager notifyByTokens(String title, String message, List<String> tokens) {
        notification.setRegistration_ids(tokens);
        notification.setTitle(title);
        notification.setMessage(message);
        return this;
    }

    /**
     * Allow to you to put extra data with notification to be a useful data when the target person received it
     *
     * @param data
     * @return
     */
    public FCMPushNotificationManager addExtraData(Map<String, String> data) {
        notification.setData(data);
        return this;
    }

    /***
     * Send notification action function
     */
    public void send() {

        if (topics != null && topics.size() > 0) {
            for (String topic : topics) {
                notification.setTo(FIREBASE_GROUP_SERVER_NODE + topic);
                sendToServer();
            }
        } else {
            sendToServer();
        }
    }

    private void sendToServer() {
        Gson gson = new GsonBuilder().create();
        String payload = gson.toJson(notification);
        sendMessage(payload);
    }


    @SuppressLint("StaticFieldLeak")
    private void sendMessage(final String payload/*final JSONArray recipients, final String title, final String body, final String icon, final String message*/) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String result = postServer.post(payload);
                    callMainThreadToSuccess();
                    Log.d("FCM_Module", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (pushFCMCallback != null) {
                        callMainThreadToFail(ex.getMessage());
                    }
                }
                return null;
            }
        }.execute();
    }

    /**
     * Setting service callback to delegate anything happened
     *
     * @param pushFCMCallback
     */
    public void setPushFCMCallback(IPushFCMCallback pushFCMCallback) {
        this.pushFCMCallback = pushFCMCallback;
    }

    ///////////////helper function///////////////////////
    void callMainThreadToSuccess() {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pushFCMCallback != null) {
                    pushFCMCallback.onPushNotificationSuccess();
                }
            }
        }, 1000);
    }

    void callMainThreadToFail(final String errorMessage) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pushFCMCallback != null) {
                    pushFCMCallback.onError(errorMessage);
                }
            }
        }, 1000);
    }
}
