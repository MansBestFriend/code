package com.github.joey.mansbestfriend;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;

public class ProfileMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        final HandleDB helper = new HandleDB(getApplicationContext());
        final SQLiteDatabase db = helper.getReadableDatabase();

        final ImageView background = (ImageView) findViewById(R.id.profileBackground);

        final Button viewActivities = (Button) findViewById(R.id.activityBtn);
        final TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        final TextView biographyTextView = (TextView) findViewById(R.id.biographyTextView);
        final TextView ageTextView = (TextView) findViewById(R.id.ageTextView);
        final TextView sexTextView = (TextView) findViewById(R.id.sexTextView);
        final TextView breedTextView = (TextView) findViewById(R.id.breedTextView);
        final EditText editName = (EditText) findViewById(R.id.editNameTextField);
        final EditText editBiography = (EditText) findViewById(R.id.editBiographyTextField);
        final EditText editAge = (EditText) findViewById(R.id.editAgeTextField);
        final EditText editSex = (EditText) findViewById(R.id.editSexTextField);
        final Spinner editBreedDropdown = (Spinner) findViewById(R.id.editBreedDropdown);
        final Button submitButton = (Button) findViewById(R.id.submitProfileButton);

        editName.setVisibility(View.INVISIBLE);
        editAge.setVisibility(View.INVISIBLE);
        editBiography.setVisibility(View.INVISIBLE);
        editSex.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);
        editBreedDropdown.setVisibility(View.INVISIBLE);

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

        File file = new File(Environment.getExternalStorageDirectory()+File.separator + photoPath);
        Uri uri = Uri.fromFile(file);
        background.setImageURI(uri);

        final String finalName = name;

        //test

        nameTextView.setText(name);
        biographyTextView.setText(biography);
        ageTextView.setText(age);
        sexTextView.setText(sex);
        breedTextView.setText(breed);

        editName.setText(name);
        editAge.setText(age);
        editBiography.setText(biography);
        editSex.setText(sex);

        LinkedList<String> breedList = new LinkedList<String>();

        c = db.rawQuery("SELECT Name FROM Breeds;",null);
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
        ArrayAdapter<String> editBreedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tmpBrdAry);
        editBreedDropdown.setAdapter(editBreedAdapter);

        int breedPosition = editBreedAdapter.getPosition(breed);
        editBreedDropdown.setSelection(breedPosition);

        viewActivities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent activMenu = new Intent(v.getContext(), ActivityList.class);

                Bundle b = new Bundle();
                b.putInt("1", profNum);
                activMenu.putExtras(b);
                startActivityForResult(activMenu, 0);
                finishActivity(1);
            }
        });

        final Button viewReminders = (Button) findViewById(R.id.remindersBtn);

        viewReminders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent remindMenu = new Intent(v.getContext(), ReminderList.class);

                Bundle b = new Bundle();
                b.putInt("1", profNum);
                remindMenu.putExtras(b);
                startActivityForResult(remindMenu, 0);
                finishActivity(1);
            }
        });

        final Button editProfile = (Button) findViewById(R.id.editProfileButton);

        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nameTextView.setVisibility(View.INVISIBLE);
                biographyTextView.setVisibility(View.INVISIBLE);
                ageTextView.setVisibility(View.INVISIBLE);
                sexTextView.setVisibility(View.INVISIBLE);
                breedTextView.setVisibility(View.INVISIBLE);
                viewActivities.setVisibility(View.INVISIBLE);
                viewReminders.setVisibility(View.INVISIBLE);
                editProfile.setVisibility(View.INVISIBLE);
                editBreedDropdown.setVisibility(View.VISIBLE);

                editName.setVisibility(View.VISIBLE);
                editAge.setVisibility(View.VISIBLE);
                editBiography.setVisibility(View.VISIBLE);
                editSex.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String newName = editName.getText().toString();
                String newBiography = editBiography.getText().toString();
                String newAge = editAge.getText().toString();
                String newSex = editSex.getText().toString();
                String newBreed = editBreedDropdown.getSelectedItem().toString();

                nameTextView.setText(newName);
                biographyTextView.setText(newBiography);
                ageTextView.setText(newAge);
                sexTextView.setText(newSex);

                ContentValues values = new ContentValues();
                values.put("Name",newName);
                values.put("Age",newAge);
                values.put("Sex",newSex);
                values.put("Biography",newBiography);
                values.put("BreedName",newBreed);
                db.update("Profiles", values, "Name='" + finalName + "'", null);

                nameTextView.setVisibility(View.VISIBLE);
                biographyTextView.setVisibility(View.VISIBLE);
                ageTextView.setVisibility(View.VISIBLE);
                sexTextView.setVisibility(View.VISIBLE);
                breedTextView.setVisibility(View.VISIBLE);
                viewActivities.setVisibility(View.VISIBLE);
                viewReminders.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.VISIBLE);

                editName.setVisibility(View.INVISIBLE);
                editAge.setVisibility(View.INVISIBLE);
                editBiography.setVisibility(View.INVISIBLE);
                editSex.setVisibility(View.INVISIBLE);
                editBreedDropdown.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
            }
        });

        Button back = (Button)findViewById(R.id.mainBack);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(i,0);
                finishActivity(1);
                finish();
            }
        });

    }
}
