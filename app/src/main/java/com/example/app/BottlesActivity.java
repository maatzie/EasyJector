package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapters.BottlesRecyclerViewAdapter;
import com.example.app.adapters.PatientsRecyclerViewAdapter;
import com.example.app.database.pojo.Bottle;
import com.example.app.database.pojo.Patient;
import com.example.app.database.sqlite.BottleTableHandler;
import com.example.app.database.sqlite.PatientTableHandler;

import java.util.List;

public class BottlesActivity extends AppCompatActivity {

    private RecyclerView bottlesRecyclerView;
//    private List<Patient> patients;
    private BottleTableHandler bottleTableHandler;
//    public Integer editPatientID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottles);


        bottleTableHandler = new BottleTableHandler(getBaseContext());
        //bottleTableHandler.addTestData();
        bottleTableHandler.getBottles();


//        EditText editText_City = (EditText) findViewById(R.id.editText_City);
//
//
//
//        Button cancelButton = (Button) findViewById(R.id.button_cancelEditing);
//        cancelButton.setVisibility(View.GONE);
//
        List<Bottle> bottles = bottleTableHandler.getBottles();
        initRecyclerView(bottles);
//        initAddButtonClick();
//        initCancelButtonClick();
    }

    public void initRecyclerView(List<Bottle> bottles){
        bottlesRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_bottles);
        bottlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BottlesRecyclerViewAdapter adapter = new BottlesRecyclerViewAdapter(bottles, getSupportFragmentManager(), this);
        bottlesRecyclerView.setAdapter(adapter);
        bottlesRecyclerView.setNestedScrollingEnabled(false);
        //bottlesRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));

    }
}