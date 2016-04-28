package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewBark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bark);
        setupUI(findViewById(R.id.newBarkParent));
        Spinner barkTypeDropdown = (Spinner) findViewById(R.id.barkTypeDropdown);
        String[] barkTypeList = new String[]{"Recommendation", "Warning", "Adoption", "Funny", "Other"};
        ArrayAdapter<String> barkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, barkTypeList);
        barkTypeDropdown.setAdapter(barkAdapter);

        Button backButton = (Button) findViewById(R.id.backButton);
        Button submitBarkButton = (Button) findViewById(R.id.submitBarkButton);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent barksList = new Intent(v.getContext(), BarksList.class);
                startActivity(barksList);
                finish();
            }
        });

        submitBarkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent barksList = new Intent(v.getContext(), BarksList.class);
                startActivity(barksList);
                finish();
                //This should also add the new bark's information to the barks table in the database
            }
        });
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(NewBark.this);
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
