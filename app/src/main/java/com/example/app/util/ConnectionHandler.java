package com.example.app.util;



import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import org.json.JSONException;
import org.json.JSONObject;


public class ConnectionHandler {
    public static Tcp tcp;
    public static Boolean isConnectionEstablished;
    
    public static String deviceName;
    public static long currentID;


    public ConnectionHandler() {
        if (isConnectionEstablished == null)
            isConnectionEstablished = false;
    }


    private void send(String message) {
        tcp.send(message);
    }

    private String receive() {
        return tcp.receive();
    }

    public boolean establishConnection(){
        if (!isConnectionEstablished)
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

    public void setDeviceName(WifiManager wifiManager){
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssid = info.getSSID();
        ConnectionHandler.deviceName = ssid;
    }

    public boolean startInjection() {
        if(!ConnectionHandler.isConnectionEstablished) return false;
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
        if(!ConnectionHandler.isConnectionEstablished) return false;
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

    public String getBatteryState(){
        if(!ConnectionHandler.isConnectionEstablished) return null;
        JSONObject msg = new JSONObject();
        try {
            msg.put("cmd", "battery");
            send(msg.toString());
            String receiveMessage = receive();

            JSONObject json = new JSONObject(receiveMessage);
            receiveMessage = json.get("result").toString();

            return receiveMessage;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
