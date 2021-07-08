package com.example.app.util;


import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import org.json.JSONException;
import org.json.JSONObject;


public class ConnectionHandler {
    public static Tcp tcp;
    public static Boolean isConnectionEstablished;
    //TODO change
    public static String deviceName = "test";
    public static long currentID;


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
            return receiveMessage != null;

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
            return receiveMessage != null;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

}
