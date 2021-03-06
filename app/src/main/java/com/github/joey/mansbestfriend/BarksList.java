package com.github.joey.mansbestfriend;


import android.content.ContentValues;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class BarksList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barks_list);

        String category = "";
        String comment = "";
        String dateTime = "";
        int likeNumber = 0;
        String barkString = "";

        HandleDB helper = new HandleDB(getApplicationContext());
        final SQLiteDatabase db = helper.getReadableDatabase();
        final ListView barksList = (ListView) findViewById(R.id.barkListView);
        ArrayList<String> barkListItems = new ArrayList<String>();


        Cursor c = db.rawQuery("SELECT * FROM Barks;",null);

        if(c != null){
            if(c.moveToFirst()){
                do{
                    category = c.getString(c.getColumnIndex("Category"));
                    comment = c.getString(c.getColumnIndex("Comment"));
                    dateTime = c.getString(c.getColumnIndex("dateTime"));
                    likeNumber = c.getInt(c.getColumnIndex("likeNumber"));
                    barkString = category + ", Posted " + dateTime + "\n\n" + comment + "\n\n" + likeNumber + " Likes";
                    Log.e("likes", String.valueOf(likeNumber));
                    barkListItems.add(barkString);
                } while(c.moveToNext());
            }
        }



        //barkString = "Recommendation" + ", Posted " + "April 29, 12:06pm" + "\n\n" + "PetSmart is awesome!" + "\n\n" + 4 + " Likes";
        //barkListItems.add(barkString);
        //barkString = "Warning" + ", Posted " + "April 29, 11:23am" + "\n\n" + "Don't walk your dog on 15th street! Too much traffic" + "\n\n" + 8 + " Likes";
        //barkListItems.add(barkString);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,barkListItems
        );
        barksList.setAdapter(arrayAdapter);

        barksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long i2) {
                Toast.makeText(getApplicationContext(),"Liked",Toast.LENGTH_SHORT).show();

                int Id = pos+1;
                Log.e("pos",String.valueOf(Id));
                int likeNumber2 = 0;


                Cursor c2 = db.rawQuery("SELECT likeNumber FROM Barks WHERE ID=" + Id +";",null);

                if(c2 != null){
                    if(c2.moveToFirst()){
                        do{
                            likeNumber2 = c2.getInt(c2.getColumnIndex("likeNumber"));

                        } while(c2.moveToNext());
                    }
                }

                ContentValues values = new ContentValues();
                values.put("likeNumber", likeNumber2 + 1);
                db.update("Barks", values, "ID=" + Id, null);

                Intent i = new Intent(getApplicationContext(),BarksList.class);
                startActivity(i);
                finish();


            }
        });

        Button newBarkButton = (Button) findViewById(R.id.newBarkButton);

        newBarkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newBark = new Intent(v.getContext(), NewBark.class);
                startActivity(newBark);
                db.close();
                finish();
            }
        });

        String mLatitudeText;
        String mLongitudeText;

        Button back = (Button)findViewById(R.id.barkBack);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(),MainActivity.class);
                startActivity(i);
                finishActivity(1);
                db.close();
                finish();
            }
        });
        /*
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,
                        (GoogleApiClient.OnConnectionFailedListener) this)
                .build();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }



        mGoogleApiClient.connect();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        mLatitudeText = String.valueOf(mLastLocation.getLatitude());
        mLongitudeText = String.valueOf(mLastLocation.getLongitude());

        TextView locationTextView = (TextView) findViewById(R.id.locationText);
        locationTextView.setText(mLatitudeText+" "+mLongitudeText);
        */

    }
}
