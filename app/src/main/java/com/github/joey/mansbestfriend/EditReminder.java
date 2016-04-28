package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class EditReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        setupUI(findViewById(R.id.editRemindParent));
        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("num");
        final String strTitle = b.getString("title");
        HandleDB helper = new HandleDB(getApplicationContext());

        final SQLiteDatabase db = helper.getWritableDatabase();
        final int id;
        final String curTitle;
        int tmpId = 0;
        Cursor c = db.rawQuery("SELECT * FROM Reminders WHERE ProfileId == " + profNum + " and Title == '" +strTitle + "';", null);
        int minutes = 0;
        String tmpMinutes = "";
        String tmpTitle = "";
        if(c != null){
            if(c.moveToFirst()){
                do{
                    tmpId = Integer.parseInt(c.getString(c.getColumnIndex("Number")));
                    tmpTitle = c.getString(c.getColumnIndex("Title"));
                    tmpMinutes = c.getString(c.getColumnIndex("Frequency"));
                }while(c.moveToNext());
            }
        }
        id = tmpId;
        curTitle = tmpTitle;
        if(!tmpMinutes.isEmpty()){
            minutes = Integer.parseInt(tmpMinutes);
        }
        Log.e("e", String.valueOf(minutes));
        int hours = minutes/60;
        minutes -= hours * 60;


        EditText oldMin = (EditText)findViewById(R.id.minField);
        EditText oldHour = (EditText)findViewById(R.id.hourField);
        EditText dayField = (EditText)findViewById(R.id.dayField);
        oldMin.setText(String.valueOf(minutes), TextView.BufferType.EDITABLE);
        oldHour.setText(String.valueOf(hours), TextView.BufferType.EDITABLE);
        dayField.setText("0", TextView.BufferType.EDITABLE);


        Button updateRem = (Button)findViewById(R.id.updateRmBtn);
        updateRem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                stopService(new Intent(EditReminder.this,BackgroundService.class));
                EditText newMin = (EditText)findViewById(R.id.editMin);
                EditText newHour = (EditText)findViewById(R.id.editHr);
                EditText newDay = (EditText)findViewById(R.id.editDay);
                String minStr = newMin.getText().toString();
                String hourStr = newHour.getText().toString();
                String dayStr = newDay.getText().toString();
                int intMin = Integer.parseInt(minStr);
                int intHour = Integer.parseInt(hourStr);
                int intDay = Integer.parseInt(dayStr);
                int newFreq = intMin + (60*intHour) + (24*60*intDay);
                ContentValues data = new ContentValues();
                data.put("Title",curTitle);
                data.put("Frequency", newFreq);
                data.put("ProfileId", profNum);
                db.update("Reminders", data, "Number=" + id, null);
                db.close();
                NotificationSender.unscheduleAlarm(getApplicationContext(),String.valueOf(profNum),curTitle,newFreq);
                HashMap<String,ArrayList<String>> idToDat = new HashMap<String,ArrayList<String>>();
                ArrayList<String> profileList = new ArrayList<String>();
                HandleDB helper2 = new HandleDB(getApplicationContext());

                SQLiteDatabase db2 = helper2.getReadableDatabase();
                Cursor u = db2.rawQuery("SELECT * FROM REMINDERS;",null);
                if(u != null){
                    if(u.moveToFirst()){
                        do {
                            String curId = u.getString(u.getColumnIndex("ProfileId"));
                            ArrayList<String> cur;
                            if (idToDat.containsKey(curId)) {
                                cur = idToDat.get(curId);

                            } else {
                                cur = new ArrayList<String>();
                            }
                            String cTitle = u.getString(u.getColumnIndex("Title"));
                            String cFreq = u.getString(u.getColumnIndex("Frequency"));
                            cur.add(cTitle);
                            cur.add(cFreq);
                            idToDat.put(curId, cur);
                        } while(u.moveToNext());
                    }
                }
                HashMap<String,ArrayList<String>> nameToDat = new HashMap<String,ArrayList<String>>();
                Set<String> keys = idToDat.keySet();
                for(String s : keys){
                    u = db2.rawQuery("SELECT Name FROM Profiles WHERE Id =="+s+";",null);
                    if(u != null){
                        if(u.moveToFirst()){

                            String name = u.getString(u.getColumnIndex("Name"));
                            nameToDat.put(name,idToDat.get(s));
                        }
                    }
                }

                Set<String> nameKeys = nameToDat.keySet();
                for(String s : nameKeys){
                    ArrayList<String> curRems = nameToDat.get(s);
                    for(int i=0; i<curRems.size(); i+=2){
                        String curTitle = curRems.get(i);
                        String curFreq = curRems.get(i+1);
                        int timeInt = Integer.parseInt(curFreq);
                        NotificationSender.scheduleAlarms(getApplicationContext(),s,curTitle,timeInt);

                    }
                }

                db2.close();
                Intent remindMenu = new Intent(v.getContext(),ReminderList.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                remindMenu.putExtras(b);
                startActivityForResult(remindMenu, 0);
                finishActivity(1);
                finish();

            }
        });

        Button delRemindBtn = (Button)findViewById(R.id.delRemindBtn);
        delRemindBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                db.delete("Reminders","Number="+id,null);
                NotificationSender.unscheduleAlarm(getApplicationContext(), String.valueOf(profNum), curTitle, 0);
                Intent remindMenu = new Intent(v.getContext(),ReminderList.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                remindMenu.putExtras(b);
                startActivityForResult(remindMenu, 0);
                finishActivity(1);
                finish();
            }

        });

        Button back = (Button)findViewById(R.id.editBack);
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

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(EditReminder.this);
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
