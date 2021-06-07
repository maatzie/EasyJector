package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.PatientTableHandler;

import java.util.List;

public class BottlesActivity extends AppCompatActivity {

//    private RecyclerView patientsRecyclerView;
//    private List<Patient> patients;
//    private PatientTableHandler patientTableHandler;
//    public Integer editPatientID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottles);

        //patientTableHandler = new PatientTableHandler(getBaseContext());


//        EditText editText_City = (EditText) findViewById(R.id.editText_City);
//
//
//
//        Button cancelButton = (Button) findViewById(R.id.button_cancelEditing);
//        cancelButton.setVisibility(View.GONE);
//
//        List<Patient> patients = patientTableHandler.getPatients();
//        initRecyclerView(patients);
//        initAddButtonClick();
//        initCancelButtonClick();
    }
}