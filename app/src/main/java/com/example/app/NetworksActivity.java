package com.example.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapters.NetworksRecyclerViewAdapter;
import com.example.app.util.WifiHandler;

public class NetworksActivity extends AppCompatActivity {

    private WifiHandler wifi;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networks);

        wifi = new WifiHandler();

        ActivityCompat.requestPermissions(NetworksActivity.this,
                new String[] {"android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.CHANGE_WIFI_STATE",
                "android.permission.ACCESS_WIFI_STATE",
                "android.permission.INTERNET"},
                PackageManager.PERMISSION_GRANTED);

        wifiManager = (WifiManager)
                getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        initRecyclerView();

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scanSuccess();
                } else {
                    // scan failure handling
                    scanFailure();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);


        boolean success = wifiManager.startScan();
        if (!success) {
            scanFailure();
        }


    }
    private void scanSuccess() {
        WifiHandler.networks = wifiManager.getScanResults();
        initRecyclerView();

    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        WifiHandler.networks = wifiManager.getScanResults();
        initRecyclerView();
    }

    public void initRecyclerView(){
        RecyclerView networksRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_networks);
        networksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NetworksRecyclerViewAdapter adapter = new NetworksRecyclerViewAdapter(WifiHandler.networks, getSupportFragmentManager(), this);
        networksRecyclerView.setAdapter(adapter);
        networksRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

    }

}