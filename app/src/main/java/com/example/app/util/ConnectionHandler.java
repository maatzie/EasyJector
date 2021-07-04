package com.example.app.util;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Date;

public class ConnectionHandler {
    public static Tcp tcp;
    public static Boolean isConnectionEstablished;
    private Date startTime;
    private Date stopTime;

    public ConnectionHandler() {
        if (tcp == null)
            tcp = new Tcp();
        if (isConnectionEstablished == null)
            isConnectionEstablished = false;
    }

    public String getConnectionName(WifiManager wifiManager) {
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssid = info.getSSID();
        return ssid;
    }

    public void send(String message) {
        //tcp = new Tcp();
        tcp.send(message);
    }

    public String receive() {
        return tcp.receive();
    }

    public boolean establishConnection(){
        tcp = new Tcp();
        JSONObject msg = new JSONObject();
        try {
            msg.put("cmd", "battery");
            send(msg.toString());
            String receiveMessage = receive();
            if (receiveMessage == null)
                return false;

            isConnectionEstablished = true;
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean startInjection() {
        JSONObject msg = new JSONObject();
        try {
            msg.put("cmd", "on");
            send(msg.toString());
            String receiveMessage = receive();
            if (receiveMessage == null)
                return false;
            startTime = new Date();
            Log.i("START_TIME", startTime.toString());

            // TODO insert into DB
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean stopInjection(){
        JSONObject msg = new JSONObject();
        try {
            msg.put("cmd", "off");
            send(msg.toString());
            String receiveMessage = receive();
            if (receiveMessage == null)
                return false;
            stopTime = new Date();
            Log.i("STOP_TIME", stopTime.toString());

            // TODO update raw about injection
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

}
