package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ReminderList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("1");

        HandleDB helper = new HandleDB(getApplicationContext());


        Log.e("db",getDatabasePath("mansbestfriend.db").toString());

        final SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT Title FROM Reminders WHERE ProfileId == " + profNum + ";", null);

        String title = "";

        final ListView remindList = (ListView) findViewById(R.id.remindersList);
        ArrayList<String> listItems = new ArrayList<String>();

        if(c != null){
            if(c.moveToFirst()){
                do{
                    title = c.getString(c.getColumnIndex("Title"));
                    listItems.add(title);

                } while(c.moveToNext());
            }
        }
        else{
            Log.e("db", "c is null");
        }



        if(!title.isEmpty()){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,listItems
            );
            remindList.setAdapter(arrayAdapter);
        } else {
            Log.e("db","title is empty");
        }

        remindList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long i2) {

                Intent edit = new Intent(v.getContext(), EditReminder.class);
                Bundle b = new Bundle();
                b.putInt("num", profNum);
                String title = remindList.getItemAtPosition(pos).toString();
                b.putString("title", title);
                edit.putExtras(b);
                startActivity(edit);
                finishActivity(1);
            }
        });
        Button newRem = (Button) findViewById(R.id.addNewReminderButton);






        newRem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newReminder = new Intent(v.getContext(), NewReminder.class);
                Bundle b = new Bundle();
                b.putInt("1", profNum);
                newReminder.putExtras(b);
                startActivity(newReminder);
                finishActivity(1);
            }
        });

        Button testDel = (Button)findViewById(R.id.testBtn);
        testDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.delete("Reminders",null,null);
                NotificationSender.unscheduleAlarm(getApplicationContext(), "Spot", "Title", 60);
                Intent refresh = new Intent(v.getContext(),ReminderList.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                refresh.putExtras(b);
                startActivity(refresh);
                finishActivity(1);
                finish();

            }
        });

        Button back = (Button)findViewById(R.id.remBack);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),ProfileMain.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                i.putExtras(b);
                startActivity(i);
                finishActivity(1);
                finish();
            }
        });

    }

}
