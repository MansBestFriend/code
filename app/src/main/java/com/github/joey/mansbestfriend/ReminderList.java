package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ReminderList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

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
