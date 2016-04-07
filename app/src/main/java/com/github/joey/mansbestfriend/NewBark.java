package com.github.joey.mansbestfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class NewBark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bark);

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
                startActivityForResult(barksList, 0);
            }
        });

        submitBarkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent barksList = new Intent(v.getContext(), BarksList.class);
                startActivityForResult(barksList, 0);

                //This should also add the new bark's information to the barks table in the database
            }
        });
    }

}
