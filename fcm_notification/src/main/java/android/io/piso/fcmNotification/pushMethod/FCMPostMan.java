package android.io.piso.fcmNotification.pushMethod;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by pisoo on 4/17/2018.
 */

public class FCMPostMan implements IPostServer {
    private OkHttpClient mClient = new OkHttpClient();
    private String serverKey;

    public FCMPostMan(String serverKe) {
        this.serverKey = serverKe;
    }

    @Override
    public String post(String body) throws IOException {

        final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, body);

        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(requestBody)
                .addHeader("Authorization", "key=" +serverKey)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
