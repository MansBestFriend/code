package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

public class ActivityList extends AppCompatActivity {



    ListView activityListView;

    String[] activityList = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
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

        activityListView = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> activityListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, activityList);

        activityListView.setAdapter(activityListAdapter);


        Button newActiv = (Button) findViewById(R.id.newActivityBtn);

        newActiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(v.getContext(), NewActivity.class);
                startActivityForResult(newActivity, 0);
            }
        });
    }


}
