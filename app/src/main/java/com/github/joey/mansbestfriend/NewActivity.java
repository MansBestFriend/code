package com.github.joey.mansbestfriend;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("1");

        final HandleDB helper = new HandleDB(getApplicationContext());

        final SQLiteDatabase db = helper.getWritableDatabase();

        final Spinner activityDropdown = (Spinner)findViewById(R.id.activityDropdown);
        String[] activityTypeList = new String[]{"Fed", "Walked", "Bathed", "Medicated", "Other"};
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityTypeList);
        activityDropdown.setAdapter(activityAdapter);

        Button enterActiv = (Button) findViewById(R.id.enterActivityBtn);

        enterActiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Cursor c = db.rawQuery("SELECT Number FROM Activities WHERE ProfileId == " + profNum + ";", null);

                String numberStr = "";

                if(c != null){
                    if(c.moveToFirst()){
                        do{
                            numberStr = c.getString(c.getColumnIndex("Number"));
                        } while(c.moveToNext());
                    }
                }

                int num = Integer.parseInt(numberStr) + 1;

                EditText commentText = (EditText) findViewById(R.id.activityComment);
                String commentStr = commentText.getText().toString();
                String title = activityDropdown.getSelectedItem().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                ContentValues values = new ContentValues();
                values.put("Number",num);
                values.put("DateTime",currentDateTimeString);
                values.put("Title",title);
                values.put("Comment", commentStr);
                values.put("ProfileId", profNum);

                db.insert("Activities", null, values);

                db.close();
                Intent activMenu = new Intent(v.getContext(), ActivityList.class);

                Bundle b = new Bundle();
                b.putInt("1", profNum);
                activMenu.putExtras(b);
                startActivityForResult(activMenu, 0);
                finishActivity(1);
                finish();
            }
        });
    }

}
