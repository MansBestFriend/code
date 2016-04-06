package com.github.joey.mansbestfriend;

import java.util.Date;

/**
 * Created by Joey on 4/5/2016.
 */
public class Activity {

    String activityType;
    Date activityDate;
    String activityMessage;

    public Activity(String type, Date date, String message){
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
}
