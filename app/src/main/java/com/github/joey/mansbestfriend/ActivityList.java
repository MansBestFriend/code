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
import java.util.Collections;
import java.util.Date;

public class ActivityList extends AppCompatActivity {



    ListView activityListView;

    String[] activityList = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("1");

        HandleDB helper = new HandleDB(getApplicationContext());


        Log.e("db", getDatabasePath("mansbestfriend.db").toString());

        SQLiteDatabase db = helper.getReadableDatabase();

        //SQLiteDatabase db = this.openOrCreateDatabase(getDatabasePath("mansbestfriend.db").toString(), MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT * FROM Activities WHERE ProfileId == " + profNum + ";", null);

        String comment = "";
        String title = "";
        String dateTime = "";
        String activity = "";

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

       ListView activityListView = (ListView) findViewById(R.id.activityList);
        ArrayList<String> listItems = new ArrayList<String>();








        if(c != null){
            if(c.moveToFirst()){
                do{
                    comment = c.getString(c.getColumnIndex("Comment"));
                    title = c.getString(c.getColumnIndex("Title"));
                    dateTime = c.getString(c.getColumnIndex("DateTime"));
                    activity = title + ": " + comment + " (" + dateTime +")";
                    listItems.add(activity);
                    Log.e("e",comment);
                } while(c.moveToNext());
            }
        }

        if(!comment.isEmpty()){
            Collections.reverse(listItems);
            ArrayAdapter<String> activityListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
            //Collections.reverse(listItems);
            activityListView.setAdapter(activityListAdapter);
        }

        Button newActiv = (Button) findViewById(R.id.newActivityBtn);

        newActiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(v.getContext(), NewActivity.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                newActivity.putExtras(b);
                startActivityForResult(newActivity, 0);
                finishActivity(1);
            }
        });

    }


}
