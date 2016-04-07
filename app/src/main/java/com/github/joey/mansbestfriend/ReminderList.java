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

        try {
            helper.createDataBase();
        } catch(Exception e){
            Log.e("e","Failed to create database");
        }

        Log.e("db",getDatabasePath("mansbestfriend.db").toString());

        SQLiteDatabase db = this.openOrCreateDatabase(getDatabasePath("mansbestfriend.db").toString(), MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT Title FROM Reminders WHERE ProfileId == " + profNum + ";", null);

        String title = "";

        if(c != null){
            if(c.moveToFirst()){
                do{
                    title = c.getString(c.getColumnIndex("Title"));

                } while(c.moveToNext());
            }
        }
        else{
            Log.e("db", "c is null");
        }

        ListView remindList = (ListView) findViewById(R.id.remindersList);
        ArrayList<String> listItems = new ArrayList<String>();

        if(!title.isEmpty()){
            listItems.add(title);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,listItems
            );
            remindList.setAdapter(arrayAdapter);
        } else {
            Log.e("db","title is empty");
        }

        Button newRem = (Button) findViewById(R.id.addNewReminderButton);






        newRem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newReminder = new Intent(v.getContext(), NewReminder.class);
                startActivityForResult(newReminder, 0);
            }
        });

    }

}
