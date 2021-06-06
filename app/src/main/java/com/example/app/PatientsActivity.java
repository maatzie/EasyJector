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
import android.widget.Button;
import android.widget.EditText;

import com.example.app.ui.main.SectionsPagerAdapter;

import java.util.LinkedList;
import java.util.List;


public class PatientsActivity extends AppCompatActivity {

    private RecyclerView patientsRecyclerView;
    private List<Patient> patients;
    private PatientTableHandler patientTableHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        patientTableHandler = new PatientTableHandler(getBaseContext());

        //List<Patient> patients = patientTableHandler.getPatients();
        //patientTableHandler.addTestData();
        //patientTableHandler.updatePatient(6, "Veranika", "Yakouchyts", 22, "Brest");
        //patientTableHandler.deletePatient(5);
        List<Patient> patients = patientTableHandler.getPatients();
        initRecyclerView(patients);
        initButtonClick();
    }

    public void initRecyclerView(List<Patient> patients){
        patientsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_patients);
        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PatientsRecyclerViewAdapter adapter = new PatientsRecyclerViewAdapter(patients, getSupportFragmentManager(), this);
        patientsRecyclerView.setAdapter(adapter);
        //patientsRecyclerView.setNestedScrollingEnabled(false);
        //patientsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));

    }

    private void initButtonClick(){
        Button button = (Button) findViewById(R.id.button_addUser);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean isError = false;
                //First Name handler
                EditText editText_Name = (EditText) findViewById(R.id.editTextT_Name);
                String name = editText_Name.getText().toString();
                if(name.equals("")){
                    editText_Name.setError("You need to enter a First Name!");
                    isError = true;
                }
                //Last Name handler
                EditText editText_Surname = (EditText) findViewById(R.id.editText_Surname);
                String surname = editText_Surname.getText().toString();
                if(surname.equals("")){
                    editText_Surname.setError("You need to enter a Last Name!");
                    isError = true;
                }
                //Age handler
                EditText editText_Age = (EditText) findViewById(R.id.editText_Age);
                String ageText = editText_Age.getText().toString();
                if(ageText.equals("")){
                    editText_Age.setError("You need to enter an Age!");
                    isError = true;
                }
                else if(!ageText.matches("\\d+")){
                    editText_Age.setError("Age must be a number!");
                    isError = true;
                }
                //City handler
                EditText editText_City = (EditText) findViewById(R.id.editText_City);
                String city = editText_City.getText().toString();
                if(city.equals("")){
                    editText_City.setError("You need to enter a City!");
                    isError = true;
                }

                if(!isError){
                    patientTableHandler.addPatient(name, surname, Integer.parseInt(ageText), city);
                    // refresh patients list
                    initRecyclerView(patientTableHandler.getPatients());
                    // clear all inputs
                    editText_Name.getText().clear();
                    editText_Surname.getText().clear();
                    editText_Age.getText().clear();
                    editText_City.getText().clear();
                }
            }
        });
    }

}