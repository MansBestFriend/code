package com.github.joey.mansbestfriend;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;

public class ProfileMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        setupUI(findViewById(R.id.profileParent));
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
        final Button back = (Button)findViewById(R.id.mainBack);
        final Button deleteProfileButton = (Button) findViewById(R.id.deleteProfileButton);
        final TextView ageTitle = (TextView) findViewById(R.id.ageTitle);
        final TextView sexTitle = (TextView) findViewById(R.id.sexTitle);
        final TextView breedTitle = (TextView) findViewById(R.id.breedTitle);
        final TextView areYouSure = (TextView) findViewById(R.id.areYouSureTextView);
        final Button yesButton = (Button) findViewById(R.id.yesButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);

        editName.setVisibility(View.INVISIBLE);
        editAge.setVisibility(View.INVISIBLE);
        editBiography.setVisibility(View.INVISIBLE);
        editSex.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);
        editBreedDropdown.setVisibility(View.INVISIBLE);
        deleteProfileButton.setVisibility(View.INVISIBLE);
        areYouSure.setVisibility(View.INVISIBLE);
        yesButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);



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

        //File file = new File(photoPath);
        //Bitmap bit = CustomList.decodeSampledBitmapFromResource(this.getResources(), photoPath, 100, 100);
        //background.setImageBitmap(bit);
        //background.setImageURI(uri);
        //Matrix m = new Matrix();
        //background.setScaleType(ImageView.ScaleType.MATRIX);
        //m.postRotate(90);
        //background.setImageMatrix(m);

        final String finalName = name;

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
                startActivity(activMenu);
                finishActivity(1);
                finish();
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
                startActivity(remindMenu);
                finishActivity(1);
                finish();
            }
        });

        final Button editProfile = (Button) findViewById(R.id.editProfileButton);

        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                back.setVisibility(View.INVISIBLE);
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
                deleteProfileButton.setVisibility(View.VISIBLE);
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
                back.setVisibility(View.VISIBLE);

                editName.setVisibility(View.INVISIBLE);
                editAge.setVisibility(View.INVISIBLE);
                editBiography.setVisibility(View.INVISIBLE);
                editSex.setVisibility(View.INVISIBLE);
                editBreedDropdown.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                deleteProfileButton.setVisibility(View.INVISIBLE);
            }
        });


        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){


                Intent i = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(i,0);
                finishActivity(1);
                finish();
            }
        });

        deleteProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editName.setVisibility(View.INVISIBLE);
                editAge.setVisibility(View.INVISIBLE);
                editBiography.setVisibility(View.INVISIBLE);
                editSex.setVisibility(View.INVISIBLE);
                editBreedDropdown.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                deleteProfileButton.setVisibility(View.INVISIBLE);
                ageTitle.setVisibility(View.INVISIBLE);
                sexTitle.setVisibility(View.INVISIBLE);
                breedTitle.setVisibility(View.INVISIBLE);

                areYouSure.setVisibility(View.VISIBLE);
                yesButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                db.delete("Profiles","Id=" + profNum,null);

                Intent i = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(i,0);
                finishActivity(1);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editName.setVisibility(View.VISIBLE);
                editAge.setVisibility(View.VISIBLE);
                editBiography.setVisibility(View.VISIBLE);
                editSex.setVisibility(View.VISIBLE);
                editBreedDropdown.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);
                deleteProfileButton.setVisibility(View.VISIBLE);
                ageTitle.setVisibility(View.VISIBLE);
                sexTitle.setVisibility(View.VISIBLE);
                breedTitle.setVisibility(View.VISIBLE);

                areYouSure.setVisibility(View.INVISIBLE);
                yesButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);

            }
        });



    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ProfileMain.this);
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
