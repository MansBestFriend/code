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

        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("1");

        viewActivities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent activMenu = new Intent(v.getContext(), ActivityList.class);

                Bundle b = new Bundle();
                b.putInt("1",profNum);
                activMenu.putExtras(b);
                startActivity(activMenu);
                finish();
            }
        });

        Button viewReminders = (Button) findViewById(R.id.remindersBtn);

        viewReminders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent remindMenu = new Intent(v.getContext(), ReminderList.class);

                Bundle b = new Bundle();
                b.putInt("1",profNum);
                remindMenu.putExtras(b);
                startActivity(remindMenu);
                finish();
            }
        });



    }

}
