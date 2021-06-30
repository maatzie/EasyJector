package com.example.app.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.LinkedList;
import java.util.List;

public class WifiHandler {
    public static List<ScanResult> networks = new LinkedList<>();
    public static ScanResult selectedNetwork;

    public static String getSelectedNetworkName(){
        if(selectedNetwork == null)
            return "";
        return selectedNetwork.SSID;
    }

}
