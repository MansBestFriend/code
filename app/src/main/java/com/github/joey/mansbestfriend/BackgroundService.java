package com.github.joey.mansbestfriend;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Andrew on 4/26/2016.
 */
public class BackgroundService extends Service {

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        if(intent.getExtras() != null) {
            Bundle b = intent.getExtras();
            if(b != null) {
                Log.d(getClass().getSimpleName(), "I ran!");
                if(b.get("time") != null) {
                    String strTime = b.get("time").toString();
                    String strName = b.get("name").toString();
                    String strTitle = b.get("title").toString();
                    Intent i = new Intent(this, NotificationSender.class);
                    PendingIntent pi = PendingIntent.getActivity(this, 1, i, 0);
                    NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification.Builder build = new Notification.Builder(this);
                    build.setContentTitle(strTitle);
                    build.setContentText(strName);
                    build.setSmallIcon(R.drawable.mansbestfriendicon);
                    build.setContentIntent(pi);
                    build.setAutoCancel(true);
                    Notification notif = build.build();
                    manager.notify(1, notif);
                }

            }
        }


        return 0;
    }








}
