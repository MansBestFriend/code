package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        final HandleDB helper = new HandleDB(getApplicationContext());
        final SQLiteDatabase db = helper.getReadableDatabase();

        Button viewActivities = (Button) findViewById(R.id.activityBtn);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView biographyTextView = (TextView) findViewById(R.id.biographyTextView);
        TextView ageTextView = (TextView) findViewById(R.id.ageTextView);
        TextView sexTextView = (TextView) findViewById(R.id.sexTextView);
        TextView breedTextView = (TextView) findViewById(R.id.breedTextView);

        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("1");

        String name = "";
        String age = "";
        String sex = "";
        String biography = "";
        String breed = "";
        String photoPath = "";

        Cursor c = db.rawQuery("SELECT * FROM Profiles WHERE Id=" + profNum + ";", null);

        if(c != null){
            if (c.moveToFirst()) {
                do{
                    name = c.getString(c.getColumnIndex("Name"));
                    age = c.getString(c.getColumnIndex("Age"));
                    sex = c.getString(c.getColumnIndex("Sex"));
                    biography = c.getString(c.getColumnIndex("Biography"));
                    breed = c.getString(c.getColumnIndex("BreedName"));
                    photoPath = c.getString(c.getColumnIndex("PhotoPath"));
                } while(c.moveToNext());
            }
        }

        nameTextView.setText(name);
        biographyTextView.setText(biography);
        ageTextView.setText(age);
        sexTextView.setText(sex);
        breedTextView.setText(breed);



        viewActivities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent activMenu = new Intent(v.getContext(), ActivityList.class);

                Bundle b = new Bundle();
                b.putInt("1",profNum);
                activMenu.putExtras(b);
                startActivityForResult(activMenu,0);
                finishActivity(1);
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
                startActivityForResult(remindMenu,0);
                finishActivity(1);
            }
        });



    }

}
