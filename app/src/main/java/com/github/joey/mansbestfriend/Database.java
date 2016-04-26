package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Database extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        final HandleDB helper = new HandleDB(getApplicationContext());
        final SQLiteDatabase db = helper.getReadableDatabase();
        String dbQuery = "";
        final TextView dbInfoText = (TextView) findViewById(R.id.dogInfoText);

        String selectedBreed = "";
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

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent profiles = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(profiles, 0);
            }
        });




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
        final Spinner breedDropdown = (Spinner)findViewById(R.id.dbDropdown);
        ArrayAdapter<String> profAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tmpBrdAry);
        breedDropdown.setAdapter(profAdapter);

        /*selectedBreed = breedDropdown.getSelectedItem().toString();

        c = db.rawQuery("SELECT * FROM Breeds WHERE Name='" + selectedBreed + "';", null);

        infoText = "General Information\n\n" + type + "\n\n" + "Disposition\n\n" + disposition + "\n\n" +
                "Grooming\n\n" + grooming + "\n\n" + "Health\n\n" + health + "\n\n";


        dbInfoText.setText(infoText);
        */

        breedDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedBreed2 = breedDropdown.getSelectedItem().toString();
                String type = "";
                String disposition = "";
                String grooming = "";
                String health = "";
                Cursor c2 = db.rawQuery("SELECT * FROM Breeds WHERE Name='" + selectedBreed2 + "';", null);
                if(c2 != null){
                    if(c2.moveToFirst()){
                        do{
                            type = c2.getString(c2.getColumnIndex("Type"));
                            disposition = c2.getString(c2.getColumnIndex("Disposition"));
                            grooming = c2.getString(c2.getColumnIndex("Grooming"));
                            health = c2.getString(c2.getColumnIndex("Health"));
                        } while(c2.moveToNext());
                    }
                }
                String infoText = "GENERAL INFORMATION\n\n" + type + "\n\n\n\n" + "DISPOSITION\n\n" + disposition + "\n\n\n\n" +
                        "GROOMING\n\n" + grooming + "\n\n\n\n" + "HEALTH\n\n" + health;

                dbInfoText.setText(infoText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

}
