package com.github.joey.mansbestfriend;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Andrew on 4/26/2016.
 */
public class NotificationSender extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

//        NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
//        Notification.Builder build = new Notification.Builder(context);
//        build.setContentTitle("Reminder");
//        build.setContentText("Text");
//        build.setSmallIcon(R.drawable.mansbestfriendicon);
//        Notification notif = build.build();
//
//        PendingIntent content = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
//        manager.notify(1,notif);
//        Log.e("e", "notify");
    }

    static void scheduleAlarms(Context c,String name, String title, int time){

        AlarmManager mgr = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(c,BackgroundService.class);
        Bundle b = new Bundle();
        b.putString("name",name);
        b.putString("title",title);
        b.putInt("time",time);
        i.putExtras(b);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, time);
        long tmpTime = 1000 * time;
        PendingIntent p = PendingIntent.getService(c,0,i,0);
        mgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), tmpTime, p);

//        PendingIntent pendInt = PendingIntent.getBroadcast(this,0,new Intent(this,NotificationSender.class),PendingIntent.FLAG_UPDATE_CURRENT);
//        Calendar c = Calendar.getInstance();
//        long tmpTime = 1000 * time;
//        c.add(Calendar.SECOND,time);
//        long futTime = c.getTimeInMillis();
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,futTime,tmpTime,pendInt);
    }
}
