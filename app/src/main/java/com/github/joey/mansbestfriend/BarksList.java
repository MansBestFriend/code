package com.github.joey.mansbestfriend;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;



public class BarksList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barks_list);

        Button newBarkButton = (Button) findViewById(R.id.newBarkButton);

        newBarkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newBark = new Intent(v.getContext(), NewBark.class);
                startActivity(newBark);
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
