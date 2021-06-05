package com.example.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.Contract;
import com.example.app.database.sqlite.DbHelper;
import com.example.app.database.sqlite.PatientTableHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.app.ui.main.SectionsPagerAdapter;

import java.util.LinkedList;
import java.util.List;


public class PatientsActivity extends AppCompatActivity {

    private RecyclerView patientsRecyclerView;
    private List<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        PatientTableHandler patientTableHandler = new PatientTableHandler(getBaseContext());

        //List<Patient> patients = patientTableHandler.getPatients();
        //patientTableHandler.addTestData();
        patientTableHandler.updatePatient(6, "Veranika", "Yakouchyts", 22, "Brest");
        patientTableHandler.deletePatient(5);
        List<Patient> patients = patientTableHandler.getPatients();
        initRecyclerView(patients);
    }

    private void initRecyclerView(List<Patient> patients){
        patientsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_patients);
        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PatientsRecyclerViewAdapter adapter = new PatientsRecyclerViewAdapter(patients, getSupportFragmentManager(), this);
        patientsRecyclerView.setAdapter(adapter);
        //patientsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));

    }



}