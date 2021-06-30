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
        tcp = new Tcp();
        tcp.send(message);
    }

    public String receive() {
        return tcp.receive();
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
            Log.i("TIME", startTime.toString());
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

}
