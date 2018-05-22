package com.propheticapps.halloweenapplocktheme.Service;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.propheticapps.halloweenapplocktheme.R;

public class LockScreenService extends Service {

    DeviceAdminReceiver receiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Register for Lockscreen event intents
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        receiver = new LockScreenReceiver();
        registerReceiver(receiver, filter);
        startForeground();
        return START_STICKY;
    }
    // Run service in foreground so it is less likely to be killed by system
    private void startForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText("Running")
                .setContentIntent(null)
                .setOngoing(true)
                .build();
        startForeground(9999,notification);
    }
    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {

        KeyguardManager.KeyguardLock key;
        KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = km.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();

        //This is deprecated, but it is a simple way to disable the lockscreen in code
        key = km.newKeyguardLock("IN");

        key.disableKeyguard();

        //Start listening for the Screen On, Screen Off, and Boot completed actions
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        Log.i("Screen","....."+filter);

        //Set up a receiver to listen for the Intents in this Service
        receiver = new LockScreenReceiver();
        registerReceiver(receiver, filter);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

}
