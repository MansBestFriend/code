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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

//Test
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandleDB dbChk = new HandleDB(getApplicationContext());
        if(!dbChk.checkDatabase(getApplicationContext())) {
            try {
                dbChk.createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_main);
        updateProfList();
        Button newProfileButton = (Button) findViewById(R.id.newProfileButton);
        //ImageButton prof1Button = (ImageButton) findViewById(R.id.imageButton);




        newProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newProfile = new Intent(v.getContext(), Profile.class);
                startActivityForResult(newProfile, 3);


            }
        });

//        prof1Button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent prof = new Intent(v.getContext(), ProfileMain.class);
//                Bundle b = new Bundle();
//                b.putInt("1",1);
//                prof.putExtras(b);
//                startActivityForResult(prof,0);
//                finishActivity(1);
//
//
//            }
//        });

        Button barksButton = (Button) findViewById(R.id.barksButton);

        barksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent barksList = new Intent(v.getContext(), BarksList.class);
                startActivity(barksList);
                finish();
            }
        });


        Button dbButton = (Button) findViewById(R.id.dbButton);

        dbButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent db = new Intent(v.getContext(), Database.class);
                startActivity(db);
                finish();
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateProfList(){
        HandleDB helper = new HandleDB(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Id,PhotoPath FROM Profiles;", null);
        TreeMap<String,String> imgMap = new TreeMap<String,String>();
        LinkedList<String> profList = new LinkedList<String>();

        if(c != null){
            if(c.moveToFirst()){
                do{
                    String tmp = c.getString(c.getColumnIndex("Id"));
                    profList.add(tmp);
                    if(tmp != null && !tmp.isEmpty()) {
                        String tmpPath = c.getString(c.getColumnIndex("PhotoPath"));
                        if(tmpPath != null && !tmpPath.isEmpty()) {
                            imgMap.put(tmp, tmpPath);

                            Log.e("e", tmp);
                        }


                    }
                } while(c.moveToNext());
            }
        }
        String[] sampleTxt = new String[profList.size()];
        Integer[] imageList = new Integer[profList.size()];
        String[] imgPathList = new String[profList.size()];
        for(int i=0; i<profList.size(); i++){

            imageList[i] = R.drawable.dog;
            sampleTxt[i] = "";

        }
        CustomList adapter = new CustomList(MainActivity.this,sampleTxt,imageList,imgMap);
        ListView pList = (ListView)findViewById(R.id.profileList);
        pList.setAdapter(adapter);
        pList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent prof = new Intent(view.getContext(), ProfileMain.class);
                Bundle b = new Bundle();
                b.putInt("1",position + 1);
                prof.putExtras(b);
                startActivity(prof);
                finishActivity(1);
                finish();
            }
        });
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 3){
            updateProfList();
        }
    }
}
