package android.io.piso.fcmNotification.models;

import java.util.List;
import java.util.Map;


/**
 * Created by pisoo on 4/15/2018.
 */

public class Notification {

    private List<String> registration_ids;
    private String to;
    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    private Data notification;

    public Notification() {
        this.notification = new Data();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getNotification() {
        return notification;
    }

    public void setNotification(Data notification) {
        this.notification = notification;
    }

    public void setTitle(String title) {
        this.notification.setTitle(title);
    }

    public void setMessage(String message) {
        this.notification.setBody(message);
    }

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

}
