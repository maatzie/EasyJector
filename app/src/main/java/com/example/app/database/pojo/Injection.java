package com.example.app.database.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Injection {
    private int ID;
    private int patientID;
    private int bottleID;
    private String deviceName;
    private Date startTime;
    private Date stopTime;

    public Injection(int ID, int patientID, int bottleID, String deviceName, String startTime, String stopTime){
        this.ID = ID;
        this.patientID = patientID;
        this.bottleID = bottleID;
        this.deviceName = deviceName;
        this.startTime = null;
        this.stopTime = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        try {
            this.startTime = format.parse(startTime);
            this.stopTime = format.parse(stopTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getBottleID() {
        return bottleID;
    }

    public void setBottleID(int bottleID) {
        this.bottleID = bottleID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }
}
