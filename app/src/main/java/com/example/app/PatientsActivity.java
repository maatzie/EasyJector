package com.example.app;

import android.os.Bundle;

import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.PatientTableHandler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class PatientsActivity extends AppCompatActivity {

    private RecyclerView patientsRecyclerView;
    private PatientTableHandler patientTableHandler;
    public Integer editPatientID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        patientTableHandler = new PatientTableHandler(getBaseContext());


        Button cancelButton = (Button) findViewById(R.id.button_cancelEditingPatient);
        cancelButton.setVisibility(View.GONE);

        List<Patient> patients = patientTableHandler.getPatients();
        initRecyclerView(patients);
        initAddButtonClick();
        initCancelButtonClick();
    }

    public void initRecyclerView(List<Patient> patients){
        patientsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_patients);
        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PatientsRecyclerViewAdapter adapter = new PatientsRecyclerViewAdapter(patients, this);
        patientsRecyclerView.setAdapter(adapter);
        patientsRecyclerView.setNestedScrollingEnabled(false);

    }

    private void initAddButtonClick(){
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
                    if(editPatientID == null){
                        int exit = patientTableHandler.addPatient(name, surname, Integer.parseInt(ageText), city);
                        if(exit == 1) //success
                            Toast.makeText(getApplicationContext(),"Patient has been added!", Toast.LENGTH_SHORT).show();
                        else if(exit == 0) //error
                            Toast.makeText(getApplicationContext(),"Error occurred while adding a patient!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        int exit = patientTableHandler.updatePatient(editPatientID, name, surname, Integer.parseInt(ageText), city);
                        editPatientID = null;
                        Button cancelButton = (Button) findViewById(R.id.button_cancelEditingPatient);
                        cancelButton.setVisibility(View.GONE);
                        if(exit == 1) //success
                            Toast.makeText(getApplicationContext(),"Patient has been edited!", Toast.LENGTH_SHORT).show();
                        else if(exit == 0) //error
                            Toast.makeText(getApplicationContext(),"Error occurred while editing a patient!", Toast.LENGTH_SHORT).show();
                    }
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

    public void initCancelButtonClick() {
        Button cancelButton = (Button) findViewById(R.id.button_cancelEditingPatient);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText_Name = (EditText) findViewById(R.id.editTextT_Name);
                EditText editText_Surname = (EditText) findViewById(R.id.editText_Surname);
                EditText editText_Age = (EditText) findViewById(R.id.editText_Age);
                EditText editText_City = (EditText) findViewById(R.id.editText_City);

                // clear all inputs
                editText_Name.getText().clear();
                editText_Surname.getText().clear();
                editText_Age.getText().clear();
                editText_City.getText().clear();

                editPatientID = null;

                Button cancelButton = (Button) findViewById(R.id.button_cancelEditingPatient);
                cancelButton.setVisibility(View.GONE);

            }
        });
    }
}