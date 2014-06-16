package sk.rdy.legi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import sk.rdy.legi.auth.LegiAccountAuthenticator;

/**
 * Created by rdy on 10.7.2013.
 */
public class LegiAuthService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        LegiAccountAuthenticator authenticator = new LegiAccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}
