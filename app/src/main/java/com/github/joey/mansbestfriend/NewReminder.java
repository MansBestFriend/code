package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class NewReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        setupUI(findViewById(R.id.reminderParent));
        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("1");

        final HandleDB helper = new HandleDB(getApplicationContext());

        final SQLiteDatabase db = helper.getWritableDatabase();

        Button enterReminder = (Button) findViewById(R.id.enterRemindBtn);

        enterReminder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Cursor c = db.rawQuery("SELECT Number FROM Reminders;", null);
                String numberStr = "";
                String nameStr = "";
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            numberStr = c.getString(c.getColumnIndex("Number"));
                            Log.e("e", numberStr);
                        } while (c.moveToNext());
                    }
                }
                c = db.rawQuery("SELECT Name FROM Profiles WHERE Id == " + profNum + ";", null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            nameStr = c.getString(c.getColumnIndex("Name"));
                        } while(c.moveToNext());
                    }
                }
                int num;
                if(numberStr.isEmpty()){
                    num = 1;
                } else {
                    num = Integer.parseInt(numberStr) + 1;
                }
                EditText title = (EditText) findViewById(R.id.reminderTitle);
                String titleStr = title.getText().toString();
                if(titleStr.isEmpty()){
                    titleStr = "Unknown";
                }
                EditText minutes = (EditText) findViewById(R.id.minutesText);
                int minStr = Integer.parseInt(minutes.getText().toString());
                EditText hours = (EditText) findViewById(R.id.hoursText);
                int hourStr = Integer.parseInt(hours.getText().toString());
                EditText days = (EditText) findViewById(R.id.daysText);
                int dayStr = Integer.parseInt(days.getText().toString());
                int totalMinutes = (minStr + (hourStr * 60) + (dayStr * 24 * 60));
                int totalSeconds = (totalMinutes * 60);
                ContentValues values = new ContentValues();
                values.put("Number", num);
                values.put("Frequency", totalMinutes);
                values.put("Title", titleStr);
                values.put("ProfileId", profNum);
                db.insert("Reminders", null, values);
                db.close();
                NotificationSender.scheduleAlarms(getApplicationContext(),nameStr, titleStr, totalSeconds);

                Intent remindMenu = new Intent(v.getContext(), ReminderList.class);

                Bundle b = new Bundle();
                b.putInt("1", profNum);
                remindMenu.putExtras(b);
                startActivityForResult(remindMenu, 0);
                finishActivity(1);
                finish();
            }
        });

        Button back = (Button)findViewById(R.id.newRemBack);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),ReminderList.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                i.putExtras(b);
                startActivityForResult(i,0);
                finishActivity(1);
                finish();
            }
        });

    }

    private void setNotification(int time){


        PendingIntent pendInt = PendingIntent.getBroadcast(this, 0, new Intent(this, NotificationSender.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar c = Calendar.getInstance();
        long tmpTime = 1000 * time;
        c.add(Calendar.SECOND,time);
        long futTime = c.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, futTime, tmpTime, pendInt);
    }


    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(NewReminder.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
