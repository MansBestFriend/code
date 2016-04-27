package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class EditReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        setupUI(findViewById(R.id.editRemindParent));
        Bundle b = getIntent().getExtras();
        final int profNum = b.getInt("num");
        final String strTitle = b.getString("title");
        HandleDB helper = new HandleDB(getApplicationContext());

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT Frequency FROM Reminders WHERE ProfileId == " + profNum + " and Title == '" +strTitle + "';", null);
        int minutes = 0;
        String tmpMinutes = "";
        if(c != null){
            if(c.moveToFirst()){
                do{
                    tmpMinutes = c.getString(c.getColumnIndex("Frequency"));
                }while(c.moveToNext());
            }
        }
        if(!tmpMinutes.isEmpty()){
            minutes = Integer.parseInt(tmpMinutes);
        }
        Log.e("e", String.valueOf(minutes));
        int hours = minutes/60;
        minutes -= hours * 60;


        EditText oldMin = (EditText)findViewById(R.id.minField);
        EditText oldHour = (EditText)findViewById(R.id.hourField);
        EditText dayField = (EditText)findViewById(R.id.dayField);
        oldMin.setText(String.valueOf(minutes), TextView.BufferType.EDITABLE);
        oldHour.setText(String.valueOf(hours), TextView.BufferType.EDITABLE);
        dayField.setText("0",TextView.BufferType.EDITABLE);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(EditReminder.this);
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
