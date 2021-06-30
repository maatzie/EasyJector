package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.app.util.WifiHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NetworkActivity extends AppCompatActivity {

    private static List<ScanResult> networks = new LinkedList<>();
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        TextView networkName = findViewById(R.id.textView_networkName);
        networkName.setText(WifiHandler.getSelectedNetworkName());

        setCancelButtonOnClickListener((Button) findViewById(R.id.button_cancelConnection), new NetworksActivity());
        setSubmitButtonOnClickListener((Button) findViewById(R.id.button_submitConnection), new NetworksActivity());

        ActivityCompat.requestPermissions(NetworkActivity.this,
                new String[] {"android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.ACCESS_COARSE_LOCATION",
                        "android.permission.CHANGE_WIFI_STATE",
                        "android.permission.ACCESS_WIFI_STATE",
                        "android.permission.INTERNET"},
                PackageManager.PERMISSION_GRANTED);

    }

    private void setCancelButtonOnClickListener(Button button, final Activity activity) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiHandler.selectedNetwork = null;
                Intent intent = new Intent(view.getContext(), activity.getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void setSubmitButtonOnClickListener(Button button, final Activity activity) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //connect to wifi here

                EditText passEdit = (EditText) findViewById(R.id.editText_networkPassword);
                String pass = passEdit.getText().toString();

//                WifiConfiguration conf = new WifiConfiguration();
//                conf.SSID = "\"" + WifiHandler.getSelectedNetworkName() + "\"";
//
//                conf.wepKeys[0] = "\"" + pass + "\"";
//                conf.wepTxKeyIndex = 0;
//                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//
//                conf.preSharedKey = "\"" + pass + "\"";
//
//                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                wifiManager.addNetwork(conf);
//
//
//                @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
//                for( WifiConfiguration i : list ) {
//                    if(i.SSID != null && i.SSID.equals("\"" + WifiHandler.getSelectedNetworkName() + "\"")) {
//                        wifiManager.disconnect();
//                        wifiManager.enableNetwork(i.networkId, true);
//                        wifiManager.reconnect();
//
//                        break;
//                    }
//                }

//                WifiConfiguration wifiConfig = new WifiConfiguration();
//                wifiConfig.SSID = String.format("\"%s\"", WifiHandler.getSelectedNetworkName());
//                wifiConfig.preSharedKey = String.format("\"%s\"", pass);
//
//                WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
//                //remember id
//                int netId = wifiManager.addNetwork(wifiConfig);
//                wifiManager.disconnect();
//                boolean result = wifiManager.enableNetwork(netId, true);
//                Log.i("WIFI", Boolean.toString(result));
//                wifiManager.reconnect();



//                final WifiNetworkSuggestion suggestion1 =
//                        new WifiNetworkSuggestion.Builder()
//                                .setSsid(WifiHandler.getSelectedNetworkName())
//                                //.setIsAppInteractionRequired(true) // Optional (Needs location permission)
//                                .build();
//
//                final WifiNetworkSuggestion suggestion2 =
//                        new WifiNetworkSuggestion.Builder()
//                                .setSsid(WifiHandler.getSelectedNetworkName())
//                                .setWpa2Passphrase(pass)
//                                //.setIsAppInteractionRequired(true) // Optional (Needs location permission)
//                                .build();
//
//                final WifiNetworkSuggestion suggestion3 =
//                        new WifiNetworkSuggestion.Builder()
//                                .setSsid(WifiHandler.getSelectedNetworkName())
//                                .setWpa3Passphrase(pass)
//                                //.setIsAppInteractionRequired(true) // Optional (Needs location permission)
//                                .build();
//
//
//                final List<WifiNetworkSuggestion> suggestionsList =
//                        new ArrayList<WifiNetworkSuggestion>();
//                suggestionsList.add(suggestion1);
//                suggestionsList.add(suggestion2);
//                suggestionsList.add(suggestion3);
//
//
//                final WifiManager wifiManager =
//                        (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
////                int netId = wifiManager.getConnectionInfo().getNetworkId();
////                wifiManager.disableNetwork(netId);
//                boolean dis = wifiManager.disconnect();
//                Log.i("STATUS", Boolean.toString(dis));
//
//                wifiManager.removeNetworkSuggestions(suggestionsList);
//                final int status = wifiManager.addNetworkSuggestions(suggestionsList);
//                Log.i("STATUS", Integer.toString(status));
//                if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
//                    // do error handling here…
//                    Log.i("WIFI", "ERROR");
//                }
//
//                // Optional (Wait for post connection broadcast to one of your suggestions)
//                final IntentFilter intentFilter =
//                        new IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION);
//
//                final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context context, Intent intent) {
//                        if (!intent.getAction().equals(
//                                WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
//                            return;
//                        }
//                        Log.i("WIFI", "CONNECTED?");
//                    }
//                };
//                getBaseContext().registerReceiver(broadcastReceiver, intentFilter);

                Intent intent = new Intent(view.getContext(),activity.getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intent);
            }
        });
    }

}