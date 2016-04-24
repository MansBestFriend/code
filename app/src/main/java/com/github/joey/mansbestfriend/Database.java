package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Database extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        final HandleDB helper = new HandleDB(getApplicationContext());
        final SQLiteDatabase db = helper.getReadableDatabase();
        String dbQuery = "";
        TextView dbInfoText = (TextView) findViewById(R.id.dbTextView);

        String selectedBreed;
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

        Spinner dbDropdown = (Spinner) findViewById(R.id.dbDropdown);
        String[] breedList = new String[]{"Akita", "Australian Shepherd", "Basset Hound", "Beagle", "Bernese Mountain Dog",
                "Bichon Frises", "Bloodhound", "Border Collie", "Boston Terrier", "Boxer", "Brittany", "Bulldog (English)",
                "Bulldog (French)", "Bullmastiff", "Cane Corso", "Cavalier King Charles Spaniel", "Chihuahua", "Collie",
                "Corgi (Pembroke Welsh)", "Dachsund", "Doberman Pinscher", "German Shepherd", "Great Dane", "Havanese",
                "Maltese", "Mastiff", "Miniature Schnauzer", "Newfoundland", "Papillon", "Pointer (German Shorthaired)",
                "Pomeranian", "Poodle", "Pug", "Retriever (Chesepeake Bay)", "Retriever (Golden)", "Retriever (Labrador)",
                "Rhodesian Ridgeback", "Rottweiler", "Shetland Sheepdog", "Shiba Inu", "Shih Tzu", "Siberian Husky",
                "Soft Coated Wheaten Terrier", "Spaniel (Cocker)", "Spaniel (English Springer)", "St. Bernard", "Vizsla",
                "Weimaraners", "West Highland White Terrier", "Yorkshire Terrier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, breedList);
        dbDropdown.setAdapter(adapter);

        selectedBreed = (String) dbDropdown.getSelectedItem();

        Cursor c = db.rawQuery("SELECT * FROM Breeds;", null);
        if(c.moveToFirst()){
                dbQuery = c.getString(c.getColumnIndex("Name"));
        }while(c.moveToNext())

        dbInfoText.setText(dbQuery);
    }

}
