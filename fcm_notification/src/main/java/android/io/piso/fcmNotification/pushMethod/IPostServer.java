package android.io.piso.fcmNotification.pushMethod;

import java.io.IOException;

/**
 * Created by pisoo on 4/17/2018.
 */

public interface IPostServer {
    String post(String body) throws IOException;
}
