package com.example.app;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.app.util.ConnectionHandler;

public class ConnectionActivity extends AppCompatActivity {

    ConnectionHandler connectionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        connectionHandler = new ConnectionHandler();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        ActivityCompat.requestPermissions(ConnectionActivity.this,
//                new String[] {"android.permission.ACCESS_FINE_LOCATION",
//                        "android.permission.ACCESS_COARSE_LOCATION",
//                        "android.permission.CHANGE_WIFI_STATE",
//                        "android.permission.ACCESS_WIFI_STATE",
//                        "android.permission.INTERNET"},
//                PackageManager.PERMISSION_GRANTED);

//        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context c, Intent intent) {
//
//                if (intent.getAction() == WifiManager.WIFI_STATE_CHANGED_ACTION) {
//                    setNetworkName();
//
//                } else {
//                    // scan failure handling
//                    Log.i("ERROOR", "ERROR");
//
//                }
//            }
//        };
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);



        setCheckButtonOnClickListener((Button)findViewById(R.id.button_checkConnection));
        //setNetworkName();
    }

    private void setNetworkName(){
        //Log.i("STATUS", "CHANGED");
        String ssid = connectionHandler.getConnectionName((WifiManager) getApplicationContext().getSystemService (Context.WIFI_SERVICE));

        TextView textView = (TextView) findViewById(R.id.textView_connectionName);
        textView.setText(ssid);
        //textView.refreshDrawableState();
    }
    private void setCheckButtonOnClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean isEstablished = connectionHandler.establishConnection();
                    if(isEstablished){
                        setBatteryState();
                        Toast.makeText(getApplicationContext(),"Connection has been established!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Ups! Troubles occurred while connecting to the device!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Ups! Troubles occurred while connecting to the device!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setBatteryState(){

    }

}