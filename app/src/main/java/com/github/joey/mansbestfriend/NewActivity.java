package com.github.joey.mansbestfriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Date;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Spinner activityDropdown = (Spinner)findViewById(R.id.activityDropdown);
        String[] activityTypeList = new String[]{"Fed", "Walked", "Bathed", "Medicated", "Other"};
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityTypeList);
        activityDropdown.setAdapter(activityAdapter);
    }

    /*String activityType;
    Date activityDate;
    String activityMessage;

    public NewActivity(String type, Date date, String message){
        type = activityType;
        date = activityDate;
        message = activityMessage;
    }

    public void setType(String newType){
        activityType = newType;
    }

    public void setDate(Date newDate){
        activityDate = newDate;
    }

    public void setMessage(String newMessage){
        activityMessage = newMessage;
    }

    public String getType(){
        return activityType;
    }

    public Date getDate(){
        return activityDate;
    }

    public String getMessage(){
        return activityMessage;
    }
*/


}
