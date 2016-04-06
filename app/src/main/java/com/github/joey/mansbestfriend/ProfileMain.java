package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProfileMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        Button viewActivities = (Button) findViewById(R.id.activityBtn);

        viewActivities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent activMenu = new Intent(v.getContext(), ActivityList.class);
                startActivityForResult(activMenu, 0);
            }
        });

        Button viewReminders = (Button) findViewById(R.id.remindersBtn);

        viewReminders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent remindMenu = new Intent(v.getContext(), ReminderList.class);
                startActivityForResult(remindMenu, 0);
            }
        });
    }

}
