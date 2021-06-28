package com.example.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapters.BottlesRecyclerViewAdapter;
import com.example.app.adapters.NetworksRecyclerViewAdapter;
import com.example.app.database.pojo.Bottle;

import java.util.LinkedList;
import java.util.List;

public class ConnectionActivity extends AppCompatActivity {

    private RecyclerView networksRecyclerView;
    private List<ScanResult> networks = new LinkedList<>();
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ActivityCompat.requestPermissions(ConnectionActivity.this,
                new String[] {"android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.ACCESS_COARSE_LOCATION"},
                PackageManager.PERMISSION_GRANTED);

        wifiManager = (WifiManager)
                getApplicationContext().getSystemService(Context.WIFI_SERVICE);

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
        networks = wifiManager.getScanResults();
        initRecyclerView();

    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        networks = wifiManager.getScanResults();
        initRecyclerView();
    }

    public void initRecyclerView(){
        networksRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_networks);
        networksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NetworksRecyclerViewAdapter adapter = new NetworksRecyclerViewAdapter(networks, getSupportFragmentManager(), this);
        networksRecyclerView.setAdapter(adapter);
        networksRecyclerView.setNestedScrollingEnabled(false);
        //bottlesRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));

    }

}