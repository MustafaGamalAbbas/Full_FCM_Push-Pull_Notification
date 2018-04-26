package android.io.piso.fcmNotification.models;


/**
 * Created by pisoo on 4/15/2018.
 */

public class Data {

    private String body;
    private String title;
    private String sound = "default" ;
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSound() {
        return sound;
    }
}
