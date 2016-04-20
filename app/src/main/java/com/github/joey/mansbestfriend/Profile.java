package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.LinkedList;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupUI(findViewById(R.id.profileParent));

        final HandleDB helper = new HandleDB(getApplicationContext());
        final SQLiteDatabase db = helper.getWritableDatabase();
        LinkedList<String> breedList = new LinkedList<String>();



        Cursor c = db.rawQuery("SELECT Name FROM Breeds;",null);
        if(c != null){
            if(c.moveToFirst()){
                do{
                    breedList.add(c.getString(c.getColumnIndex("Name")));
                } while(c.moveToNext());
            }
        }
        String[] tmpBrdAry = new String[breedList.size()];
        for(int i=0; i<tmpBrdAry.length; i++){
            tmpBrdAry[i] = breedList.get(i);
        }
        final Spinner breedDropdown = (Spinner)findViewById(R.id.breedSpinner);
        ArrayAdapter<String> profAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tmpBrdAry);
        breedDropdown.setAdapter(profAdapter);

        Button enterProf = (Button)findViewById(R.id.createProfBtn);
        enterProf.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Cursor pNum = db.rawQuery("SELECT Id FROM Profiles;",null);

                String numStr = "";
                if(pNum != null){
                    if(pNum.moveToFirst()){
                        do{
                            numStr = pNum.getString(pNum.getColumnIndex("Id"));
                        } while(pNum.moveToNext());
                    }
                }
                if(!numStr.isEmpty()){
                    int id = Integer.parseInt(numStr) + 1;

                    EditText title = (EditText)findViewById(R.id.dogName);
                    String nameStr = title.getText().toString();
                    EditText weight = (EditText)findViewById(R.id.dogWeight);
                    String weightStr = weight.getText().toString();
                    EditText age = (EditText)findViewById(R.id.ageText);
                    String ageStr = age.getText().toString();
                    RadioGroup sex = (RadioGroup)findViewById(R.id.sexRadioGroup);
                    String sexStr = "";
                    if(sex.getCheckedRadioButtonId() == 0){
                        sexStr = "M";
                    } else {
                        sexStr = "F";
                    }
                    String breedStr = breedDropdown.getSelectedItem().toString();
                    EditText comment = (EditText)findViewById(R.id.profBio);
                    String commentStr = comment.getText().toString();
                    ContentValues values = new ContentValues();
                    values.put("Id",id);
                    values.put("Name", nameStr);
                    values.put("Age", ageStr);
                    values.put("Sex", sexStr);
                    values.put("Biography", commentStr);
                    values.put("BreedName", breedStr);
                    db.insert("Profiles", null, values);
                    db.close();
                    Intent resultIntent = new Intent(v.getContext(),MainActivity.class);
                    setResult(3,resultIntent);
                    finish();

                }

            }

        });

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }



    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Profile.this);
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
