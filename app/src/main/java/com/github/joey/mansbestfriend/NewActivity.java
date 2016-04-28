package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
        setupUI(findViewById(R.id.newActivParent));
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

                Cursor c = db.rawQuery("SELECT Number FROM Activities;", null);

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
                startActivity(activMenu);
                finishActivity(1);
                finish();
            }
        });

        Button back = (Button)findViewById(R.id.newActivBack);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),ActivityList.class);
                Bundle b = new Bundle();
                b.putInt("1",profNum);
                i.putExtras(b);
                startActivity(i);
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
                    hideSoftKeyboard(NewActivity.this);
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
