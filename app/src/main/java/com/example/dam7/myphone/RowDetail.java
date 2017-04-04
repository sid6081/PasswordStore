package com.example.dam7.myphone;

/**
 * Created by dam7 on 17/02/17.
 */

public class RowDetail {
    private String name, userId, passwordPin, timeStamp;

    public RowDetail(){

    }

    public RowDetail(String n, String u, String p, String t){
        name = n;
        userId = u;
        passwordPin = p;
        timeStamp = t;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String u){
        userId = u;
    }

    public String getPasswordPin(){
        return passwordPin;
    }

    public void setPasswordPin(String p){
        passwordPin = p;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public void setTimeStamp(String t){
        timeStamp = t;
    }
}
